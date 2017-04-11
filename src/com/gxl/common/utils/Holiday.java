package com.gxl.common.utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;




public class Holiday {

    public static boolean isHoliday(String date)
        throws Exception {                
        String httpUrl = "http://www.easybots.cn/api/holiday.php";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date = sdf.format(sdfDateFormat.parse(date));
        String fdate = "d=" + date;        
        String response = request(httpUrl, fdate);
        JSONObject jsonResult = JSONObject.fromObject(response);
        System.out.printf(jsonResult.getString(date));
        if ("2".equals(jsonResult.getString(date))) {                   
           return true;
        }        
        return false;
    }

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        System.out.println(httpUrl);
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");                      
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}