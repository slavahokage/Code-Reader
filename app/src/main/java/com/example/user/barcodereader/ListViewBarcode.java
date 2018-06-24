package com.example.user.barcodereader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewBarcode extends AppCompatActivity {


    ArrayList<String> listData;
    ListView listView;
    int itemID = -1;
    DatabaseHelper mDatabaseHelper;
    Button clean;
    int counter;
    String result;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_barcode);
        mDatabaseHelper = new DatabaseHelper(this);
        listView = (ListView)findViewById(R.id.lvMain);
        clean = (Button) findViewById(R.id.clean);
        addToListView();
        cleanBtn();
    }

    public void addToListView(){
        //mDatabaseHelper.deleteAll();
        Cursor data = mDatabaseHelper.getDataBarcode();
        listData  = new ArrayList<>();

        while (data.moveToNext()){
                listData.add(data.getString(1));
        }

        final ListAdapter adapter = new ArrayAdapter<>(this,R.layout.dialog_choise,listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String name = parent.getItemAtPosition(position).toString();
                Cursor data = mDatabaseHelper.getItemIDBar(name);


                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID >-1){
                    //Toast.makeText(getActivity(),"id = "+itemID, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(ListViewBarcode.this, AlertDialog.THEME_HOLO_LIGHT);
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ListViewBarcode.this, R.layout.dialog_choise);
                    arrayAdapter.add("Посмотреть информацию");
                    arrayAdapter.add("Удалить");

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                String selectedFromList = (String )(listView.getItemAtPosition(position));
                                infoAboutBarcode(selectedFromList);

                            }

                            if (which == 1) {
                                mDatabaseHelper.deleteNameBar(itemID,name);
                                Toast.makeText(ListViewBarcode.this,"Удалено", Toast.LENGTH_SHORT).show();
                                addToListView();
                            }
                        }
                    });
                    builderSingle.show();

                }else {
                    Toast.makeText(ListViewBarcode.this,"no id "+itemID, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void cleanBtn(){
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteAll();
                addToListView();
            }
        });
    }


    public void infoAboutBarcode(String item){
        counter = Calculating.counter(item);
        result = Calculating.typeBarcode(Calculating.counter);

            Calculating.summaEven = Calculating.checkBoxEven(item);
            Calculating.summaEvenx3 = Calculating.x3(Calculating.summaEven);
            Calculating.summaOdd = Calculating.checkBoxOdd(item);
            Calculating.summaOddEven = Calculating.checkBoxOddEven(Calculating.summaEvenx3, Calculating.summaOdd);
            Calculating.last = Calculating.last(item);
            Calculating.answer = Calculating.test(Calculating.summaOddEven, Calculating.last);

            if (Calculating.answer) {
                answer = "Штрих код записон правильно!";
            } else {
                answer = "Штрих код записон неверно!";
            }


            if (Calculating.answer) {
                if (Calculating.counter == 8) {
                    Calculating.codeProduct = Calculating.isCodeProduct8(item);
                    Calculating.codeProd = Calculating.isCodeProd8(item);
                    Calculating.codeCountry = Calculating.isCodeCountry8(item);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 13) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(item);
                    Calculating.codeProd = Calculating.isCodeProd13(item);
                    Calculating.codeCountry = Calculating.isCodeCountry8(item);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 12) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(item);
                    Calculating.codeProd = Calculating.isCodeProd12(item);
                    Calculating.codeCountry = Calculating.isCodeCountry8(item);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 10) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(item);
                    Calculating.codeProd = Calculating.isCodeProd10(item);
                    Calculating.codeCountry = Calculating.isCodeCountry8(item);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 14) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(item);
                    Calculating.codeProd = Calculating.isCodeProd14(item);
                    Calculating.codeCountry = Calculating.isCodeCountry8(item);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                }

                Intent intent = new Intent(ListViewBarcode.this, InformationAboutBarcodeReader.class);
                intent.putExtra("barcode",item);
                intent.putExtra("counter",Calculating.counter);
                intent.putExtra("group",result);
                intent.putExtra("even",Calculating.summaEven);
                intent.putExtra("odd",Calculating.summaOdd);
                intent.putExtra("evenPlusOdd",Calculating.summaEvenx3);
                intent.putExtra("control",Calculating.last);
                intent.putExtra("product",Calculating.codeProduct);
                intent.putExtra("prod",Calculating.codeProd);
                intent.putExtra("codeCountry",Calculating.codeCountry);
                intent.putExtra("country",Calculating.discoverCountry);
                startActivity(intent);
            }

        }
    }
