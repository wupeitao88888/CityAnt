/*
 * Copyright 2011 meiyitian
 * Blog  :http://www.cnblogs.com/meiyitian
 * Email :haoqqemail@qq.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iloomo.net;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.iloomo.bean.BaseModel;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.content.Context;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 异步HTTPPOST请求
 * <p>
 * 线程的终止工作交给线程池，当activity停止的时候，设置回调函数为false ，就不会执行回调方法。
 *
 * @param <T>
 * @param <T>
 * @author sailor
 */
public class AsyncHttpPost<T> {
    public AsyncHttpPost(final ThreadCallBack callBack, String url,
                         Map<String, String> parameter, final int resultCode, final Class<T> modelClass, final Context context) {
        L.e("请求：" + url + "?" + sort(parameter));
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(url)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DialogUtil.stopDialogLoading(context);
                        String response = ErrorUtil.errorJson("404", "网络连接失败，请检查网络");
                        Object model = JSON.parseObject(response, modelClass);
                        callBack.onCallbackFromThreadError(response, model);
                        callBack.onCallBackFromThreadError(response, resultCode, model);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.stopDialogLoading(context);
                        response = UnicodeUtils.decodeUnicode(response);
                        L.e("返回值：" + response);
                        Object model = JSON.parseObject(response, modelClass);
                        BaseModel baseMadel = (BaseModel) model;
                        if (baseMadel.getCode().equals("200")) {
                            callBack.onCallbackFromThread(response, model);
                            callBack.onCallBackFromThread(response, resultCode, model);
                        } else {
                            callBack.onCallbackFromThreadError(response, model);
                            callBack.onCallBackFromThreadError(response, resultCode, model);
                        }
                    }
                });
    }

    public String sort(Map<String, String> params) {

        List<String> key = new ArrayList<String>();
        Iterator it = params.keySet().iterator();
        while (it.hasNext()) {
            String keys = it.next().toString();
            key.add(keys);
        }

        Collections.sort(key);
        StringBuilder result = new StringBuilder();
        for (String temp : key) {
            if (key.size() > 0)
                result.append("&");
            result.append(temp);
            result.append("=");
            result.append(params.get(temp));
        }
        try {
            return result.toString().substring(1, result.toString().length());
        } catch (Exception e) {
            return "";
        }
    }

    /****
     * 将文件作为请求体，发送到服务器。
     */
    public AsyncHttpPost(String url,File file,StringCallback stringCallback,Context context) {
        L.e("请求：" + url );
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .tag(context)
                .build()
                .execute(stringCallback);
    }

    /****
     * 将文件作为请求体，发送到服务器。
     */
    public AsyncHttpPost(String url,Map<String,String> params,String fliekey,File file,StringCallback stringCallback,Context context) {
        L.e("请求：" + url + "?" + sort(params));
        OkHttpUtils.post()//
                .addFile(fliekey, file.getName(), file)//
                .url(url)
                .params(params)//
                .tag(context)
                .build()//
                .execute(stringCallback);
    }

    /****
     * 提交一个Gson字符串到服务器端。
     * @param url
     * @param object
     * @param stringCallback
     */
    public AsyncHttpPost(String url,Object object,StringCallback stringCallback,Context context) {
        L.e("请求：" + url + "?" + JSON.toJSON(object).toString());
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(JSON.toJSON(object).toString())
                .tag(context)
                .build()
                .execute(stringCallback);
    }



}
