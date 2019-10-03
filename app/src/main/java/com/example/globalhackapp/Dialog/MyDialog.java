package com.example.globalhackapp.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.globalhackapp.R;


public class MyDialog extends android.app.Dialog implements View.OnClickListener {

    private Button btn_accept;
    private TextView  tv_work_detail_1, tv_work_detail_2;
    private Activity activity;
    private int mMode;

    public interface MyDialogListener {
        public void onPositiveClicked();
        public void onNegativeClicked();
    }


    private MyDialogListener dialogListener;

    public void AlertDialog(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public MyDialog(Context context, Activity mActivity, int mode ) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog);

        bindUI();
        btn_accept.setOnClickListener(this);


        activity = mActivity;
        mMode = mode;
        setupDialog();

    }


    private void bindUI() {
        btn_accept = findViewById(R.id.btn_accept);
        tv_work_detail_1 = findViewById(R.id.tv_work_detail_1);
        tv_work_detail_2 = findViewById(R.id.tv_work_detail_2);

    }

    public void setupDialog(){
        switch (mMode){
            case 0:
                tv_work_detail_1.setText("Put your Finger on sensor");
                tv_work_detail_2.setText("Please wait for 2 seconds");
                btn_accept.setText("OK");
                break;
            case 1:
                tv_work_detail_1.setText("Remove Finger");
                tv_work_detail_2.setText("Wait for the further instruction");
                btn_accept.setText("OK");
                break;
            case 2:
                tv_work_detail_1.setText("Scanning Completed");
                tv_work_detail_2.setText("You can now Vote to your candidate");
                btn_accept.setText("OK");
                break;
            case 3:
                tv_work_detail_1.setText("Succes");
                tv_work_detail_2.setText("You are now validated");
                btn_accept.setText("OK");
                break;
            case 4:
                tv_work_detail_1.setText("FAIL");
                tv_work_detail_2.setText("Your finger didn't matched");
                btn_accept.setText("OK");
                break;
            case 5:
                tv_work_detail_1.setText("Welcome KIM TAEHYEONG");
                tv_work_detail_2.setText("Please Proceed Finger print validation");
                btn_accept.setText("OK");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
//                dialogListener.onPositiveClicked();
                dismiss();

                break;

            default:
                break;

        }
    }
}
