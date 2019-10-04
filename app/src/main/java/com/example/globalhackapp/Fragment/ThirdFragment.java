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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {

    private View v;
    private LinearLayout layout_tom, layout_jerry;
    private ImageView selected_tom, selected_jerry;
    private MyDialog myDialog;
    private int count = 0;

    private BarChart bar_chart;
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
        bar_chart = v.findViewById(R.id.bar_chart);

        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("Busan");
        arrayList.add("Seoul");
        arrayList.add("Daejeon");


        ArrayList<Integer> arrayList1 = new ArrayList<>();
        arrayList1.add(23);
        arrayList1.add(43);
        arrayList1.add(10);


        BarChartGraph(arrayList,arrayList1);


    }
    private void BarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {
        // BarChart 메소드

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i < valList.size();i++){
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet (entries, "Voting rate"); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);


        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i < labelList.size(); i++){
            labels.add((String) labelList.get(i));
        }

        BarData data = new BarData(labels,depenses); // 라이브러리 v3.x 사용하면 에러 발생함

        depenses.setColors(ColorTemplate.COLORFUL_COLORS);
        bar_chart.getAxisLeft().setDrawAxisLine(false);
        bar_chart.getXAxis().setDrawGridLines(false);
        bar_chart.setDescription("");
        bar_chart.setTouchEnabled(false);
        bar_chart.setData(data);
        bar_chart.animateXY(1000,1000);
        bar_chart.invalidate();
    }








}
