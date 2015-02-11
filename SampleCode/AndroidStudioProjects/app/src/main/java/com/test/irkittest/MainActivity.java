package com.test.irkittest;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import com.test.irkittest.util.Utility;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String IRKIT_IP_ADDRESS = "192.168.0.20";
    private static final String CAMERA_IP_ADDRESS = "192.168.0.17";
    private static final String CAMERA_USERNAME = "test";
    private static final String CAMERA_PASSWORD = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onClick(View v) {
        String signal = (String) v.getTag();

        //送信する信号が未設定(null)の場合は、音声認識して制御する
        if (signal == null) {
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
        else {
            //制御する
            AsyncTaskHttpPost post = new AsyncTaskHttpPost(
                    "http://" + IRKIT_IP_ADDRESS + "/messages", mSendResultListener);
            post.execute(signal);
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
            for (int i = 0; i < results.size(); i++) {
                resultsString += results.get(i) + "\n";
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
        if (msg.contains(getString(R.string.voice_rec_forward))) {
            button = (Button) findViewById(R.id.buttonForward);
        }
        //後ろ
        else if (msg.contains(getString(R.string.voice_rec_back))) {
            button = (Button) findViewById(R.id.buttonBack);
        }
        //左
        else if (msg.contains(getString(R.string.voice_rec_left))) {
            button = (Button) findViewById(R.id.buttonLeft);
        }
        //右
        else if (msg.contains(getString(R.string.voice_rec_right))) {
            button = (Button) findViewById(R.id.buttonRight);
        }
        //腕
        else if (msg.contains(getString(R.string.voice_rec_arm))) {
            button = (Button) findViewById(R.id.buttonArm);
        }

        if (button != null) {
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

    /**
     * 各Viewの初期設定を行う
     */
    private void initViews(){
        //各ボタンに送信する信号とクリックイベントを設定
        Button button = (Button) findViewById(R.id.buttonForward);
        button.setTag(Utility.getTagFromRes(getApplicationContext(), R.raw.forward));
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.buttonBack);
        button.setTag(Utility.getTagFromRes(getApplicationContext(), R.raw.back));
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.buttonLeft);
        button.setTag(Utility.getTagFromRes(getApplicationContext(), R.raw.left));
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.buttonRight);
        button.setTag(Utility.getTagFromRes(getApplicationContext(), R.raw.right));
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.buttonArm);
        button.setTag(Utility.getTagFromRes(getApplicationContext(), R.raw.arm));
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.buttonVoiceRec);
        button.setTag(null);
        button.setOnClickListener(this);

        //カメラに接続
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler,
                    String host, String realm) {
                handler.proceed(CAMERA_USERNAME, CAMERA_PASSWORD);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollbarOverlay(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("http://" + CAMERA_IP_ADDRESS + ":8080");
    }

}
