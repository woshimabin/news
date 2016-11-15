package com.itemp.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.itemp.news.Fragment.testFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tbl;
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
    @BindView(R.id.vp)
    ViewPager vp;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] titleNames;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        titleNames=getResources().getStringArray(R.array.title_name);
        initViewPager();
        tbl.setupWithViewPager(vp);


        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rg_main_rb2:
                        intent = new Intent(MainActivity.this, EasyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb3:
                         intent = new Intent(MainActivity.this, TopicActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rg_main_rb4:
                        intent = new Intent(MainActivity.this, MyActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }



    private void initViewPager() {

        testFragment fragment0 = new testFragment(titleNames[0]);
        testFragment fragment1 = new testFragment(titleNames[1]);
        testFragment fragment2 = new testFragment(titleNames[2]);
        testFragment fragment3 = new testFragment(titleNames[3]);
        testFragment fragment4 = new testFragment(titleNames[4]);
        testFragment fragment5 = new testFragment(titleNames[5]);
        testFragment fragment6 = new testFragment(titleNames[6]);
        testFragment fragment7 = new testFragment(titleNames[7]);
        testFragment fragment8 = new testFragment(titleNames[8]);
        testFragment fragment9 = new testFragment(titleNames[9]);

        fragments.add(fragment0);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        fragments.add(fragment6);
        fragments.add(fragment7);
        fragments.add(fragment8);
        fragments.add(fragment9);


        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                    String name = titleNames[position];
                    return name;

            }
        });
        vp.setOffscreenPageLimit(2);
    }
    @Override
    protected void onResume() {
        super.onResume();
        rgMainRb1.setChecked(true);
    }
}
