package com.anwahh.designitandvideomenu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anwahh.designitandvideomenu.utils.consent.ErrorConsent;
import com.anwahh.designitandvideomenu.utils.consent.NetworkConsent;
import com.anwahh.designitandvideomenu.utils.consent.ResourceName;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.content.Context.MODE_PRIVATE;

/**
 * <p>
 *     Network util class, support http <b>POST</b>, <b>GET</b> request, and other network util methods.
 * </p>
 *  <p>
 *     All methods defined here will set to <b>static</b>.
 * </p>
 * <p>
 *     Methods here are implement with {@link HttpURLConnection} and {@link ByteArrayOutputStream}
 * </p>
 * <p>
 *     Typically offer <b>Async</b> request methods, and it usually means there could be called in <b>UI thread</b>
 * </p>
 * <p>
 *     We set the class to <b>abstract</b> since we do not wanna someone accidentally create a reference.
 * </p>
 */
public abstract class NetworkUtils {

    /**
     * <p>
     *     Simply call  {@link NetworkUtils#( Activity , String , String , NetworkCallback)} in a new thread.
     * </p>
     * <p>
     *     So in tech, this method could be call in <b>UI Thread</b> without any problem.
     * </p>
     * @param activity Context, use to support error message in multi languages
     * @param domain  Request url
     * @param params network params with JM format, you could check JM API document for params format
     * @param callback Post request result callback
     */

    public static void postAsync(@NonNull final Activity activity, @Nullable final String domain, @Nullable final String params, @NonNull final NetworkCallback callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                post(activity, domain, params, callback,true);
            }
        });
        thread.start();
    }
    public static void postAsync(@NonNull final Activity activity, @Nullable final String domain, @Nullable final String params, @NonNull final NetworkCallback callback, final boolean isJson) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                post(activity, domain, params, callback,isJson);
            }
        });
        thread.start();
    }
    /**
     * <p>
     *     Simply call {@link NetworkUtils#get(Activity, String, String, NetworkCallback)} in a new Thread.
     * </p>
     * <p>
     *     So in tech, this method could be call in <b>UI Thread</b> without any problem.
     * </p>
     * @param activity Activity context, use to support error message in different language
     * @param domain Get request url, e.g: http://domain/get/request
     * @param param Get request param, it should format like key=value&key2=value2 and so on.....
     * @param callback Get result callback
     */
    public static void getAsync(final Activity activity, final String domain, final String param, final NetworkCallback callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                get(activity, domain, param, callback);
            }
        });
        thread.start();
    }


    /**
     * <p>
     *    Http get method implementation. Implement by {@link HttpURLConnection} and use {@link ByteArrayOutputStream} to read result.
     * </p>
     * <p>
     *    This method will catch all exceptions happened in http request, and return error in callback methods.
     * </p>
     * <p>
     *    This method is <b>not Thread safe</b>. However it will always trying to close all connection and streams after request in a right way.
     * </p>
     *
     * @param activity Activity context, use to support error message in different language
     * @param domain Get request url, e.g: http://domain/get/request
     * @param param Get request param, it should format like key=value&key2=value2 and so on.....
     * @param callback Get result callback
     */
    public static boolean get(@NonNull Activity activity, @NonNull String domain, @Nullable String param, @NonNull NetworkCallback callback) {
        if (TextUtils.isEmpty(domain)) {
            callback.onFailure(ErrorConsent.EMPTY_DOMAIN, ResourceUtils.getString(activity, ResourceName.HTTP_EMPTY_DOMAIN));
            return false;
        }
        HttpURLConnection conn;
        String finalDomain = TextUtils.isEmpty(param) ? domain : domain + "?" + param;
        Logger.debug("Http get final url: " + finalDomain);
        try {
            URL url = new URL(finalDomain);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(NetworkConsent.HTTP_GET);
        } catch (Exception e) {
            Logger.error(Logger.GLOBAL_TAG, "Exceptions happens when create URL connection", e);
            callback.onFailure(ErrorConsent.URL_BUILD_EXCEPTION, ResourceUtils.getString(activity, ResourceName.URL_CREATE_EXCEPTION));
            return false;
        }

        conn.setConnectTimeout(NetworkConsent.CONN_Timeout);
        conn.setDoOutput(false);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Charset", StandardCharsets.UTF_8.name());
        conn.setRequestProperty("Connection","Keep-Alive");//设置与服务器保持连接
//        String session_value = conn.getHeaderField("Set-Cookie");
//        String[] sessionId = session_value.split(";");
//        conn.setRequestProperty("Cookie", sessionId[0]);

//登录时带上session登录。让服务器可以区别是同一个客户端在操作
        SharedPreferences sharedPreferences1 = activity.getSharedPreferences("phone_login_code",MODE_PRIVATE);
        String cookie = sharedPreferences1.getString("session","");
        conn.setRequestProperty("Cookie",cookie);
        Logger.info("==============上一次请求的cooks:"+cookie);


        //获取session并存储到本地
        String sessionId="";
        boolean isErrorStream = false;
        InputStream input = null;
        int code;
        byte[] data = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                input = conn.getInputStream();
            } else {
                input = conn.getErrorStream();
                isErrorStream = true;
            }

            String cookieVal = conn.getHeaderField("Set-Cookie");
//            if (cookieVal != null) {
//                sessionId= cookieVal.substring(0, cookieVal.indexOf(";"));
//
//            }
            Logger.info("=============cookieVal:"+cookieVal);
            if(!TextUtils.isEmpty(cookieVal)){
                SharedPreferences sharedPreferences = activity.getSharedPreferences("phone_login_code",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("session",cookieVal);
                editor.commit();
            }



            int size;
            while ((size = input.read(data)) > 0) {
                baos.write(data, 0, size);
            }
        } catch (IOException e) {
            Logger.error(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.RESPONSE_READ_EXCEPTION), e);
            callback.onFailure(ErrorConsent.RESPONSE_READ_EXCEPTION, ResourceUtils.getString(activity, ResourceName.RESPONSE_READ_EXCEPTION));
            return false;
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) {
                Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.CLOSE_EXCEPTION_IS), "GET InputStream", e);
            }
            try {
                baos.close();
            } catch (IOException e) {
                Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.CLOSE_EXCEPTION_IS), "GET ByteArrayOutputStream", e);
            }
        }

        //send callback
        if (isErrorStream) {
            String message = "ErrorStream: " + new String(data, 0, data.length, StandardCharsets.UTF_8);
            callback.onFailure(code, message);
            return false;
        } else {
            callback.onSuccess(ErrorConsent.SUCCESS, baos.toString());
            return true;
        }
    }


    /**
     * <p>
     *     Http Post method implementation. Implement with {@link HttpURLConnection} and use {@link ByteArrayOutputStream} to read result.
     * </p>
     * <p>
     *    This method will catch all exceptions happened in http request, and return error in callback methods.
     * </p>
     * <p>
     *    This method is <b>not Thread safe</b>. However it will always trying to close all connection and streams after request in a right way.
     * </p>
     * <p>
     *     The difference between this method with {@link NetworkUtils#get(Activity, String, String, NetworkCallback)} is Http Request method
     *      & the way deal with request params. In a simple word, one is GET another is POST. :)
     * </p>
     * @param activity Context, use to support error message in multi languages
     * @param domain  Request url
     * @param params network params with JM format, you could check JM API document for params format
     * @param callback Post request result callback
     */
    public static boolean post(@NonNull Activity activity, @Nullable String domain, @Nullable String params, @NonNull NetworkCallback callback, boolean isJson) {
        if (TextUtils.isEmpty(domain)) {
            callback.onFailure(ErrorConsent.EMPTY_DOMAIN, ResourceUtils.getString(activity, ResourceName.HTTP_EMPTY_DOMAIN));
            return false;
        }
        HttpURLConnection conn = null;
        try {
            //build http url connection
            URL url = new URL(domain);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(NetworkConsent.HTTP_POST);
            conn.setReadTimeout(NetworkConsent.READ_Timeout);
            conn.setConnectTimeout(NetworkConsent.CONN_Timeout);
            if(isJson){
                conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            }else {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            }
            SharedPreferences sharedPreferences1 = activity.getSharedPreferences("phone_login_code",MODE_PRIVATE);
            String cookie = sharedPreferences1.getString("session","");
            conn.setRequestProperty("Cookie",cookie);
           // Logger.info("====================Cookie:"+cookie);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //set post request params
            if (!TextUtils.isEmpty(params)) {
                OutputStream out = null;
                try {
                    out = new BufferedOutputStream(conn.getOutputStream());
                } catch (IOException e) {
                    Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.STREAM_BUILD_EXCEPTION), e);
                }
                if (out != null) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
                    try {
                        writer.write(params);
                        writer.flush();
                    } catch (IOException e) {
                        Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.PARAM_READ_EXCEPTION), e);
                    }
                    try {
                        writer.close();
                    } catch (IOException e) {
                        Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.CLOSE_EXCEPTION_IS), "POST BufferWriter", e);
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.CLOSE_EXCEPTION_IS), "POST BufferedOutputStream", e);
                    }
                }
            }

            //connect url connection
            conn.connect();

            //start read data
            boolean isErrorStream = false;
            int byteLength = conn.getContentLength();
            Logger.verbose(Logger.GLOBAL_TAG, "byteLength: %d", byteLength);
            byte[] data = new byte[4]; //TODO should change to a bigger number, use space swtich time
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream input = null;
            int responseCode = conn.getResponseCode();
            try {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    input = conn.getInputStream();
                } else {
                    input = conn.getErrorStream();
                    isErrorStream = true;
                }
                int size;
                while ((size = input.read(data)) > 0) {
                    baos.write(data, 0, size);
                }
            } catch (IOException e) {
                Logger.error(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.RESPONSE_READ_EXCEPTION), e);
                callback.onFailure(ErrorConsent.RESPONSE_READ_EXCEPTION, ResourceUtils.getString(activity, ResourceName.RESPONSE_READ_EXCEPTION));
                return false;
            } finally {
                try {
                    if (input != null) input.close();
                } catch (IOException e) {
                    Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.CLOSE_EXCEPTION_IS), "Respond InputStream", e);
                }
                try {
                    baos.close();
                } catch (IOException e) {
                    Logger.warning(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.CLOSE_EXCEPTION_IS), "Reader ByteArrayOutputStream", e);
                }
            }

            // return result
            if (isErrorStream) {
                String message = "ErrorStream: " + new String(data, 0, data.length, StandardCharsets.UTF_8);
                callback.onFailure(responseCode, message);
                return false;
            } else {
                callback.onSuccess(ErrorConsent.SUCCESS, baos.toString());
                return true;
            }
        } catch (MalformedURLException e) {
            Logger.error(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.URL_IS), domain);
            Logger.error(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.URL_CREATE_EXCEPTION), e);
            callback.onFailure(ErrorConsent.URL_BUILD_EXCEPTION, ResourceUtils.getString(activity, ResourceName.URL_CREATE_EXCEPTION));
        } catch (ProtocolException e) {
            Logger.error(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.METHOD_SET_EXCEPTION), e);
            callback.onFailure(ErrorConsent.HTTP_METHOD_SET_EXCEPTION, ResourceUtils.getString(activity, ResourceName.METHOD_SET_EXCEPTION));
        } catch (IOException e) {
            Logger.error(Logger.GLOBAL_TAG, ResourceUtils.getString(activity, ResourceName.URL_CONNECTION_EXCEPTION), e);
            callback.onFailure(ErrorConsent.URL_CONNECTION_OPEN_EXCEPTION, ResourceUtils.getString(activity, ResourceName.URL_CONNECTION_EXCEPTION));
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return false;
    }
}
