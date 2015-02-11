package com.test.irkittest.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by skobayashi1 on 15/02/12.
 */
public class Utility {
    /**
     * res/rawのfileをString形式で読み出す<br>
     * 複数行のfileには対応しない
     *
     * @param resId R.raw.xxxx
     * @return
     */
    public static String getTagFromRes(Context context, int resId) {
        Resources resource = context.getResources();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String line = null;
        try {
            try {
                inputStream = resource.openRawResource(resId);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                line = bufferedReader.readLine();
            }finally {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStream != null) inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return line;
    }

}
