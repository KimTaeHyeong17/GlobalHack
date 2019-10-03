package com.example.globalhackapp.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globalhackapp.Dialog.MyDialog;
import com.example.globalhackapp.Network.Network;
import com.example.globalhackapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import cz.msebera.android.httpclient.Header;


public class FirstFragment extends Fragment {

    private View v;

    private SurfaceView cameraView;
    private TextView textView;
    private CameraSource cameraSource;
    private View layout_finger, layout_card;
    final int RequestCamearPermissionID = 1001;

    private String idCardStringData;

    private BluetoothSPP bt;
    private Button btn_go_finger, btn_back_to_card;

    private String fingerData;
    private String nameString;
    private TextView tv_validated;


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCamearPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_first, container, false);

        BindUI();
        SetTextRecognizer();
        SetBlueTooth();

        return v;

    }

    private void BindUI() {
        cameraView = v.findViewById(R.id.surface_view);
        textView = v.findViewById(R.id.tv);

        layout_finger = v.findViewById(R.id.layout_finger);
        layout_card = v.findViewById(R.id.layout_card);

        layout_finger.setVisibility(View.GONE);

        btn_go_finger = v.findViewById(R.id.btn_go_finger);
        btn_back_to_card = v.findViewById(R.id.btn_back_to_card);
        tv_validated = v.findViewById(R.id.tv_validated);

        btn_go_finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_card.setVisibility(View.GONE);
                layout_finger.setVisibility(View.VISIBLE);
            }
        });

        btn_back_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_card.setVisibility(View.VISIBLE);
                layout_finger.setVisibility(View.GONE);
            }
        });


    }

    //bluetooth
    private void SetBlueTooth() {
        bt = new BluetoothSPP(getContext()); //Initializing
        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
//            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {

//                final MyDialog myDialog1 = new MyDialog(getContext(), getActivity(), 0);
//                final MyDialog myDialog2 = new MyDialog(getContext(), getActivity(), 1);
//                final MyDialog myDialog3 = new MyDialog(getContext(), getActivity(), 2);
//                MyDialog myDialog4 = new MyDialog(getContext(), getActivity(), 2);
                MyDialog myDialog = new MyDialog(getContext(), getActivity(), 0);

                Log.e("message from arduino", message);

                //data receive
                if (message.equals("Sensor Ready!")) {
//                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                    myDialog.setText("Put your Finger on senser", "Please wait for 2 seconds");
                    myDialog.show();


                } else if (message.equals("1st attempt")) {
                    Log.e("1st attempt", "first attemt");
                    myDialog.dismiss();
                    myDialog.setText("Put your Finger on senser", "Please wait for 2 seconds");
                    myDialog.show();


                } else if (message.equals("Remove finger")) {
                    Log.e("remove finger", "");
                    myDialog.dismiss();
                    myDialog.setText("Remove finger", "Wait for the further instruction");
                    myDialog.show();


                } else if (message.equals("2nd attempt")) {
                    myDialog.dismiss();
                    myDialog.setText("Put your Finger on senser", "Please wait for 2 seconds");
                    myDialog.show();

                    Log.e("2nd attempt", "");


                } else if (message.equals("Prints matched!")) {
                    myDialog.dismiss();
                    myDialog.setText("Succes", "You are now validated");
                    myDialog.show();

                    Log.e("print matched", "");

                } else if (message.equals("Error")) {
                    Toast.makeText(getContext(), "EROOR", Toast.LENGTH_SHORT).show();

                } else if (message.equals("Did not match")) {
                    myDialog.setText("FAIL", "Your finger didn't matched");


                } else {
                    //데이터 전송
                    fingerData = message;
                    Log.e("message data", fingerData);
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    sendDataToServer(fingerData);
                }
//                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제

            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnConnect = v.findViewById(R.id.btnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    //api
    private void sendDataToServer(String data) {
        RequestParams params = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "KIM TAEHYEONG");
            jsonObject.put("id", "123");
            jsonObject.put("fingerPrint", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String jsonStr = "{\n" +
//                "  \"name\": \"KIM TAEHYEONG\",\n" +
//                "  \"id\": \"123\",\n" +
//                "  \"fingerPrint\": \"2391255255255255201303\"\n" +
//                "}";
        Log.e("jsonObject", jsonObject.toString());

        params.put("postData", jsonObject.toString());

        Network.post(getActivity(), "/project/votingSystem.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    if (response.getString("code").equals("1")) {
                        Toast.makeText(getContext(), "sucess", Toast.LENGTH_SHORT).show();
                        Log.e("fuck this im out", response.toString());
                        layout_finger.setVisibility(View.GONE);
                        layout_card.setVisibility(View.GONE);
                        tv_validated.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "catch", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("login_error", String.valueOf(statusCode));
            }
        });
    }

    //bluetooth
    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    //bluetooth
    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    //bluetooth
    public void setup() {
        Button btnSend = v.findViewById(R.id.btnSend); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("send", "clicked");
                bt.send("1", true);
            }
        });
    }

    //bluetooth
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
//                finish();
            }
        }
    }

    private void SetTextRecognizer() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("FirstFragment", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
        }
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                                RequestCamearPermissionID);
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuilder.append("\n");
                            }
                            textView.setText(stringBuilder.toString());
                            idCardStringData = stringBuilder.toString();

                            String name = "";
                            if (idCardStringData.length() > 13) {
                                name = idCardStringData.substring(idCardStringData.length() - 14);
                            } else {
                                name = idCardStringData;
                            }

                            Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                            Log.e("name", name);

                            int intIndex = name.indexOf("KIM");

                            if (intIndex == -1) {
                                System.out.println("Hello not found");
                            } else {

                                cameraSource.stop();

                                MyDialog myDialog = new MyDialog(getContext(), getActivity(), 5);
                                myDialog.show();
                                final String finalName = name;
                                myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        nameString = finalName;
                                        layout_card.setVisibility(View.GONE);
                                        layout_finger.setVisibility(View.VISIBLE);
                                    }
                                });

                            }


                        }
                    });
                }
            }
        });
    }


}
