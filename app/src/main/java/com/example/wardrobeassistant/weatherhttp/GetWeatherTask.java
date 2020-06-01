package com.example.wardrobeassistant.weatherhttp;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeatherTask extends AsyncTask<Void,Void,String> {

    String wea;//天气情况
    String wea_img;//天气图标
    String tem;//当前温度
    String tem_day;//白天温度
    String tem_night;//晚上温度
    String win;//风力
    String win_speed;//风力等级
    String win_meter;//风速
    String air;//空气质量

     public GetWeatherTask() {

    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        try {
            result =getWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            wea = jsonObject.optString("wea");
            wea_img = jsonObject.optString("wea_img");
            tem = jsonObject.optString("tem");
            tem_day = jsonObject.optString("tem_day");
            tem_night = jsonObject.optString("tem_night");
            win = jsonObject.optString("win");
            win_speed = jsonObject.optString("win_speed");
            win_meter = jsonObject.optString("win_meter");
            air = jsonObject.optString("air");
            tem = tem + "°C";
            if (callBack != null){
                callBack.getWeatherData(tem, wea + "|" + getAirLevel(air));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getWeather() throws IOException {
        URL url=new URL("https://www.tianqiapi.com/free/day?appid=99937974&appsecret=Hcvf8Xn4&city=郑州");
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(30 * 1000);
        InputStream in=conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line);
        }
        conn.disconnect();
        return stringBuffer.toString();
    }

    private String getAirLevel(String air){
        String airLevel = "";
        int level = 0;
        if (!TextUtils.isEmpty(air)){
            level = Integer.valueOf(air);
        }
        if (level < 51){
            airLevel = "空气优";
        } else if (50 < level && level < 101){
            airLevel = "空气良";
        }else if (100 < level && level < 201){
            airLevel = "轻度污染";
        }else if (200 < level && level < 301){
            airLevel = "中度污染";
        }else{
            airLevel = "重度污染";
        }
        return airLevel;
    }

    public interface GetWeatherDetailCallBack{
        void getWeatherData(String tem,String tip);
    }

    GetWeatherDetailCallBack callBack;

    public void setCallBack(GetWeatherDetailCallBack callBack) {
        this.callBack = callBack;
    }
}
