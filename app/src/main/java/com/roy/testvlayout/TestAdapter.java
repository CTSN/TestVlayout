package com.roy.testvlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by roy on 2017/5/5.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {


    private OnItemClickListerner onItemClickListerner;
    private Context context;

    public TestAdapter(Context context){
        this.context = context;
    }

    public void setOnItemClickListerner(OnItemClickListerner onItemClickListerner) {
        this.onItemClickListerner = onItemClickListerner;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(TestHolder holder, final int position) {
        ((TextView)holder.itemView.findViewById(R.id.tv)).setText(Integer.toString(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListerner != null)
                    onItemClickListerner.itemClick(position,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class TestHolder extends RecyclerView.ViewHolder{

        public TestHolder(View itemView) {
            super(itemView);
        }
    }

    interface OnItemClickListerner{
        void itemClick(int position,View v);
    }
}
