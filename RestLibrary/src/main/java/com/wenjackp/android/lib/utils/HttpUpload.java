package com.wenjackp.android.lib.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUpload extends Thread {

    private String url;
    private String boundary = "ahhjifeohiawf";
    String twoHyphens = "--";
    private String lineEnd = "\r\n";
    private String code;
    private String time;
    private String sign;
    private String UserID;
    private String fileName;
    private File mFile;

    public HttpUpload(String url, String code, String time, String sign, String UserID, String fileName, File mFile) {
        this.code = code;
        this.time = time;
        this.sign = sign;
        this.UserID = UserID;
        this.fileName = fileName;
        this.mFile = mFile;
        this.url = url;
    }

    @Override
    public void run() {

        try {

            HttpURLConnection mHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.setDoOutput(true);


            mHttpURLConnection.setRequestMethod("POST");
            mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
            mHttpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream mDataOutputStream = new DataOutputStream(mHttpURLConnection.getOutputStream());

            mDataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"UserID\"" + lineEnd + lineEnd);
            mDataOutputStream.writeBytes(UserID);
            mDataOutputStream.writeBytes(lineEnd);

            mDataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"code\"" + lineEnd + lineEnd);
            mDataOutputStream.writeBytes(code);
            mDataOutputStream.writeBytes(lineEnd);

            mDataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"sign\"" + lineEnd + lineEnd);
            mDataOutputStream.writeBytes(sign);
            mDataOutputStream.writeBytes(lineEnd);

            mDataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"format\"" + lineEnd + lineEnd);
            mDataOutputStream.writeBytes("json");
            mDataOutputStream.writeBytes(lineEnd);


            mDataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"time\"" + lineEnd + lineEnd);
            mDataOutputStream.writeBytes(time);
            mDataOutputStream.writeBytes(lineEnd);


//
//		mDataOutputStream.writeBytes(twoHyphens+boundary+lineEnd);
//		mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"checkstate\"" + lineEnd+lineEnd);
//		mDataOutputStream.writeBytes(checkstate);
//		mDataOutputStream.writeBytes(lineEnd);
//
            mDataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            mDataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + fileName + "\"" + lineEnd + lineEnd);
            mDataOutputStream.writeBytes("Content-Type: application/octet-stream");
            mDataOutputStream.writeBytes(lineEnd);

            FileInputStream mFileOutputStream = new FileInputStream(mFile);
            byte[] buffer = new byte[2048];
            int count = 0;
            int length = 0;

            while ((length = mFileOutputStream.read(buffer)) != -1) {
                mDataOutputStream.write(buffer);
                count += length;
            }

            mFileOutputStream.close();
            mDataOutputStream.writeBytes(lineEnd);
            mDataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            mDataOutputStream.flush();

            if (mHttpURLConnection.getResponseCode() == 200) {
                BufferedReader mRead = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
                StringBuffer stb = new StringBuffer();
                String line;
                while ((line = mRead.readLine()) != null) {
                    stb.append(line);
                }

                System.out.println(stb.toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("CAONIMA" + ex.toString());
        }

        super.run();
    }
}
