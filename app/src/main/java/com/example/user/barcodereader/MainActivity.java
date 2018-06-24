package com.example.user.barcodereader;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Fragment frag1;
    Fragment frag2;
    FragmentTransaction fragmentTransaction;
    Button barcodeBtn;
    Button QRBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        //System.out.println(Arrays.asList(fingerprints));
        barcodeBtn = (Button) findViewById(R.id.barcodeBtn);
        QRBtn = (Button) findViewById(R.id.QrBtn);
        frag1 = new Fragment1();
        frag2 = new Fragment2();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frgmCont, frag1);
        fragmentTransaction.commit();
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        QRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frgmCont, frag2);
                fragmentTransaction.commit();
            }
        });

        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frgmCont, frag1);
                fragmentTransaction.commit();

            }
        });

    }
}
