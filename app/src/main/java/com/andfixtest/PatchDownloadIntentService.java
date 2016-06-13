package com.andfixtest;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wukd on 2016/6/12.
 */
public class PatchDownloadIntentService extends IntentService{
    private int fileLength,downloadLength;


    public PatchDownloadIntentService(){
        super("PatchDownloadIntentService");
    }
    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               Context#startService(Intent)}.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null){
            String downloadUrl = intent.getStringExtra("url");
            if (downloadUrl !=null && !TextUtils.isEmpty(downloadUrl)) {
                downloadPatch(downloadUrl);
            }

        }
    }

    private void downloadPatch(String downloadUrl){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (!dir.exists()){
            dir.mkdir();
        }
        File patchFile = new File(dir,"out.apatch");
        downloadFile(downloadUrl, patchFile);
    }
    private void downloadFile(String downloadUrl, File file){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStream ips = null;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(3000);
            fileLength = Integer.valueOf(huc.getHeaderField("Content-Length"));
            ips = huc.getInputStream();
            int hand = huc.getResponseCode();
            if (hand == 200) {
                byte[] buffer = new byte[8192];
                int len = 0;
                while ((len = ips.read(buffer)) != -1) {
                    if (fos != null) {
                        fos.write(buffer, 0, len);
                    }
                    downloadLength = downloadLength + len;
                }
            } else {
                //Log.e("response code: " + hand);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (ips != null) {
                    ips.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
