package com.example.nowingo.newsday.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 网络链接操作类
 *
 */
public class HttpConnection {
    //拼接城市路径
    public static String getCityPath(){
        String path=null;
        StringBuffer sb=new StringBuffer();
        sb.append(HttpEntity.HTTP_CITY_PATH);
        sb.append("?key=");
        sb.append(HttpEntity.HTTP_KEY_2);
        path=sb.toString();
        return path;

    }
    //拼接天气种类路径
    public static String getWeatherTypePath(){
        String path=null;
        StringBuffer sb=new StringBuffer();
        sb.append(HttpEntity.HTTP_WEATHER_TYPE_PATH);
        sb.append("?key=");
        sb.append(HttpEntity.HTTP_KEY_2);
        path=sb.toString();
        return path;
    }

    /**
     * 拼接天气查询路径
     * @param cityName 需要查询的城市名称
     * @return  路径
     */
    public static String getWeatherPath(String cityName){
        String path=null;
        StringBuffer sb=new StringBuffer();
        sb.append(HttpEntity.HTTP_GET_WEATHER_PATH);
        sb.append("?format=2");
        sb.append("&cityname=");
        try {
            sb.append(URLEncoder.encode(cityName,"utf-8"));//转码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&key=");
        sb.append(HttpEntity.HTTP_KEY_2);
        path=sb.toString();
        return path;
    }

    /**
     * 获得城市天气
     * @param cityName 需要查询的城市名称
     * @return  天气详细信息
     */
    public static String getCityWeather(String cityName){
        String result=null;
        try {
            URL url=new URL(getWeatherPath(cityName));//建立通道
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();//通过通道打开链接
            //配置方式
            urlConnection.setRequestMethod("GET");
            //超时时间定义
            urlConnection.setConnectTimeout(5000);//链接最大时长
            urlConnection.setReadTimeout(5000);//读取最大时长
            urlConnection.connect();//开启链接
            if(urlConnection.getResponseCode()==200){//如果返回码为200 代表访问成功
                BufferedInputStream bis=new BufferedInputStream(urlConnection.getInputStream());//获得返回的输入流


                byte[] b=new byte[1024];//定义一个数组
                int len=0;//定义读取长度
                StringBuffer sb=new StringBuffer();//字符串拼接缓冲类
                while((len=bis.read(b))!=-1){//不等于-1代表还在继续读取
                        sb.append(new String(b,0,len));//将数组转换为String 确保不浪费控件，每次转换读取长度的位置

                }
                result=sb.toString();//获得返回数据
                //关闭流
                 bis.close();
                //断开链接
                urlConnection.disconnect();
            } else{
                //这里写链接失败的操作
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return result;
    }

    /**
     * 获得支持的城市列表
     * @return  城市列表
     */
    public static String getCityData(){
        String result=null;
        try {
            URL url=new URL(getCityPath());//建立通道
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();//通过通道打开链接
            //配置方式
            urlConnection.setRequestMethod("GET");
            //超时时间定义
            urlConnection.setConnectTimeout(5000);//链接最大时长
            urlConnection.setReadTimeout(5000);//读取最大时长
            urlConnection.connect();//开启链接
            if(urlConnection.getResponseCode()==200){//如果返回码为200 代表访问成功
//                BufferedInputStream bis=new BufferedInputStream(urlConnection.getInputStream());//获得返回的输入流
                InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream(),"UTF-8");
                char[] b=new char[1024];//定义一个数组
                int len=0;//定义读取长度
                StringBuffer sb=new StringBuffer();//字符串拼接缓冲类
                while((len=isr.read(b))!=-1){//不等于-1代表还在继续读取
                    sb.append(new String(b,0,len));//将数组转换为String 确保不浪费控件，每次转换读取长度的位置

                }
                result=sb.toString();//获得返回数据
                //关闭流
                isr.close();
                //断开链接
                urlConnection.disconnect();
            } else{
                //这里写链接失败的操作
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得天气标识
     * @return 天气标识
     */
    public static String getWeatherType(){
        String result=null;
        try {
            URL url=new URL(getWeatherTypePath());//建立通道
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();//通过通道打开链接
            //配置方式
            urlConnection.setRequestMethod("GET");
            //超时时间定义
            urlConnection.setConnectTimeout(5000);//链接最大时长
            urlConnection.setReadTimeout(5000);//读取最大时长
            urlConnection.connect();//开启链接
            if(urlConnection.getResponseCode()==200){//如果返回码为200 代表访问成功
                BufferedInputStream bis=new BufferedInputStream(urlConnection.getInputStream());//获得返回的输入流
                byte[] b=new byte[1024];//定义一个数组
                int len=0;//定义读取长度
                StringBuffer sb=new StringBuffer();//字符串拼接缓冲类
                while((len=bis.read(b))!=-1){//不等于-1代表还在继续读取
                    sb.append(new String(b,0,len));//将数组转换为String 确保不浪费控件，每次转换读取长度的位置

                }
                result=sb.toString();//获得返回数据
                //关闭流
                bis.close();
                //断开链接
                urlConnection.disconnect();
            } else{
                //这里写链接失败的操作
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
