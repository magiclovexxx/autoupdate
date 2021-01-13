package com.auto.autoupdate;

import android.app.ActivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity<deviceId> extends AppCompatActivity {

    Button btnStart, btnUpdateApk, btnUninstallApk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=findViewById(R.id.btnStart);
        btnUpdateApk=findViewById(R.id.btnUpdateApk);
        btnUninstallApk=findViewById(R.id.btnUninstallApk);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new InstallSupport(getApplicationContext()).startUpdateTestApk();
//                int PID1= android.os.Process.getUidForName("com.startup.shoppyauto");
//                android.os.Process.killProcess(PID1);
                   // callAuto();
            }
        });

        btnUpdateApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InstallSupport(getApplicationContext()).startUpdateApk();
            }
        });

        btnUninstallApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unInstallApp("com.startup.shoppyauto", "Auto app");
            }
        });


        checkQuyen();
    }

    public void  checkQuyen(){
        PermissionSupport.getInstall(this).requestPermissionStore();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Khi xin quyền xong nó trả về đây

        checkQuyen();
    }

    private void unInstallApp(String packageName, String appName) {
        Log.d("SonLv", appName + ": unInstallApp: " + packageName);
        try {
            showToast("Bắt đầu gỡ ứng dụng " + appName);
            Process p = new ProcessBuilder(new String[0]).redirectErrorStream(true).command(new String[]{"su"}).start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            OutputStream out = p.getOutputStream();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("pm uninstall " + packageName + "\n");
            out.write(stringBuilder.toString().getBytes("UTF-8"));
            out.flush();
            out.write("exit\n".getBytes("UTF-8"));
            out.flush();
            out.close();
            p.waitFor();

            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                Log.d("SonLv", "line: " + line);

            }
            showToast("Gỡ thành công " + appName);
            Log.d("SonLv", "Gỡ thành công " + appName);

        } catch (Exception e) {
            Log.d("SonLv", "Exception: " + e.getMessage());
            e.printStackTrace();
            showToast("Có lỗi khi gỡ " + appName);
            Log.d("SonLv", "Có lỗi khi gỡ " + appName);
        }
    }

    public void callAuto(){
        try {
            OutputStream out = new ProcessBuilder(new String[0]).redirectErrorStream(true).command(new String[]{"su"}).start().getOutputStream();
            out.write("am instrument -w -r   -e debug false -e class 'com.auto.autoupdate.AutoLogin' com.auto.autoupdate.test/androidx.test.runner.AndroidJUnitRunner\n".getBytes("UTF-8"));
            out.flush();
            out.close();
            showToast("Bắt đầu Update");
        } catch (Exception e) {
            Log.d("ToanTQ", "Exception: " + e.getMessage());
            showToast("Có lỗi khi Auto");
        }
    }

    public  void  showToast(String sms){
        Toast.makeText(this,sms,Toast.LENGTH_LONG).show();
    }

}
