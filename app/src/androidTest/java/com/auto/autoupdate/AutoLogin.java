/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.auto.autoupdate;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AutoLogin {
    String TAG = "ToanTQ";
    public static UiDevice mDevice;
    static int step = 0;

    boolean isFinish = false;

    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(getInstrumentation());

        try {
            mDevice.wakeUp();
            Log.d("ToanTQ", "wakeUp");

        } catch (Exception e) {
            Log.d("ToanTQ", "Exception: " + e.getMessage());
        }
      //  oppenAppActivity();
    }

    @Test
    public void runTesst() {

        runAllTime();
        // checkView();
    }

    public void runAllTime() {

            Log.d("ToanTQ", "start auto UPDATE TEST APK: ");
            new InstallSupport(getApplicationContext()).startUpdateTestApk();
            sleep(ranInt(10000, 15000));

            UiObject2 clickInstall = mDevice.findObject(By.text("Cài đặt"));

            if (clickInstall != null) {

                clickInstall.click();
                Log.d("ToanTQ", "Click cài đặt");
                sleep(10000);
                UiObject2 clickXong = mDevice.findObject(By.text("Xong"));
                if (clickXong != null) {
                    Log.d("ToanTQ", "Click xong");
                    clickXong.click();

                } else {
                    Log.d("ToanTQ", "Không tìm thấy nút xong");

                }
                //  checkView();
                sleep(7000);
                UiObject2 clickChapNhan = mDevice.findObject(By.text("CHẤP NHẬN"));
                if (clickChapNhan != null) {
                    clickChapNhan.click();
                    Log.d("ToanTQ", "Click Chấp nhận");
                }

                sleep(3000);
                oppenMyApp();
                Log.d("ToanTQ", "open app");
                sleep(7000);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                // checkView();
                UiObject2 clickStart = mDevice.findObject(By.text("START AUTO"));
                if (clickStart != null) {
                    clickStart.click();
                    sleep(3000);
                } else {
                    Log.d("ToanTQ", "Không tìm thấy nút Start");

                }

            } else {
                Log.d("ToanTQ", "Có lỗi khi Click cài đặt");
            }

    }

    public void autoView() {
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);



        /*    Log.d("ToanTQ", "start auto UPDATE APP APK: ");
            new InstallApp(getApplicationContext()).startUpdateAppApk();
            sleep(ranInt(10000, 15000));

            clickInstall = mDevice.findObject(By.text("Cài đặt"));

            if (clickInstall != null) {
                clickInstall.click();
                Log.d("ToanTQ", "Click cài đặt");
                sleep(ranInt(15000, 20000));
                UiObject2 clickXong = mDevice.findObject(By.text("Xong"));
                clickXong.click();
                Log.d("ToanTQ", "Click xong");
                sleep(ranInt(5000, 7000));
                oppenMyApp();
                Log.d("ToanTQ", "open app");
                sleep(ranInt(5000, 7000));
                UiObject2 clickStart = mDevice.findObject(By.text("START AUTO"));
                clickStart.click();

            } */



        sleep(ranInt(5000, 10000));
        // autoView(schedule);
    }

    public static void checkView() {
        Log.d("checkView", "processView: " + step);
        try {
            List<UiObject2> listView = mDevice.findObjects(By.enabled(true));
            Log.d("checkView", "----------------------View-------------------------");
            Log.d("checkView", "list: " + listView.size());
            for (int i = 0; i < listView.size(); i++) {
                UiObject2 view = listView.get(i);
                try {
                    Log.d("checkView", i + " class i thu: " + i);
                    Log.d("checkView", i + " getClassName: " + view.getClassName());
                    Log.d("checkView", i + " getResourceName: " + view.getResourceName());
                    Log.d("checkView", i + " getText class i thu: " + i + " " + view.getText());
                    Log.d("checkView", i + " getContentDescription: " + view.getContentDescription());
                    Log.d("checkView", "-----------------------------------------------\n");
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            Log.d("checkView", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
        sleep(ranInt(10000, 11000));
        //checkView();
    }

    public void sleep() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
    }
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {

        }
    }
    
    public void oppenAppActivity() {
        mDevice.pressHome();
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.startup.shoppyauto");
        //    .getLaunchIntentForPackage("com.android.chrome");
        if (intent == null) {
            oppenMainActivity("Chưa cài đặt auto");
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public static int ranInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public void oppenMyApp() {
        mDevice.pressHome();
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.startup.shoppyauto");
        //    .getLaunchIntentForPackage("com.android.chrome");
        if (intent == null) {
            oppenMainActivity("Không có my app");
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public void oppenMainActivity(String sms) {
        Log.d("ToanTQ", "sms: " + sms);
        isFinish = true;

        mDevice.pressHome();
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
        intent.putExtra("sms", sms);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
