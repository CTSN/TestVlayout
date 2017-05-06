package com.roy.testvlayout;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecylerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

//-----------------------GridLayoutHelper------------------

        GridLayoutHelper helper = new GridLayoutHelper(3);
        helper.setMargin(7, 7, 7, 7);
        helper.setPadding(10, 10, 10, 10);
        helper.setBgColor(0xff87e543);
        helper.setGap(3);
        helper.setAutoExpand(true);
        helper.setHGap(3);
        MyAdapter myAdapter = new MyAdapter(this, helper, 10);
        adapters.add(myAdapter);



//---------------------FloatHelper 浮动布局--------------------------

        FloatLayoutHelper layoutHelper = new FloatLayoutHelper();
        layoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);
        layoutHelper.setDefaultLocation(100, 400);
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(150, 150);
        adapters.add(new MyAdapter(this, layoutHelper, 1, layoutParams));


//----------------------LinearLayoutHelper 线性布局------------------
        LinearLayoutHelper layoutHelper1 = new LinearLayoutHelper();
        layoutHelper1.setAspectRatio(2.0f);
        LinearLayoutHelper layoutHelper2 = new LinearLayoutHelper();
        layoutHelper2.setAspectRatio(4.0f);
        layoutHelper2.setDividerHeight(10);
        layoutHelper2.setMargin(10, 30, 10, 10);
        layoutHelper2.setPadding(10, 30, 10, 10);
        layoutHelper2.setBgColor(0xFFF5A623);
        adapters.add(new MyAdapter(this, layoutHelper1, 1));
        adapters.add(new MyAdapter(this, layoutHelper2, 6) {

            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                if (position % 2 == 0) {
                    VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                    layoutParams.mAspectRatio = 5;
                    holder.itemView.setLayoutParams(layoutParams);
                }
            }
        });



//--------------------StickyLayoutHelper 吸附布局 ----------------------
        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper(true);  //
        stickyLayoutHelper.setOffset(10);
        stickyLayoutHelper.setBgColor(0xff00ff);
        stickyLayoutHelper.setPadding(10,10,10,10);
        adapters.add(new MyAdapter(this, stickyLayoutHelper, 1, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)));

//--------------------ColumnLayoutHelper  列表布局----------------------
        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
        columnLayoutHelper.setBgColor(0xff00f0f0);
        columnLayoutHelper.setWeights(new float[]{40.0f, Float.NaN, 40});
        adapters.add(new MyAdapter(this, columnLayoutHelper, 5) {

            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                if (position == 0) {
                    VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                    layoutParams.mAspectRatio = 4;
                    holder.itemView.setLayoutParams(layoutParams);
                } else {
                    VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                    layoutParams.mAspectRatio = Float.NaN;
                    holder.itemView.setLayoutParams(layoutParams);
                }
            }

        });

//--------------------OnePlusNLayoutHelper 栅格布局----------------------
        OnePlusNLayoutHelper onePlusNLayoutHelper1 = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper1.setBgColor(0xff876384);
        onePlusNLayoutHelper1.setAspectRatio(4.0f);
        onePlusNLayoutHelper1.setColWeights(new float[]{40f, 60f});
        onePlusNLayoutHelper1.setMargin(10, 20, 10, 20);
        onePlusNLayoutHelper1.setPadding(10, 10, 10, 10);
        adapters.add(new MyAdapter(this, onePlusNLayoutHelper1, 2));


        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setBgColor(0xffef8ba3);
        onePlusNLayoutHelper.setAspectRatio(2.0f);
        onePlusNLayoutHelper.setColWeights(new float[]{40f});
        onePlusNLayoutHelper.setRowWeight(30f);
        onePlusNLayoutHelper.setMargin(10, 20, 10, 20);
        onePlusNLayoutHelper.setPadding(10, 10, 10, 10);
        adapters.add(new MyAdapter(this, onePlusNLayoutHelper, 4) {
            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams lp = (VirtualLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                if (position == 0) {
                    lp.rightMargin = 1;
                } else if (position == 1) {

                } else if (position == 2) {
                    lp.topMargin = 1;
                    lp.rightMargin = 1;
                }
            }
        });


//        adapters.add(new SubAdapter(this, new OnePlusNLayoutHelper(), 0));
        OnePlusNLayoutHelper onePlusNLayoutHelper2 = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper2.setBgColor(0xff87e543);
        onePlusNLayoutHelper2.setAspectRatio(1.8f);
        onePlusNLayoutHelper2.setColWeights(new float[]{33.33f, 50f, 40f});
        onePlusNLayoutHelper2.setMargin(10, 20, 10, 20);
        onePlusNLayoutHelper2.setPadding(10, 10, 10, 10);
        VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        adapters.add(new MyAdapter(this, onePlusNLayoutHelper2, 3, lp) {
            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams lp = (VirtualLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                if (position == 0) {
                    lp.rightMargin = 1;
                }
            }
        });
//--------------------GridLayoutHelper 表格----------------------

        adapters.add(new MyAdapter(this, new LinearLayoutHelper(), 10));

        StickyLayoutHelper stickyLayoutHelper2 = new StickyLayoutHelper(false);
        stickyLayoutHelper2.setOffset(10);
        stickyLayoutHelper2.setBgColor(0xff00ff);
        stickyLayoutHelper2.setPadding(10,10,10,10);
        adapters.add(new MyAdapter(this, stickyLayoutHelper2, 1, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)));

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setMargin(0, 10, 0, 10);
        adapters.add(new MyAdapter(this, gridLayoutHelper, 3));

//----------------StaggeredGridLayoutHelper 瀑布流-----------------

        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper(3, 10);
        staggeredGridLayoutHelper.setMargin(20, 10, 10, 10);
        staggeredGridLayoutHelper.setPadding(10, 10, 20, 10);
        staggeredGridLayoutHelper.setBgColor(0xFF86345A);
        adapters.add(new MyAdapter(this, staggeredGridLayoutHelper, 25) {
            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                if (position % 2 == 0) {
                    layoutParams.mAspectRatio = 1.0f;
                } else {
                    layoutParams.height = 340 + position % 7 * 20;
                }
                holder.itemView.setLayoutParams(layoutParams);
            }
        });
//--------------------ScrollFixLayoutHelper 固定位置----------------------
        adapters.add(new MyAdapter(this,new FixLayoutHelper(FixLayoutHelper.TOP_RIGHT,20,20),1){
            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(200, 200);
                holder.itemView.setLayoutParams(layoutParams);
            }
        });

        adapters.add(new MyAdapter(this, new ScrollFixLayoutHelper(FixLayoutHelper.TOP_LEFT,20, 20), 1) {
            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(200, 200);
                holder.itemView.setLayoutParams(layoutParams);
            }
        });


        delegateAdapter.addAdapters(adapters);



        final Handler mainHandler = new Handler(Looper.getMainLooper());

        Runnable trigger = new Runnable() {
            @Override
            public void run() {
                // recyclerView.scrollToPosition(22);
                // recyclerView.getAdapter().notifyDataSetChanged();
                // mainHandler.postDelayed(trigger, 1000);
                //List<DelegateAdapter.Adapter> newAdapters = new ArrayList<>();
                //newAdapters.add((new SubAdapter(VLayoutActivity.this, new ColumnLayoutHelper(), 3)));
                //newAdapters.add((new SubAdapter(VLayoutActivity.this, new GridLayoutHelper(4), 24)));
                //delegateAdapter.addAdapters(newAdapters);
                mRecylerView.requestLayout();
            }
        };


        mainHandler.postDelayed(trigger, 1000);


    }


    class MyAdapter extends DelegateAdapter.Adapter<MyHolder> {

        private Context context;
        private LayoutHelper mHelper;
        private int count = 0;
        private VirtualLayoutManager.LayoutParams params;
        private OnItemClickListerner itemClickListerner;

        public void setItemClickListerner(OnItemClickListerner itemClickListerner) {
            this.itemClickListerner = itemClickListerner;
        }

        public MyAdapter(Context context, LayoutHelper helper, int count) {
            this(context, helper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public MyAdapter(Context context, LayoutHelper helper, int count, VirtualLayoutManager.LayoutParams params) {
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
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(params));
        }

        @Override
        protected void onBindViewHolderWithOffset(MyHolder holder, final int position, final int offsetTotal) {
//            holder.itemView.setBackgroundResource(R.drawable.border_bg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.i("TAG","---->" + offsetTotal);
                    if (itemClickListerner != null)
                        itemClickListerner.itemClick(v, offsetTotal);
                }
            });
            ((TextView) holder.itemView.findViewById(R.id.tv)).setText(Integer.toString(offsetTotal));
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

    interface OnItemClickListerner {
        void itemClick(View v, int position);
    }
}
