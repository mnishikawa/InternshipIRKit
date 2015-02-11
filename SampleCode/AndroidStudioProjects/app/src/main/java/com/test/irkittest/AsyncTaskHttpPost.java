package com.test.irkittest;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AsyncTaskHttpPost extends AsyncTask<String, Void, Boolean> {
    private final static String TAG = AsyncTaskHttpPost.class.getSimpleName();

    private String mUri = null;

    private SendResultListener mListener=null;

    public interface SendResultListener{
        public void onSendResult(boolean isSend);
    }

    public AsyncTaskHttpPost(String uri,SendResultListener listener) {
        mUri = uri;
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Log.v(TAG, "doInBackground");

        if (params == null || params.length == 0) {
            Log.e(TAG, "params==null || params.length == 0");
            return false;
        }

        String strJson = params[0];
        Log.v(TAG, "strJson = " + strJson);

        if (mUri == null) {
            Log.e(TAG, "mUri == null");
            return false;
        }

        {
            // HTTPリクエストの構築
            HttpPost request = new HttpPost(mUri);

            try {
                StringEntity body = new StringEntity(strJson);
                request.addHeader("Content-type", "application/json");
                request.setEntity(body);
                Log.v(TAG, "setEntity done");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            // HTTPリクエスト発行
            AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android HTTP Client Test");
            HttpResponse response = null;

            boolean isSend = false;

            try {
                response = httpClient.execute(request);

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Log.v(TAG, "send done");
                    isSend = true;
                } else {
                    Log.v(TAG, "send error");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (httpClient != null) {
                httpClient.close();
            }

            return isSend;
        }
    }

    @Override
    protected void onPostExecute(Boolean isSend) {
        super.onPostExecute(isSend);
        Log.v(TAG, "onPostExecute bool = " + isSend);

        if(mListener != null){
            mListener.onSendResult(isSend);
        }
    }
}
