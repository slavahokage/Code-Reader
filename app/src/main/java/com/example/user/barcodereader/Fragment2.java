package com.example.user.barcodereader;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import java.util.ArrayList;

/**
 * Created by USER on 28.04.2018.
 */

public class Fragment2 extends Fragment {
    TextView textView;
    ImageButton imageButton;
    Button clean;
    Button cancel;
    EditText editText;
    ListView listView;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> listData;
    int itemID = -1;
    private final String[] scope = new String[]{VKScope.WALL};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2,null);
        textView = (TextView)v.findViewById(R.id.textViewQR);
        imageButton = (ImageButton) v.findViewById(R.id.imageButtonScan);
        listView = (ListView) v.findViewById(R.id.frameForList);
        clean = (Button) v.findViewById(R.id.close);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        addToListView();
        cameraBtn();
        cleanBtn();
        return v;
    }

    public void addToListView(){
        Cursor data = mDatabaseHelper.getData();
        listData  = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        final ListAdapter adapter = new ArrayAdapter<>(getActivity(),R.layout.dialog_choise,listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String name = parent.getItemAtPosition(position).toString();
                Cursor data = mDatabaseHelper.getItemID(name);

                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }


                if(itemID >-1){
                    //Toast.makeText(getActivity(),"id = "+itemID, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dialog_choise);
                    arrayAdapter.add("Открыть сылку");
                    arrayAdapter.add("Удалить");
                    arrayAdapter.add("Опубликовать на стене в ВК");
                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                String selectedFromList = (String )(listView.getItemAtPosition(position));
                                Intent browserIntent = new
                                        Intent(Intent.ACTION_VIEW, Uri.parse(selectedFromList));
                                startActivity(browserIntent);
                            }

                            if (which == 1) {
                                mDatabaseHelper.deleteName(itemID, name);
                                Toast.makeText(getActivity(), "Удалено", Toast.LENGTH_SHORT).show();
                                addToListView();
                            }

                            if (which == 2) {
                                final String selectedFromList = (String )(listView.getItemAtPosition(position));
                                android.support.v7.app.AlertDialog.Builder builderForVK = new android.support.v7.app.AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                View view = inflater.inflate(R.layout.custom_edit_for_vk, null);
                                builderForVK.setView(view);
                                final android.support.v7.app.AlertDialog dialogForVK = builderForVK.create();
                                dialogForVK.show();
                                cancel = (Button) view.findViewById(R.id.close);
                                editText = (EditText) view.findViewById(R.id.editVK);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!VKSdk.isLoggedIn()) {
                                            VKSdk.login(getActivity(), scope);
                                        }

                                        String text = editText.getText().toString();
                                        final VKShareDialogBuilder builder = new VKShareDialogBuilder();
                                        builder.setText(text+"\n"+"\n"+"\n"+"*Опубликовано с помощью Code Reader*");
                                        //VKPhotoArray photos = new VKPhotoArray();
                                       //photos.add(new VKApiPhoto("photo-47200925_314622346"));
                                       // builder.setUploadedPhotos(photos);
                                        builder.setAttachmentLink("Сыслка:",
                                               selectedFromList);
                                        builder.setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                                            @Override
                                            public void onVkShareComplete(int postId) {
                                                // recycle bitmap if need
                                                Toast.makeText(getActivity(), "Успешно опубликовано", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onVkShareCancel() {
                                                // recycle bitmap if need
                                               // Toast.makeText(getActivity(), "Загрузка приостановлина", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onVkShareError(VKError error) {
                                                // recycle bitmap if need
                                                // Toast.makeText(getActivity(), "Не удалось разместить запись на стене", Toast.LENGTH_LONG).show();
                                                VKSdk.login(getActivity(), scope);
                                            }
                                        });
                                        builder.show(getFragmentManager(), "VK_SHARE_DIALOG");
                                        dialogForVK.cancel();
                                    }
                                });
                            }


                        }
                    });
                    builderSingle.show();

                } else {
                    Toast.makeText(getActivity(), "no id " + itemID, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if (insertData) {
            Toast.makeText(getActivity(), "Сылка добавлена, вы можете воспользоваться ей кликнув на нее", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Не получилось добавить сылку", Toast.LENGTH_LONG).show();
        }
    }

    public void cameraBtn() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getActivity(), CameraForQR.class);
                        startActivityForResult(intent1, 1);

                    }
                });
            }
        });
    }

    public void cleanBtn() {
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabaseHelper.deleteAllFromQR();
                        addToListView();

                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Toast.makeText(getActivity(),"Успешно авторизовано", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onError(VKError error) {
                VKSdk.login(getActivity(), scope);
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }


        final String QR = data.getStringExtra("editQR");

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dialog_choise);
        arrayAdapter.add(QR);
        arrayAdapter.add("Открыть сылку");
        arrayAdapter.add("Сохранить");
        arrayAdapter.add("Открыть и сохранить");


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse(QR));
                    startActivity(browserIntent);
                }

                if (which == 1) {
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse(QR));
                    startActivity(browserIntent);
                }

                if (which == 2) {
                    if (QR.length() != 0) {
                        AddData(QR);
                        addToListView();
                    }
                }

                if (which == 3) {
                    if (QR.length() != 0) {
                        AddData(QR);
                        addToListView();
                    }
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse(QR));
                    startActivity(browserIntent);
                }
            }
        });

        builderSingle.show();
    }


}
