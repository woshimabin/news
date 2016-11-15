package com.itemp.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyActivity extends AppCompatActivity {

    @BindView(R.id.rg_main_rb1)
    RadioButton rgMainRb1;
    @BindView(R.id.rg_main_rb2)
    RadioButton rgMainRb2;
    @BindView(R.id.rg_main_rb3)
    RadioButton rgMainRb3;
    @BindView(R.id.rg_main_rb4)
    RadioButton rgMainRb4;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.btnyejian)
    CheckBox btnyejian;
    @BindView(R.id.vp)
    LinearLayout vp;
    @BindView(R.id.llradiogrop)
    LinearLayout llradiogrop;
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.card2)
    CardView card2;
    @BindView(R.id.card3)
    CardView card3;
    @BindView(R.id.card4)
    CardView card4;
    @BindView(R.id.card5)
    CardView card5;
    @BindView(R.id.card6)
    CardView card6;
    @BindView(R.id.card7)
    CardView card7;
    @BindView(R.id.card8)
    CardView card8;
    @BindView(R.id.card9)
    CardView card9;
    @BindView(R.id.card0)
    CardView card0;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.llguanzhu)
    LinearLayout llguanzhu;
    @BindView(R.id.llread)
    LinearLayout llread;
    @BindView(R.id.llxiaoxi)
    LinearLayout llxiaoxi;
    @BindView(R.id.lldongtai)
    LinearLayout lldongtai;
    @BindView(R.id.shangcheng)
    LinearLayout shangcheng;
    @BindView(R.id.llqianbao)
    LinearLayout llqianbao;
    @BindView(R.id.lljinbirenwu)
    LinearLayout lljinbirenwu;
    @BindView(R.id.llyejian)
    LinearLayout llyejian;
    @BindView(R.id.llhuodong)
    LinearLayout llhuodong;
    @BindView(R.id.yijian)
    LinearLayout yijian;
    private Intent intent;

    List<CardView> list=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);

        list.add(card0);
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        list.add(card5);
        list.add(card6);
        list.add(card7);
        list.add(card8);
        list.add(card9);

        btnyejian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vp.setBackgroundResource(R.color.yejian);
                    llradiogrop.setBackgroundResource(R.color.yejian);

                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCardBackgroundColor(getResources().getColor(R.color.yejian));
                    }

                } else {
                    llradiogrop.setBackgroundResource(R.color.white);


                    for (int i = 0; i < list.size(); i++) {
                        vp.setBackgroundResource(R.color.white);
                        llradiogrop.setBackgroundResource(R.color.white);
                        list.get(i).setCardBackgroundColor(getResources().getColor(R.color.white));
                    }

                }
            }
        });

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_main_rb1:
                        intent = new Intent(MyActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb2:
                        intent = new Intent(MyActivity.this, EasyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb3:
                        intent = new Intent(MyActivity.this, TopicActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, DengluActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        rgMainRb4.setChecked(true);
    }
}
