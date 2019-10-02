package com.example.globalhackapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.globalhackapp.Fragment.FirstFragment;
import com.example.globalhackapp.Fragment.SecondFragment;
import com.example.globalhackapp.Fragment.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FirstFragment firstFragment = new FirstFragment();
    private SecondFragment secondFragment = new SecondFragment();
    private ThirdFragment thirdFragment = new ThirdFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 누른 결과를 보여주기 위해 TextView를 사용합니다.


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, firstFragment).commitAllowingStateLoss();

        // 버튼 클릭시 사용되는 리스너를 구현합니다.
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();


                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {

                            case R.id.menuitem_bottombar_up:
                                transaction.replace(R.id.frameLayout, firstFragment).commitAllowingStateLoss();

                                return true;

                            case R.id.menuitem_bottombar_down:
                                transaction.replace(R.id.frameLayout, secondFragment).commitAllowingStateLoss();

                                return true;

                            case R.id.menuitem_bottombar_search:
                                transaction.replace(R.id.frameLayout, thirdFragment).commitAllowingStateLoss();

                                return true;
                        }
                        return false;
                    }
                });
    }
}
