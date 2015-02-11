package com.test.irkittest;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.irkittest.AsyncTaskHttpPost;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //各ボタンに送信する信号とクリックイベントを設定
        Button button = (Button)findViewById(R.id.buttonForward);
        button.setTag("");
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonBack);
        button.setTag("");
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonLeft);
        button.setTag("");
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonRight);
        button.setTag("");
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonVoiceRec);
        button.setTag(null);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String signal = (String)v.getTag();

        //送信する信号が未設定(null)の場合は、音声認識して制御する
        if(signal==null) {
            // 音声認識の画面を表示
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.voice_rec_guide));
            startActivityForResult(intent, REQUEST_CODE);
        }
        //送信する信号が設定されている場合は、制御する
        else{
            //制御する
            String uri = "http://192.168.0.20/messages";
            AsyncTaskHttpPost post = new AsyncTaskHttpPost(uri,mSendResultListener);
            String strJson = "{\"format\":\"raw\",\"freq\":38,\"data\":[1002,5794,1002,2911,1037,1037,935,2911,935,935,935,935,935,2911,935,2911,935,1037,935,1037,1037,1037,1037,1037,935,2911,1037,2911,1037,65535,0,65535,0,65535,0,11139,1002,5794,1002,2911,1037,1037,935,2911,935,1037,935,1037,935,2911,1037,2911,1037,1037,1037,1037,935,1037,935,935,935,2911,968,2911,968,65535,0,65535,0,65535,0,12782,1002,5794,1002,2911,1002,1002,1002,2911,1002,1002,1002,1002,1002,2911,1002,2911,1002,2911,1002,1002,1002,1002,1002,1002,1002,2911,1002,873,873,65535,0,65535,0,65535,0,11932,1037,5794,1002,2911,1002,1002,1002,2911,1002,1002,1002,1002,1002,2911,1002,2911,1002,2911,1002,1002,1002,1002,1002,1002,1002,2911,1002,904,1002,65535,0,65535,0,65535,0,11932,1002,5794,1002,2911,1037,1037,935,2911,1037,935,1037,1037,935,2911,1002,2911,1002,2911,1002,1002,1002,1002,1002,1002,1002,2911,1002,873,968,65535,0,65535,0,65535,0,13693,1002,5794,1002,2911,1002,1002,1002,2911,1002,1002,1002,1002,1002,2911,1002,2911,1002,1002,1002,1002,1002,1002,1002,1002,1002,2911,1002,2911,1002,65535,0,65535,0,65535,0,10047,1002,5794,1002,2911,1002,1002,1002,2911,1002,1002,1002,1002,1002,2911,1002,2911,1002,1002,1002,1002,1002,1002,1002,1002,1002,2911,1002,2911,1002,65535,0,65535,0,65535,0,11529,968,5794,1002,2911,968,968,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,968,968,968,968,65535,0,65535,0,65535,0,3834,1002,5794,1037,2911,1002,1002,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,1002,1002,904,904,65535,0,65535,0,65535,0,4554,1002,5794,1002,2911,1002,1002,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,1002,1002,904,904,65535,0,65535,0,65535,0,3704,1002,5794,1037,2911,968,968,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,2911,968,968,968,968,968,65535,0,65535,0,65535,0,4554,1037,5794,1037,2911,1002,1002,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,1002,1002,873,968,65535,0,65535,0,65535,0,3834,1002,5794,1037,2911,1002,1002,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,1002,1002,904,904,65535,0,65535,0,65535,0,4554,1002,5794,1002,2911,1037,1037,935,2911,935,2911,935,2911,1037,2813,1037,2813,1037,2813,1037,2813,1037,2813,1037,2813,1037,1037,935,935,935,65535,0,65535,0,65535,0,3704,1002,5794,1002,2911,1037,1037,1037,2911,1037,2911,1037,2911,1037,2911,1037,2911,1037,2911,1037,2911,1037,2911,1037,2911,1037,1037,935,935,935,65535,0,65535,0,65535,0,4554,1002,5794,1002,2911,1002,1002,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,2911,1002,1002,1002,904,904]}";
            post.execute(strJson);
        }
    }

    // 音声認識の画面が閉じられると呼び出される
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 音声認識が成功した場合は制御する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";

            // 結果文字列リスト
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //文字列が複数あった場合に結合する
            for (int i = 0; i< results.size(); i++) {
                resultsString += results.get(i);
            }

            // トーストを使って結果を表示
            Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();

            //音声認識結果から制御する
            parse(resultsString);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //音声認識結果から制御する
    private void parse(String msg) {

        Button button = null;

        //前
        if (msg.contains(getString(R.string.voice_rec_forward))){
            button = (Button)findViewById(R.id.buttonForward);
        }
        //後ろ
        else if(msg.contains(getString(R.string.voice_rec_back))){
            button = (Button)findViewById(R.id.buttonBack);
        }
        //左
        else if(msg.contains(getString(R.string.voice_rec_left))){
            button = (Button)findViewById(R.id.buttonLeft);
        }
        //右
        else if(msg.contains(getString(R.string.voice_rec_right))){
            button = (Button)findViewById(R.id.buttonRight);
        }

        if(button != null) {
            //ボタンのクリックイベントを発行
            button.performClick();
        }
    }

    private AsyncTaskHttpPost.SendResultListener mSendResultListener = new AsyncTaskHttpPost.SendResultListener() {
        @Override
        public void onSendResult(boolean isSend) {
            Log.v(TAG, "onSendResult = " + isSend);
        }
    };

}
