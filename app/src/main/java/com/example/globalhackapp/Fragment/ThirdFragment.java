package com.example.globalhackapp.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.globalhackapp.Dialog.MyDialog;
import com.example.globalhackapp.R;

public class ThirdFragment extends Fragment {

    private View v;
    private LinearLayout layout_tom, layout_jerry;
    private ImageView selected_tom, selected_jerry;
    private MyDialog myDialog;
    private int count = 0;

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_third,container,false);

        BindUI();
        return v;

    }


    private void BindUI(){
        layout_tom = v.findViewById(R.id.layout_tom);
        layout_jerry = v.findViewById(R.id.layout_jerry);
        selected_jerry = v.findViewById(R.id.selected_jerry);
        selected_tom = v.findViewById(R.id.selected_tom);

        myDialog = new MyDialog(getContext(),getActivity(),0);

        layout_tom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setText("You want to Vote for","TOM");
                myDialog.show();


            }
        });

        layout_jerry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setText("You want to Vote for","Jerry");
                myDialog.show();
            }
        });

        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (count==0){
                    myDialog.setText("Thank you for your vote","Your vote can change the world!");
                    myDialog.show();
                }
                count++;

            }
        });



    }





}
