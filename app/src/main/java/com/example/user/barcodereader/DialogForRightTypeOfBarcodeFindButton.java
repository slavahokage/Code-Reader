package com.example.user.barcodereader;

import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by USER on 26.04.2018.
 */

public class DialogForRightTypeOfBarcodeFindButton extends AppCompatDialogFragment {
    Button cancel;
    TextView textView;
    String barcode;


    public DialogForRightTypeOfBarcodeFindButton() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_for_right_answer_find_button,null);
        builder.setView(view);
        cancel = (Button) view.findViewById(R.id.close);
        textView = (TextView) view.findViewById(R.id.textViewForRightButtonFind);

        textView.setText("Контрольный разряд = "+(Calculating.getLast = Calculating.isGetLast(Calculating.barcode)));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        return builder.create();
    }

}
