package com.cityant.main.zxing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cityant.main.R;
import com.cityant.main.utlis.Utils;
import com.cityant.main.zxing.camera.CameraManager;
import com.cityant.main.zxing.decoding.CaptureActivityHandler;
import com.cityant.main.zxing.decoding.InactivityTimer;
import com.cityant.main.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends Activity implements Callback{

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	public static String RESULT = "result";
	public static String SCAN_RESULT = "scanresult";
	private boolean vibrate;
	private ProgressDialog mProDialog;
	private String resultString;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		// ViewUtil.addTopView(getApplicationContext(), this,
		// R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

		LinearLayout cancel_ll = (LinearLayout) findViewById(R.id.cancel_ll);
		cancel_ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MipcaActivityCapture.this.finish();

			}
		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 处理扫描结果
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		resultString = result.getText();
		if (resultString.equals("") || resultString == null) {
			Toast.makeText(MipcaActivityCapture.this, "Scan failed!",
					Toast.LENGTH_SHORT).show();
		} else {
//			BenguoBasic.getInstance().scanisTrue(
//					MipcaActivityCapture.this,
//					SPathApi.getInstance().getStringKey(
//							SPathApi.BENGUO_CONF_DEVID, ""), resultString,
//					TaskID.ACTION_GET_SCAN_RESULT);
			// TODO 网络请求
			mProDialog = Utils.getProgressDialog(this, null, "已扫描,正在处理...");
			mProDialog.show();

		}
		// MipcaActivityCapture.this.finish();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

//	@Override
//	public void onCompleted(SPathApiTask task, Object obj) {
//		int taskid = task.getTaskID();
//		try {
//			if (obj != null) {
//				if (taskid == TaskID.ACTION_GET_SCAN_RESULT) {
//					getScanOver(obj);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			if (mProDialog != null && mProDialog.isShowing()) {
//				mProDialog.dismiss();
//			}
//			e.printStackTrace();
//			SPathException.getInstance(this).handleErrorMessage(e);
//		}
//	}
//
//	@Override
//	public void onError(SPathApiTask task, Exception e) {
//		e.printStackTrace();
//		if (mProDialog != null && mProDialog.isShowing()) {
//			mProDialog.dismiss();
//		}
//		scanResult(false);
//	}

	private void getScanOver(Object obj) {
		if(obj != null){
			JSONObject object = JSON.parseObject(obj.toString());
			boolean result = object.getBooleanValue("result");
			if (result) {
				scanResult(result);
			} else {
				scanResult(false);
			}
		}
	}

	private void scanResult(Boolean result) {
		Intent resultIntent = new Intent(this, ScanResultActivity.class);
		resultIntent.putExtra(RESULT, result);
		resultIntent.putExtra(SCAN_RESULT, resultString);
		startActivity(resultIntent);
		MipcaActivityCapture.this.finish();
	}

	/**
	 * 
	 * 扫描本地相册图片
	 */
	// public String parsLocalPic(String path) {
	// String parseOk = null;
	// Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType,
	// String>();
	// hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
	//
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true; // 先获取原大小
	// Bitmap bitmap = BitmapFactory.decodeFile(path, options);
	// options.inJustDecodeBounds = false; // 获取新的大小
	// // 缩放比
	// int be = (int) (options.outHeight / (float) 200);
	// if (be <= 0)
	// be = 1;
	// options.inSampleSize = be;
	// bitmap = BitmapFactory.decodeFile(path, options);
	// int w = bitmap.getWidth();
	// int h = bitmap.getHeight();
	// System.out.println(w + "   " + h);
	// RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
	// BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
	// QRCodeReader reader2 = new QRCodeReader();
	// Result result;
	// try {
	// result = reader2.decode(bitmap1, hints);
	// android.util.Log.i("steven", "result:" + result);
	// parseOk = result.getText();
	//
	// } catch (NotFoundException e) {
	// parseOk = null;
	// } catch (ChecksumException e) {
	// parseOk = null;
	// } catch (FormatException e) {
	// parseOk = null;
	// }
	// return parseOk;
	// }

}