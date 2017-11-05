package com.example.user.lab03;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
/**
 * Created by user on 2017/10/25.
 */

class myViewHolder extends RecyclerView.ViewHolder{
    TextView captial, name;
    View mconvertview;
    SparseArray<View>mView;
    myViewHolder(Context context, View view, ViewGroup parent) {
        super(view);
        mconvertview=view;
        mView = new SparseArray<View>();
        captial = (TextView) view.findViewById(R.id.capital);
        name = (TextView) view.findViewById(R.id.goods_name);
    }

    public static myViewHolder get(Context context,ViewGroup parent ,int layoutid){
        View itemView = LayoutInflater.from(context).inflate(layoutid,parent,false);
        myViewHolder holder = new myViewHolder(context,itemView,parent);
        return holder;
    }

    public <T extends View> T getView(int viewId){
        View view = mView.get(viewId);
        if (view == null){
            //save in Array
            view = mconvertview.findViewById(viewId);
            mView.put(viewId,view);
        }
        return (T) view;
    }
}
