package com.example.globalhackapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.globalhackapp.Dialog.MyDialog;
import com.example.globalhackapp.R;


public class SecondFragment extends Fragment {

    private View v;
    private LinearLayout btn_tom, btn_jerry;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_second, container, false);

        bindUI();

        return v;
    }

    public void bindUI(){
        btn_tom = v.findViewById(R.id.btn_tom);
        btn_jerry = v.findViewById(R.id.btn_jerry);

        btn_tom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog myDialog = new MyDialog(getContext(),getActivity(),0);
                myDialog.setText("Toms Info","Rep. Jim Jordan, a ranking member of the House Oversight Committee said, “not one thing” ex-diplomat to Ukraine Kurt Volker told lawmakers this morning “aligns with the “Democratic impeachment narrative.”\n" +
                        "\n" +
                        "Volker spent the morning testifying behind closed-doors before members from multiple House committees. Jordan said House Intel Chairman Adam Schiff limited members from asking questions.\n" +
                        "\n" +
                        "“We have never seen a chairman suggest that members aren’t allowed to ask questions. So, if this is how Mr. Schiff is going to conduct these kinds of interviews ... that’s a concern as well,” Jordan said.\n" +
                        "\n" +
                        "The Ohio Republican said Volker has been “very impressive,” but would not elaborate on the specifics, just saying nothing Volker said ");
                myDialog.show();
            }
        });

        btn_jerry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog myDialog = new MyDialog(getContext(),getActivity(),0);
                myDialog.setText("Toms Info","Rep. Jim Jordan, a ranking member of the House Oversight Committee said, “not one thing” ex-diplomat to Ukraine Kurt Volker told lawmakers this morning “aligns with the “Democratic impeachment narrative.”\n" +
                        "\n" +
                        "Volker spent the morning testifying behind closed-doors before members from multiple House committees. Jordan said House Intel Chairman Adam Schiff limited members from asking questions.\n" +
                        "\n" +
                        "“We have never seen a chairman suggest that members aren’t allowed to ask questions. So, if this is how Mr. Schiff is going to conduct these kinds of interviews ... that’s a concern as well,” Jordan said.\n" +
                        "\n" +
                        "The Ohio Republican said Volker has been “very impressive,” but would not elaborate on the specifics, just saying nothing Volker said ");
                myDialog.show();

            }
        });


    }

}
