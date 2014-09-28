package com.example.potatoposse.activities;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.potatoposse.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class TakePictureActivity extends Activity {
	private static final String TAG = "CallCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

	Uri fileUri = null;
	ImageView photoImage = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.takepicture);
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = getOutputPhotoFile();
		fileUri = Uri.fromFile(getOutputPhotoFile());
		i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );
	}
	private File getOutputPhotoFile() {
		File directory = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), getPackageName());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				Log.e(TAG, "Failed to create storage directory.");
				return null;
			}
		}
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
		Log.e("camera_file",directory.getPath() + File.separator + "IMG_"  
				+ timeStamp + ".jpg" );	  
		return new File(directory.getPath() + File.separator + "IMG_"  
				+ timeStamp + ".jpg");
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
			if (resultCode == RESULT_OK) {
				Uri photoUri = null;
				if (data == null) {		    	  
					// A known bug here! The image should have saved in fileUri
					Toast.makeText(this, "Image saved successfully", 
							Toast.LENGTH_SHORT).show();
					photoUri = fileUri;

					Log.e("camera_file", "Data null so "+fileUri);
				} else {
					photoUri = data.getData();
					Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
							Toast.LENGTH_LONG).show();
				}
				showPhoto(photoUri);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Callout for image capture failed!", 
						Toast.LENGTH_LONG).show();
			}
		}
	}
	private void showPhoto(Uri photoUri) {

		Log.e("camera_file", photoUri.toString());
		String filePath = photoUri.getEncodedPath();
		File imageFile = new File(filePath);
		if (imageFile.exists()){
			Intent i = new Intent().setClass(this,SendEmailActivity.class);
			i.putExtra("IMAGE", filePath);
			startActivity (i);
		}       
	}
}

