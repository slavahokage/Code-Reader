package com.example.user.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Camera extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callCamera();
    }


    @Override
    public void handleResult(final Result result) {

        zXingScannerView.resumeCameraPreview(this);
        final Intent intent = new Intent();


        char[] text = result.getText().toCharArray();
        boolean flag = true;

        for (char aText : text) {
            if (!((int) aText > 47 && (int) text[0] < 58)) {
                flag = false;
            }
        }

        if(flag) {
            intent.putExtra("editBarcode", result.getText());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    public void callCamera() {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

}
