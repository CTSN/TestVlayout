package com.roy.testvlayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelperEx;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by roy on 2017/5/5.
 */

public class TestActivity extends Activity {

    private RecyclerView mRecylerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecylerView = (RecyclerView) findViewById(R.id.rv_show);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        mRecylerView.setLayoutManager(virtualLayoutManager);

        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecylerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);
        mRecylerView.setAdapter(delegateAdapter);

        //创建DelegateAdapter.Adapter 集合 存放DelegateAdapter.Adapter
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

//        adapters.add(new MyAdapter(this, new LinearLayoutHelper(), 10));

        adapters.add(new MyAdapter(this,new GridLayoutHelper(3),10));

        VirtualLayoutManager.LayoutParams fixLayoutParams = new VirtualLayoutManager.
                LayoutParams(200, 200);
        adapters.add(new MyAdapter(this,new FixLayoutHelper(20,20),1,fixLayoutParams));

        adapters.add(new MyAdapter(this,new FixLayoutHelper(FixLayoutHelper.TOP_RIGHT,20,20),1,
                fixLayoutParams));

        //指定item宽高
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(150, 150);
        adapters.add(new MyAdapter(this,new FloatLayoutHelper(),1,layoutParams));

        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
        columnLayoutHelper.setWeights(new float[]{40.0f,30.0f,30.0f});
        adapters.add(new MyAdapter(this,columnLayoutHelper,3));



        adapters.add(new MyAdapter(this,new SingleLayoutHelper(),1));

        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setColWeights(new float[]{10f});
        onePlusNLayoutHelper.setRowWeight(30f);
        adapters.add(new MyAdapter(this, onePlusNLayoutHelper, 5));

        adapters.add(new MyAdapter(this,new StickyLayoutHelper(true),1));
        adapters.add(new MyAdapter(this,new StickyLayoutHelper(false),1));

        adapters.add(new MyAdapter(this, new LinearLayoutHelper(), 10));


        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper(3,0);
        adapters.add(new MyAdapter(this, staggeredGridLayoutHelper, 25) {
            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                //设置item高度
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 200);
                if (position % 2 == 0) {
                    layoutParams.mAspectRatio = 1.0f;
                } else {
                    layoutParams.height = 340 + position % 7 * 20;
                }
                holder.itemView.setLayoutParams(layoutParams);
            }
        });

        //添加DelegateAdapter.Adapter集合
        delegateAdapter.addAdapters(adapters);
    }




    class MyAdapter extends DelegateAdapter.Adapter<MyHolder> {

        private Context context;
        private LayoutHelper mHelper;
        private int count = 0;
        private VirtualLayoutManager.LayoutParams params;


        public MyAdapter(Context context, LayoutHelper helper, int count) {
            this(context, helper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, 300));
        }

        public MyAdapter(Context context, LayoutHelper helper, int count, VirtualLayoutManager.LayoutParams
                params) {
            this.context = context;
            this.mHelper = helper;
            this.count = count;
            this.params = params;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mHelper;
        }

        @Override
        protected void onBindViewHolderWithOffset(MyHolder holder, final int position, final int offsetTotal) {
            ((TextView) holder.itemView.findViewById(R.id.tv)).setText(Integer.toString(offsetTotal));
        }


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(params));
        }



        @Override
        public int getItemCount() {
            return count;
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public MyHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;

        }
    }
}
