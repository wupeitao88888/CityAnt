package com.cityant.main.utlis;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


/**
 * 上传语音文件类
 *
 */
public class UploadVoice {
	// 上传语音文件
	public void uploadVoice(String filePath, final Context context) throws IOException {
		
		Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 10:// 上传成功
					try {
						JSONObject jsObject = new JSONObject(msg.obj.toString());
//						XNLog.e("语音", msg.obj.toString());
						Object opt = jsObject.get("type");
						int type = Integer.parseInt(opt +"");
						if(type == 3){
							@SuppressWarnings("unused")
							String msgVoice = jsObject.getString("msg");//传输完成
							String urlVoice = jsObject.getString("url");//文件地址
							String mp3Voice = jsObject.getString("mp3");//mp3格式地址
//							XNLog.e("语音地址", mp3Voice);
							@SuppressWarnings("unused")
							String oldfileVoice = jsObject.getString("oldfile");
							String sizeVoice = jsObject.getString("size");//文件大小
							String extensionVoice = jsObject.getString("extension");//格式,amr
							String lengthVoice = jsObject.getString("length");// 语音文件时长
//							XNLog.e("语音长度", lengthVoice);
//							Chat.sendVoice(context, "voice", String.valueOf(5), urlVoice, mp3Voice, extensionVoice, sizeVoice, lengthVoice);
						}
								
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				case 20:
					// 上传语音失败
					Toast.makeText(context, "无网络链接,发送失败", Toast.LENGTH_SHORT).show();
				break;
				default:
					break;
				}
			}
		};
//		Map<String, File> files = new HashMap<String, File>();
//		files.put("userfile", new File(filePath));//文件
//		Map<String, String> params = new HashMap<String, String>();
//		String siteidsub = NtalkerUtils.getfloat(context);
//		String siteid = siteidsub.substring(siteidsub.indexOf("siteid["),
//				siteidsub.indexOf("]")).substring("siteid[".length());
//
////		m.put("siteid",
////				Receivechat.Getsiteid(context));
////		String upUrl = "http://downt.rd.ntalker.com/t2d/func/";
//		mHttpclient.getInstance().addTaskimage(new MyRunnable(handler),//myrunable
//				NtalkerUtils.mFileserver+"/imageupload.php?action=uploadaudio&siteid="+siteid+"&type=json",//上传uRl
//				handler,
//				params,//参数
//				files);//文件
	}
	
	
}
