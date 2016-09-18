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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.iloomo.bean.BaseModel;
import com.iloomo.utils.L;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import android.content.Context;

import okhttp3.Call;

/**
 * @param <T>
 * @author zxy
 */
public class AsyncHttpGet<T> {
    public AsyncHttpGet(final ThreadCallBack callBack, String url,
                        Map<String, String> parameter, final int resultCode, final Class<T> modelClass, Context context) {
        L.e("请求：" + url + "?" + sort(parameter));
        OkHttpUtils
                .get()
                .url(url)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        String response = ErrorUtil.errorJson("404", "网络连接失败，请检查网络");
                        Object model = JSON.parseObject(response, modelClass);
                        callBack.onCallbackFromThreadError(response, model);
                        callBack.onCallBackFromThreadError(response, resultCode, model);
                    }

                    @Override
                    public void onResponse(String resultData, int id) {
                        resultData = UnicodeUtils.decodeUnicode(resultData);
                        L.e("返回值：" + resultData);
                        Object model = JSON.parseObject(resultData, modelClass);
                        BaseModel baseMadel = (BaseModel) model;
                        if (baseMadel.getCode().equals("200")) {
                            callBack.onCallbackFromThread(resultData, model);
                            callBack.onCallBackFromThread(resultData, resultCode, model);
                        } else {
                            callBack.onCallbackFromThreadError(resultData, model);
                            callBack.onCallBackFromThreadError(resultData, resultCode, model);
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

    public AsyncHttpGet(String url, StringCallback stringCallback, Context context, int resultCode) {
        OkHttpUtils
                .get()
                .url(url)
                .id(resultCode)
                .tag(context)
                .build()
                .execute(stringCallback);
    }

    public AsyncHttpGet(String url, FileCallBack fileCallBack, Context context, int resultCode) {
        OkHttpUtils//
                .get()//
                .url(url)//
                .id(resultCode)
                .tag(context)
                .build()//
                .execute(fileCallBack);
    }

}
