package com.example.globalhackapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Useful {


    public static void showAlertDialog(Activity activity, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork==null){
            return false;
        }else {
            return activeNetwork.isConnectedOrConnecting();
        }
    }



    public static void pricedigittextView(final TextView textView) {
        final DecimalFormat decimalFormat = new DecimalFormat("###,###");
        final String[] result = {""};
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!result[0].equals(s.toString()) && !s.toString().equals("")) {

                    result[0] = decimalFormat.format(Integer.parseInt(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    textView.setText(result[0]);

                } else if (s.equals("")) {
                    result[0] = "";
                    textView.setText(result[0]);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public static void pricedigiteditText(final EditText editText) {
        final DecimalFormat decimalFormat = new DecimalFormat("###,###");
        final String[] result = {""};
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!result[0].equals(s.toString()) && !s.toString().equals("")) {

                    result[0] = decimalFormat.format(Integer.parseInt(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    editText.setText(result[0]);

                } else if (s.equals("")) {
                    result[0] = "";
                    editText.setText(result[0]);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(editText.length());
            }
        });
    }


    public static String convertGradeFactory(String grade) {
        String str = "";
        if (grade.equals("1")) {
            str = "브론즈";
        } else if (grade.equals("2")) {
            str = "실버";
        } else if (grade.equals("3")) {
            str = "골드";
        } else if (grade.equals("4")) {
            str = "최고등급";
        }

        if (grade.equals("브론즈")) {
            str = "1";
        } else if (grade.equals("실버")) {
            str = "2";
        } else if (grade.equals("골드")) {
            str = "3";
        } else if (grade.equals("최고등급")) {
            str = "4";
        }


        return str;
    }

    public static String convertGenderFactory(String gender) {
        String str = "";
        if (gender.equals("M")) {
            str = "남";
        } else if (gender.equals("F")) {
            str = "여";
        }

        if (gender.equals("남")) {
            str = "M";
        } else if (gender.equals("여")) {
            str = "F";
        }

        return str;
    }

    public static String deleteComma(String str) {
        String tmp = str.replace(",", "");
        return tmp;
    }

    public static String deleteUnit(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String convertPossibleFactory(String str) {
        if (str.equals("가능")) {
            str = "Y";
        } else if (str.equals("불가능")) {
            str = "N";
        }

        if (str.equals("Y")) {
            str = "가능";
        } else if (str.equals("N")) {
            str = "불가능";
        }
        return str;
    }



    public static String ChangeToMoneyString(int num){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        return nf.format(num);
    }



}
