package com.roy.testvlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by roy on 2017/5/5.
 */

public class TestActivity extends Activity {
    private RecyclerView mRecylerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecylerView = (RecyclerView)findViewById(R.id.rv_show);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter testAdapter = new TestAdapter(this);
        mRecylerView.setAdapter(testAdapter);
    }

}
