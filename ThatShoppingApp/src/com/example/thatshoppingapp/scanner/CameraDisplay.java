/**
* Class that handles camera display and positioning
* 
* Uses the ZBar library
* Created by Carson Noble
*/
package com.example.thatshoppingapp.scanner;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraDisplay extends SurfaceView implements SurfaceHolder.Callback
{
	private SurfaceHolder holder;
	private Camera camera;
	private PreviewCallback previewCallback;
	private AutoFocusCallback autoFocusCallback;
	
	/*
	 * Instantiates all of the required components to create the scanner
	 */
	@SuppressWarnings("deprecation")
	public CameraDisplay(Context scanActivity, Camera c, PreviewCallback previewCB, AutoFocusCallback autoFocusCB)
	{
		super(scanActivity);
		camera = c;
		previewCallback = previewCB;
		autoFocusCallback = autoFocusCB;
		
		holder = getHolder();
		holder.addCallback(this);
		
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	//Create surface and set where preview is drawn
	public void surfaceCreated(SurfaceHolder holder)
	{
		try
		{
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (IOException e) {
			Log.d("DBG", "Error setting camera preview: " + e.getMessage());
		} catch (AndroidRuntimeException e) {
			Log.d("DBG", "Error setting camera preview: " + e.getMessage());
		}
	}
	
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.d("DBG", "Stopping preview in SurfaceDestroyed().");
		
//		if (camera != null) 
//		{
//			camera.setPreviewCallback(null);;
//			camera.stopPreview();
//			camera.release();
//		
//			camera = null;
//		}
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if (holder.getSurface() == null)
		{
			return;
		}
		
		try {
			camera.stopPreview();
		} catch (Exception e) {
			//Ignore - Tried to stop a non-existent display
		}
		
		try {
			camera.setDisplayOrientation(90);
			camera.setPreviewDisplay(holder);
			camera.setPreviewCallback(previewCallback);
			camera.startPreview();
			camera.autoFocus(autoFocusCallback);
		} catch (Exception e) {
			Log.d("DBG", "Error starting the camera preview: " + e.getMessage());
		}
	}
}