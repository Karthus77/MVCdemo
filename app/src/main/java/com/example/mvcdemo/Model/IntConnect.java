package com.example.mvcdemo.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mvcdemo.weather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class IntConnect implements CtoM {
    private Object MtoV;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void getWeather(String name, final MtoV mtov) {
        final String baseUrl = "http://apis.juhe.cn/simpleWeather/query";
        final String key = "5b8f08e99aac2cbbfb11f89d8cb6d7a5";
        Map<String, Object> params = new HashMap<>();//组合参数
        params.put("city", name);
        params.put("key", key);
        String queryParams = urlencode(params);
        final String url1=baseUrl+"?"+queryParams;
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final StringBuilder response = new StringBuilder();
                    URL url=new URL(url1);//获取服务器哦地址
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();//双方建立连接

                    urlConnection.setRequestMethod("GET");//给服务器发送请求
                    InputStream inputStream=urlConnection.getInputStream(); //字节流
                    Reader reader=new InputStreamReader(inputStream); //把字节流转化成字符流
                    BufferedReader bufferedReader=new BufferedReader(reader);//字符流 转成 缓冲流，一次可以读一行

                    String line;
                    while ((line=bufferedReader.readLine())!=null){//当temp读到的数据为空就结束
                        response.append(line);

                    }
                    Gson gson=new Gson();
                    String responseData = String.valueOf(response);
                    weather weather = gson.fromJson(responseData,com.example.mvcdemo.weather.class);
                    if(weather.getError_code()==0)
                    {
                        mtov.onSuccess(weather );
                    }
                    else
                    {
                        mtov.onError();
                    }

                } catch (Exception e) {
                    mtov.onError();
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }




    public static String urlencode(Map<String, ?> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String result = sb.toString();
        result = result.substring(0, result.lastIndexOf("&"));
        return result;
    }

}
