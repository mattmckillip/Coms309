/**
 * This class is responsible for encoding the shopping cart's contents and creating a displayable QR code for use during checkout
 * Due to some extreme complexities/time crunch in development, I'm using ZXing's QR Generator along with a request to the local
 * database to get all of the barcodes in the contents of the cart
 * @author Carson Noble
 */

package com.example.thatshoppingapp.scanner;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.list.ListDatabaseHelper;
import com.example.thatshoppingapp.cart.CartDatabaseHelper;


import android.graphics.Bitmap;

public class QRGenerator 
{
	//Referring to the colors on the final QR code
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;
	
	private int dimension = Integer.MIN_VALUE;
	private String contents = null;
	private BarcodeFormat format = BarcodeFormat.QR_CODE;
	
	private Bitmap QRCode;
	
	public QRGenerator(CartDatabaseHelper cartDB)
	{
		//Set contents to the contents of the cart
		this.contents = getCartContents(cartDB);
		try {
			this.QRCode = generateCode();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Grabs all of the contents of the cart (Specifically the barcodes) and produces a space-separated list of values to encode
	 * Initially, it would condense the list and separate values using quantities of each, but I decided against that, 
	 * considering any checkout would scan individually, and this requires less formatting on both ends
	 */
	public String getCartContents(CartDatabaseHelper cartDB)
	{
		ArrayList<ItemObject> itemList = cartDB.getAllData();
		
		String cart = "";
		for (ItemObject io:itemList)
		{
			cart += io.getCode() + " ";
		}
		return cart;
	}
	
	/**
	 * Performs all necessary steps of the QR Code generation process
	 * @return a Bitmap displaying the code
	 */
	public Bitmap generateCode() throws WriterException
	{
		Map<EncodeHintType, Object> hints = null;
		
		hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET,  "UTF-8");
		
		MultiFormatWriter writer = new MultiFormatWriter();
		//The contents of the QR Code are encoded in the ZXing external library
		//Again, I outsourced this due to time constraints and complexity issues
		BitMatrix result = writer.encode(contents, format, 500, 500, hints);
		
		//Dimensions of QR Code
		int width = result.getWidth();
		int height = result.getHeight();
		
		//Build the QR code in an array
		int[] pixels = new int[width*height];
		for (int y=0; y<height; y++)
		{
			int offset = y*width;
			for (int x=0; x<width; x++)
			{
				pixels[offset+x] = result.get(x, y) ? BLACK : WHITE;
			}
		}
		
		//Generate the QR Code from the pixel array 
		Bitmap code = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		code.setPixels(pixels, 0, width, 0, 0, width, height);
		return code;
	}
	
	public Bitmap getCode()
	{
		return QRCode;
	}
}