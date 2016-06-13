package com.andfixtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Intent patchDownloadIntent;
    private TextView textView;
    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.test_tv);
        textView.setText("Repair Success!/n 修复成功！！！！");
        testBtn = (Button)findViewById(R.id.test_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchDownloadIntent = new Intent("com.andfixtest.PatchDownloadIntentService");
                patchDownloadIntent.putExtra("url", "http://172.16.24.159:8080/Json/Test/out.apatch");
                startService(patchDownloadIntent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(patchDownloadIntent != null){
            stopService(patchDownloadIntent);
        }
    }
}
