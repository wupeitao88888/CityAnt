package com.cityant.main.utlis;

import java.io.File;
import java.io.IOException;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

public class AudioRecorder {

	private static int SAMPLE_RATE_IN_HZ = 8000; // 采样率
	private MediaRecorder recorder = new MediaRecorder();
	private String path;

	public AudioRecorder(String path) {
		this.path = sanitizePath(path);
	}

	public String sanitizePath(String path) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (!path.contains(".")) {
			path += ".amr";
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				  + "/nt" + path;
	}

	// 开始录音的方法
	@SuppressWarnings("deprecation")
	public void start() throws IOException {
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			throw new IOException("SD Card is not mounted,It is  " + state
					+ ".");
		}
		File directory = new File(path).getParentFile();
		if (!directory.exists() && !directory.mkdirs()) {
			throw new IOException("Path to file could not be created");
		}
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
		recorder.setOutputFile(path);
		recorder.prepare();
		recorder.start();
	}

	// 录音停止,释放资源
	public void stop() throws IOException {
		recorder.stop();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				recorder.reset();
				recorder.release();
				recorder = null;
			}
		}, 2000);
		
	}
	public void stops() {
		recorder.stop();
	}

	// 获得录音振幅
	public double getAmplitude() {
		if (recorder != null) {
			return recorder.getMaxAmplitude();
		} else{
			return 0;
		}
			
	}
}