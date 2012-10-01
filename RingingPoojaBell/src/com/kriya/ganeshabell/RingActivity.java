package com.kriya.ganeshabell;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class RingActivity extends Activity implements SensorEventListener {

	private SoundPool soundPool;
	private float mLastX, mLastY, mLastZ;
	private int soundID1, soundID2, soundID3, soundID4, soundID5, soundID6,
	soundID7, soundID8, soundID9, soundID10, soundID11;
	boolean loaded = false;
	boolean mInitialized = false;
	Random rand = new Random();
	int min = 1, max = 11;
	SensorManager manager;
	Sensor accelerometer;
	private final float NOISE = (float) 2.0;
	private int shakecount = 0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (!manager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_GAME)) {
			Log.d("Accelerometer", "registration failed");
		}
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});

		soundID1 = soundPool.load(this, R.raw.bell_sound1, 1);
		soundID2 = soundPool.load(this, R.raw.bell_sound2, 1);
		soundID3 = soundPool.load(this, R.raw.bell_sound3, 1);
		soundID4 = soundPool.load(this, R.raw.bell_sound4, 1);
		soundID5 = soundPool.load(this, R.raw.bell_sound5, 1);
		soundID6 = soundPool.load(this, R.raw.bell_sound6, 1);
		soundID7 = soundPool.load(this, R.raw.bell_sound7, 1);
		soundID8 = soundPool.load(this, R.raw.bell_sound8, 1);
		soundID9 = soundPool.load(this, R.raw.bell_sound9, 1);
		soundID10 = soundPool.load(this, R.raw.bell_sound10, 1);
		soundID11 = soundPool.load(this, R.raw.bell_sound11, 1);

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	protected void onResume() {
		super.onResume();
		manager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_GAME);
	}

	protected void onPause() {
		super.onPause();
		manager.unregisterListener(this);
	}

	public void onSensorChanged(SensorEvent event) {

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		ImageView iv = (ImageView)this.findViewById(R.id.imageView1);
		Matrix matrix=new Matrix();
		iv.setScaleType(ScaleType.MATRIX);  
		iv.setImageMatrix(matrix); 

		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
			if (deltaX < NOISE) {
				deltaX = (float) 0.0;
			} else {
				Log.d("Accelerometer", "X Changed");
				this.moveImage();
				this.playSound();
				
			
			}

			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;
			else {
				Log.d("Accelerometer", "Z Changed");
				this.moveImage();
				this.playSound();
						
			}
			mLastX = x;
			mLastY = y;
			mLastZ = z;
		}

		mInitialized = true;

	}

	

	
	public void playSound(){
			int randomNum = rand.nextInt(max - min + 1) + min;
			Log.d("Random", "" + randomNum);
			if (loaded) {
				switch (randomNum) {
				case 1:
					soundPool.play(soundID1, 1, 1, 0, 0, 1f);
					break;
				case 2:
					soundPool.play(soundID2, 1, 1, 0, 0, 1f);
					break;
				case 3:
					soundPool.play(soundID3, 1, 1, 0, 0, 1f);
					break;
				case 4:
					 soundPool.play(soundID4, 1, 1, 0, 0, 1f);
					break;
				case 5:
					soundPool.play(soundID5, 1, 1, 0, 0, 1f);
					break;
				case 6:
					soundPool.play(soundID6, 1, 1, 0, 0, 1f);
					break;
				case 7:
					soundPool.play(soundID7, 1, 1, 0, 0, 1f);
					break;
				case 8:
					soundPool.play(soundID8, 1, 1, 0, 0, 1f);
					break;
				case 9:
					soundPool.play(soundID9, 1, 1, 0, 0, 1f);
					break;
				case 10:
					soundPool.play(soundID10, 1, 1, 0, 0, 1f);
					break;
				case 11:
					soundPool.play(soundID11, 1, 1, 0, 0, 1f);
					break;
				}
				
				Log.d("sound played",""+randomNum);
			}
	}

		
		public void moveImage(){		
			
			ImageView iv = (ImageView) findViewById(R.id.imageView1);
			Matrix matrix=new Matrix();
			iv.setScaleType(ScaleType.MATRIX);  
			if(shakecount % 2 == 0){
			matrix.postRotate((float) 10f, iv.getDrawable().getBounds().width()/2, iv.getDrawable().getBounds().height()/2);
			}else{
				matrix.postRotate((float) -10f, iv.getDrawable().getBounds().width()/2, iv.getDrawable().getBounds().height()/2);
					
			}
			shakecount++;
			iv.setImageMatrix(matrix); 
		}

	
}
