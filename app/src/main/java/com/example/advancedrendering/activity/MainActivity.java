package com.example.advancedrendering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.example.advancedrendering.R;
import com.example.advancedrendering.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
作业：
 水波纹效果

 倒影图片

 雷达效果

 * 参考文章：
http://blog.csdn.net/t12x3456/article/details/10711779

 http://blog.csdn.net/nmsoftklb/article/details/24654879
 http://blog.csdn.net/sjf0115/article/details/7357962
 http://www.cnblogs.com/_ymw/p/3905829.html

 http://blog.csdn.net/u010402982/article/details/52538153
 http://blog.csdn.net/toyota11/article/details/50975797
 http://www.jb51.net/article/90041.htm
 http://www.jianshu.com/p/4918034e3f0e
 http://www.jianshu.com/p/ef560fe3eea8
 */
public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.CallBack {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initData();

        initRV();

        refreshRV();


    }

    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private List<String> data;

    private void initData() {
        data = new ArrayList<String>();
        data.add(getString(R.string.test1));
        data.add(getString(R.string.test2));
        data.add(getString(R.string.test3));
    }

    private void initRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);
        rv.setAdapter(myRecyclerViewAdapter);
    }

    private void refreshRV() {
        if (null != myRecyclerViewAdapter) {
            myRecyclerViewAdapter.setData(data);
            myRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void rVOnItemClick(int position) {
        if (null != myRecyclerViewAdapter) {
            String str = myRecyclerViewAdapter.getItem(position);
            if (!TextUtils.isEmpty(str)) {
                if (str.equals(getString(R.string.test1))) {
                    startActivity(new Intent(MainActivity.this,RadialGradientViewActivity.class));
                } else if (str.equals(getString(R.string.test2))) {
                    startActivity(new Intent(MainActivity.this,LinearGradientViewActivity.class));
                } else if (str.equals(getString(R.string.test3))) {
                    startActivity(new Intent(MainActivity.this,SweepGradientViewActivity.class));
                }
            }
        }
    }
}
