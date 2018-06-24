package com.example.user.barcodereader;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by USER on 28.04.2018.
 */

public class Fragment1 extends Fragment {


    EditText editBarcode;
    Button buttonCheck;
    Button buttonFind;
    Button buttonInfo;
    Button buttonSave;
    Button buttonOpenCollection;
    ImageButton imageButtonScan;
    ImageView imageViewAnswer;
    TextView textViewAnswerOnBarcode;
    String barcode;
    int counter;
    String result;
    String answer;
    ZXingScannerView zXingScannerView;
    Button cancel;
    TextView textView;
    DatabaseHelper mDatabaseHelper;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null);
        editBarcode = (EditText) v.findViewById(R.id.editBarcode);
        textViewAnswerOnBarcode = (TextView) v.findViewById(R.id.textViewAnswerOnBarcode);
        buttonCheck = (Button) v.findViewById(R.id.buttonCheck);
        buttonFind = (Button) v.findViewById(R.id.buttonFind);
        buttonInfo = (Button) v.findViewById(R.id.buttonInfo);
        buttonSave  = (Button) v.findViewById(R.id.buttonSave);
        buttonOpenCollection  = (Button) v.findViewById(R.id.buttonOpen);
        imageButtonScan = (ImageButton) v.findViewById(R.id.imageButtonScan);
        imageViewAnswer = (ImageView) v.findViewById(R.id.imageViewAnswer);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        addListenerOnButton();
        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (zXingScannerView != null) {
            zXingScannerView.stopCamera();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (zXingScannerView != null) {
            zXingScannerView.startCamera();
        }
    }



    public void addListenerOnButton() {
        imageButtonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Camera.class);
                startActivityForResult(intent,1);
            }
        });

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoAboutBarcode();
            }
        });

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcode = editBarcode.getText().toString();
                Calculating.barcode = barcode;
                counter = Calculating.counter(barcode);
                if (Calculating.counter == 7 || Calculating.counter == 12 || Calculating.counter == 11 || Calculating.counter == 13 || Calculating.counter == 9) {
                    dialogForFindBtn();
                }
                else {
                  dialogForWrongResult();
                }
            }

        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBarcode()) {
                    if (Calculating.answer) {
                        if (Calculating.counter == 8) {
                            Calculating.codeProduct = Calculating.isCodeProduct8(barcode);
                            Calculating.codeProd = Calculating.isCodeProd8(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 13) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd13(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 12) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd12(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 10) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd10(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 14) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd14(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        }
                        textViewAnswerOnBarcode.setText("Правильный штрих код!");
                        imageViewAnswer.setImageResource(R.drawable.galochkaznak);
                        textViewAnswerOnBarcode.setVisibility(View.VISIBLE);
                        imageViewAnswer.setVisibility(View.VISIBLE);
                    }
                    AddData(editBarcode.getText().toString());
                    /*Intent intent = new Intent(getActivity(), ListViewBarcode.class);
                    startActivity(intent);*/
                }else {
                    if (result.equals("Штрих код ни относится не к EAN-8, не к EAN-13, не к UPC-10, не к UPC-12, не к UPC-14.")) {
                        textViewAnswerOnBarcode.setVisibility(View.INVISIBLE);
                        imageViewAnswer.setVisibility(View.INVISIBLE);
                        dialogForWrongResult();
                    }else {
                        if (!Calculating.answer) {
                            imageViewAnswer.setImageResource(R.drawable.clipartcrosswrongpngtransparentimagespluspng);
                            textViewAnswerOnBarcode.setText("Неправильный штрих код!");
                            textViewAnswerOnBarcode.setVisibility(View.VISIBLE);
                            imageViewAnswer.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Неправильные штрих коды нельзя добавить в коллекцию", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }
        });

        buttonOpenCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListViewBarcode.class);
                startActivity(intent);
            }
        });


        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (textViewAnswerOnBarcode.getText().toString().equals("Правильный штрих код!") && textViewAnswerOnBarcode.getVisibility()==View.VISIBLE) {
                if (checkBarcode()) {
                    if (Calculating.answer) {
                        if (Calculating.counter == 8) {
                            Calculating.codeProduct = Calculating.isCodeProduct8(barcode);
                            Calculating.codeProd = Calculating.isCodeProd8(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 13) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd13(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 12) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd12(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 10) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd10(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        } else if (Calculating.counter == 14) {
                            Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                            Calculating.codeProd = Calculating.isCodeProd14(barcode);
                            Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                            Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                        }
                        textViewAnswerOnBarcode.setText("Правильный штрих код!");
                        imageViewAnswer.setImageResource(R.drawable.galochkaznak);
                        textViewAnswerOnBarcode.setVisibility(View.VISIBLE);
                        imageViewAnswer.setVisibility(View.VISIBLE);
                    }
                    Intent intent = new Intent(getActivity(), InformationAboutBarcodeReader.class);
                    intent.putExtra("barcode",barcode);
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
                }else {
                    dialogForInfoBtn();
                }
            }
        });

    }

    public boolean checkBarcode() {
        barcode = editBarcode.getText().toString();
        counter = Calculating.counter(barcode);
        result = Calculating.typeBarcode(Calculating.counter);

        if (result.equals("Штрих код ни относится не к EAN-8, не к EAN-13, не к UPC-10, не к UPC-12, не к UPC-14.")) {
            return  false;
        }else {
            Calculating.summaEven = Calculating.checkBoxEven(barcode);
            Calculating.summaEvenx3 = Calculating.x3(Calculating.summaEven);
            Calculating.summaOdd = Calculating.checkBoxOdd(barcode);
            Calculating.summaOddEven = Calculating.checkBoxOddEven(Calculating.summaEvenx3, Calculating.summaOdd);
            Calculating.last = Calculating.last(barcode);
            Calculating.answer = Calculating.test(Calculating.summaOddEven, Calculating.last);

            if (Calculating.answer) {
                return true;
            } else {
                return false;
            }
        }
    }


    public void infoAboutBarcode(){
        barcode = editBarcode.getText().toString();
        Log.i("barcode: ", barcode);
        counter = Calculating.counter(barcode);
        Log.i("колличество разрядов: ", Integer.toString(counter));
        result = Calculating.typeBarcode(Calculating.counter);
        Log.i("тип штрихового кода: ", result);

        if (result.equals("Штрих код ни относится не к EAN-8, не к EAN-13, не к UPC-10, не к UPC-12, не к UPC-14.")) {
            textViewAnswerOnBarcode.setVisibility(View.INVISIBLE);
            imageViewAnswer.setVisibility(View.INVISIBLE);
            dialogForWrongResult();


        } else {
            Calculating.summaEven = Calculating.checkBoxEven(barcode);
            Calculating.summaEvenx3 = Calculating.x3(Calculating.summaEven);
            Calculating.summaOdd = Calculating.checkBoxOdd(barcode);
            Calculating.summaOddEven = Calculating.checkBoxOddEven(Calculating.summaEvenx3, Calculating.summaOdd);
            Calculating.last = Calculating.last(barcode);
            Calculating.answer = Calculating.test(Calculating.summaOddEven, Calculating.last);

            if (Calculating.answer) {
                answer = "Штрих код записон правильно!";
            } else {
                answer = "Штрих код записон неверно!";
            }


            if (Calculating.answer) {
                if (Calculating.counter == 8) {
                    Calculating.codeProduct = Calculating.isCodeProduct8(barcode);
                    Calculating.codeProd = Calculating.isCodeProd8(barcode);
                    Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 13) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                    Calculating.codeProd = Calculating.isCodeProd13(barcode);
                    Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 12) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                    Calculating.codeProd = Calculating.isCodeProd12(barcode);
                    Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 10) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                    Calculating.codeProd = Calculating.isCodeProd10(barcode);
                    Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                } else if (Calculating.counter == 14) {
                    Calculating.codeProduct = Calculating.isCodeProduct13(barcode);
                    Calculating.codeProd = Calculating.isCodeProd14(barcode);
                    Calculating.codeCountry = Calculating.isCodeCountry8(barcode);
                    Calculating.discoverCountry = Calculating.isDiscoverCountry(Calculating.codeCountry);
                }
                textViewAnswerOnBarcode.setText("Правильный штрих код!");
                Log.i("Штриховой код является:"," правильным");
                imageViewAnswer.setImageResource(R.drawable.galochkaznak);
                textViewAnswerOnBarcode.setVisibility(View.VISIBLE);
                imageViewAnswer.setVisibility(View.VISIBLE);
            } else {
                imageViewAnswer.setImageResource(R.drawable.clipartcrosswrongpngtransparentimagespluspng);
                textViewAnswerOnBarcode.setText("Неправильный штрих код!");
                Log.i("Штриховой код является:"," неверным");
                textViewAnswerOnBarcode.setVisibility(View.VISIBLE);
                imageViewAnswer.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }


        String barcode = data.getStringExtra("editBarcode");
        editBarcode.setText(barcode);
        infoAboutBarcode();
    }

    public void dialogForFindBtn(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_for_right_answer_find_button,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        textView = (TextView) view.findViewById(R.id.textViewForRightButtonFind);
        textView.setText("Контрольный разряд = "+(Calculating.getLast = Calculating.isGetLast(Calculating.barcode)));
        Log.i("Контрольный разяряд :",String.valueOf(Calculating.getLast));
        dialog.show();
        cancel = (Button) view.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


    public void AddData(String newEntry){
       boolean insertData = mDatabaseHelper.addData2(newEntry);
        if(insertData){
            Toast.makeText(getActivity(),"Штрих код  добавлен в коллекцию", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(),"Не получилось добавить", Toast.LENGTH_LONG).show();
        }
    }

    public void dialogForWrongResult(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_for_wrong_format,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        cancel = (Button) view.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void dialogForInfoBtn(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_for_info,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        cancel = (Button) view.findViewById(R.id.close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

}

