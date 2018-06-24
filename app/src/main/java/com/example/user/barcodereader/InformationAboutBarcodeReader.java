package com.example.user.barcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.user.barcodereader.Calculating.barcode;

public class InformationAboutBarcodeReader extends AppCompatActivity {

    TextView yourBarcode;
    TextView countOfNumbers;
    TextView barcodeGroup;
    TextView barcodeEven;
    TextView barcodeOdd;
    TextView summaOfEvenAndOdd;
    TextView controlNumber;
    TextView codeOfProduct;
    TextView codeOfProd;
    TextView codeOfCountry;
    TextView lastSum;
    TextView result;
    TextView country;


    public InformationAboutBarcodeReader() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_about_barcode_reader);
        yourBarcode = (TextView) findViewById(R.id.textView21);
        countOfNumbers = (TextView) findViewById(R.id.textView22);
        barcodeGroup = (TextView) findViewById(R.id.textView23);
        barcodeEven = (TextView) findViewById(R.id.textView28);
        barcodeOdd = (TextView) findViewById(R.id.textView29);
        summaOfEvenAndOdd = (TextView) findViewById(R.id.textView35);
        controlNumber = (TextView) findViewById(R.id.textView36);
        lastSum = (TextView) findViewById(R.id.textView30);
        result = (TextView) findViewById(R.id.textView31);
        codeOfProduct = (TextView) findViewById(R.id.textView18);
        codeOfProd = (TextView) findViewById(R.id.textView33);
        country = (TextView) findViewById(R.id.textView32);
        codeOfCountry = (TextView) findViewById(R.id.textView34);


        Intent intent = getIntent();

        String barcode = intent.getStringExtra("barcode");
        yourBarcode.setText("Ваш штрихкод: "+ barcode);

        countOfNumbers.setText("Число разрядов: "+ Calculating.counter);

        String group = intent.getStringExtra("group");
        barcodeGroup.setText(group);

        barcodeEven.setText("Сумма четных: "+ Calculating.summaEven);

        barcodeOdd.setText("Сумма нечетных "+ Calculating.summaOdd);


        summaOfEvenAndOdd.setText("Сумма нечетных и (сумма четных*3) "+ Calculating.summaOddEven);

        controlNumber.setText("Контрольная цифра: "+ Calculating.last);

        lastSum.setText("(Конечная сумма + Контрольная цифра)%10 = 0");

        result.setText("Поэтому штрихкод записан правильно");

        codeOfProduct.setText("Код товара: "+ Calculating.codeProduct);

        codeOfProd.setText("Код производителя: "+ Calculating.codeProd);

        country.setText("Страна: "+ Calculating.discoverCountry);

        codeOfCountry.setText("Код страны: "+ Calculating.codeCountry);
    }
}
