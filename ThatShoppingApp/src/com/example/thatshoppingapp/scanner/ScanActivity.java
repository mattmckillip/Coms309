/**
 * Application design to scan a barcode and retrieve information from store inventory about the product
 * 
 * Designed using the ZBar library to process the code
 * Created by Carson Noble
 */
package com.example.thatshoppingapp.scanner;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.cart.CartDatabaseHelper;
import com.example.thatshoppingapp.home.HomeAddon;
import com.example.thatshoppingapp.list.ListDatabaseHelper;
import com.example.thatshoppingapp.search.RemoteDatabaseHelper2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;
import android.widget.TextView;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;


public class ScanActivity extends ShoppingAppPage implements HomeAddon
{

	private boolean itemEnterFlag = true;

	private ItemObject returnItem;

	
	private Camera camera;
	private CameraDisplay display;
	private Handler autoFocusHandler;
	FrameLayout preview;
	//Parsed code obtained after scanning
	TextView scanText;
	TextView itemInfo;
	//Button that records the picture
	ImageButton scanButton;
	ImageButton addButton;
	ImageButton addButtonCart;
	protected RemoteDatabaseHelper2 rdatabaseHelper;
	
	private boolean scanSuccess = false;
	//Separates scan functionality from normal previewCallback behavior
	
	
	private static ItemObject item;
	
	//Used to regulate button functionality
	boolean inStore = false;
	
	ImageScanner scanner;
	
	private boolean barcodeScanned = false;
	private boolean previewing = true;
	
	
	//Loads icon library
	//static { System.loadLibrary("iconv"); item = new ItemInfo(); }

	public ScanActivity() 
	{
		super("scan", "Scan Barcode", R.layout.activity_scan);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		final ListDatabaseHelper listDB = new ListDatabaseHelper(getActivity());
		final CartDatabaseHelper cartDB = new CartDatabaseHelper(getActivity());
		
		autoFocusHandler = new Handler();
		camera = getCamera();
		
		//Create barcode scanner
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);
		
		display = new CameraDisplay(this.getActivity(), camera, previewCB, autoFocusCB);
		preview = (FrameLayout)view.findViewById(R.id.cameraDisplay);
		preview.addView(display);
		
		scanText = (TextView)view.findViewById(R.id.ScanText);
		itemInfo = (TextView)view.findViewById(R.id.ItemInfo);
		scanButton = (ImageButton)view.findViewById(R.id.ScanButton);
		addButton = (ImageButton)view.findViewById(R.id.AddButton);
		addButtonCart = (ImageButton)view.findViewById(R.id.AddButtonCart);
		
		//Added by matt
		rdatabaseHelper = new RemoteDatabaseHelper2(view.getContext());
				
		//Creates the listener that performs following actions when Scan button is pressed
		scanButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v)
				{

					if (!barcodeScanned && camera != null)
					{
						barcodeScanned = true;
						scanText.setText("Processing...");
						scanButton.setImageResource(R.drawable.ic_cancel);
						camera.stopPreview();
						previewing = false;
					}
					else if (barcodeScanned && camera != null)
					{
						barcodeScanned = false;
						scanText.setText("Please scan in well-lit area.");
						scanButton.setImageResource(R.drawable.ic_scan_barcode);
						itemInfo.setVisibility(View.INVISIBLE);
						camera.setPreviewCallback(previewCB);
						previewing = true;
						camera.startPreview();
						camera.autoFocus(autoFocusCB);
						scanSuccess = false;
						addButton.setClickable(false);
					}
				}
		});
		
		//Creates the listener that adds scanned item to (cart) on button press. 
		addButtonCart.setOnClickListener(new OnClickListener() {
				public void onClick(View v)
				{
					//Button will only be available to click when there is an actual item - Errorchecking will be performed once there's a populated database
					Context context = getActivity();
					
					//If the add was successful - try/catch block here
					if (scanSuccess && item != null)
					{
						Toast.makeText(context, item.getItemName() + " successfully added to Cart", Toast.LENGTH_LONG).show();
						cartDB.insertItem(item);
					}
					else
					//catch (AddException e) { ...
						Toast.makeText(context, "Item was not added to Cart. Please scan again.", Toast.LENGTH_LONG).show();
				}
		});
		
		
		//Creates the listener that adds scanned item to (cart/list) on button press. 
		addButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v)
				{
					//Button will only be available to click when there is an actual item - Errorchecking will be performed once there's a populated database
					Context context = getActivity();
							
					
					//If the add was successful - try/catch block here
					if (scanSuccess && item != null)
					{
						Toast.makeText(context, item.getItemName() + " successfully added to List", Toast.LENGTH_LONG).show();
						listDB.insertItem(item);
					}
					//catch (AddException e) { ...
					else
						Toast.makeText(context, "Item was not added to List. Please scan again.", Toast.LENGTH_LONG).show();
				}	
		});
		
		display.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				if (previewing)
					camera.autoFocus(autoFocusCB);
			}
		});
		
		getActivity().getActionBar().setTitle("Barcode Scanner");
		
		return view;
		
	}
	
	/*
	 * Releases the camera when the screen is no longer active to avoid memory loss 
	 * Also allows control of camera to active app
	 */
	public void onPause()
	{
		super.onPause();
		releaseCamera();
	}
	
	public void onStop()
	{
		super.onStop();
		releaseCamera();
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		releaseCamera();
	}
	
	/*
	 * Resumes all application activity when the app is brought to the foreground
	 * TODO Start displaying preview on resumed app
	 */
	public void onResume()
	{
		super.onResume();
		
		//These lines act as a hard reset of the scanner's state
		//It avoids a lot of errors, but it might be better to remember the state the app was in in onPause to handle it better
		scanText.setText("Please scan in well-lit area");
		scanButton.setImageResource(R.drawable.ic_scan_barcode); 
		try {
			camera = Camera.open();
			Log.d("DBG", "Opened the camera");
			
			display = new CameraDisplay(this.getActivity(), camera, previewCB, autoFocusCB);
			preview.addView(display);
			
			previewing = true;
			
			camera.startPreview();
			camera.setPreviewCallback(previewCB);
			camera.autoFocus(autoFocusCB);
		} catch (Exception e) { Log.d("DBG", "Child already has a parent"); }
		//super.onResume() //This was in the code I had that was working - if this version breaks, this might have something
	}
	
	/*
	 * Instantiates the camera
	 */
	public static Camera getCamera()
	{
		Camera c = null;
		try
		{
			c = Camera.open();
			
		} catch (Exception e)
		{
			Log.d("DBG", "Failed to open camera in getCamera().");
		}
		
		return c;
	}
	
	/*
	 * Destructs the camera object
	 */
	private void releaseCamera()
	{
		if (camera != null)
		{
			previewing = false;
			camera.setPreviewCallback(null);
			display.getHolder().removeCallback(display); //TODO ?
			camera.release();
			camera = null;
		}
	}
	
	/*
	 * Because the camera and focus are, for some reason, entirely separate codes, we need to manually autofocus every so often to maintain high-quality preview
	 */
	private Runnable autoFocus = new Runnable() {
		public void run()
		{
			//Any future touch capabilities would be located here
			if (previewing)
			{
				camera.autoFocus(autoFocusCB);
			}
		}
	};
	
	PreviewCallback previewCB = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera)
		{				
			Camera.Parameters parameters = camera.getParameters();
			Size size = parameters.getPreviewSize();
			//Parses the bar code into the item data -> Done through ZBar code
			Image barcode = new Image(size.width, size.height, "Y800");
			barcode.setData(data);
			
			int result = scanner.scanImage(barcode);
				
			//If result is some non-zero data
			if (result != 0 && !previewing)
			{
				camera.setPreviewCallback(null);
				camera.stopPreview();
				
				//Results are displayed here
				SymbolSet syms = scanner.getResults();
				for (Symbol sym:syms) //syms size is always 1.
				{
					scanText.setText("Code found!");
					//Create item with given information
					try {
						//get barcode
						String code = sym.getData();
						Log.d("PreviewCallback", "Barcode scanned: " + code);
						
						//search remote database for that barcode
						ItemObject tempItem = rdatabaseHelper.queryDB_Barcode(code);
						item = rdatabaseHelper.queryDB_Keyword(tempItem.getItemName()).get(0);
						
						//item is not in the database
						//return letting the user know
						if (item==null){
							Log.d("PreviewCallback", "Null item: return");
							scanText.setText("Could not find that code in the database");
							scanSuccess = false;
							break;
						}
					} 
					
					//unhandled exception
					catch (Exception e) {
						Log.d("PreviewCallback", "Exception");
						scanText.setText("Unknown Error");
						break;
					}
					String text = "Code: " + item.getCode() + "\n\nName: " + item.getDisplayName();
					
					//Appends price if the call data is in the store
					if (inStore)
					{
						//TODO add users store
						text += "\n\nPrice: " + item.getPrice("0");
					}
				
					itemInfo.setText(text);
					itemInfo.setVisibility(View.VISIBLE);
					barcodeScanned = true;
					addButton.setClickable(true);
				}
			
				scanSuccess = true;
			}
			else if (!previewing && result == 0)
			{
				scanText.setText("Unable to find code. Please scan again.");
				
				scanSuccess = false;
			}
		}
	};
	
	AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera)
		{
			//Autofocus is now tap-based
		}
	};

	@Override
	public View getHomeAddon(ViewGroup container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIcon() {
		// TODO Auto-generated method stub
		return R.drawable.barcode_icon;
	}
}

