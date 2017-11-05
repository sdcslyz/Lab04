package com.example.user.lab03;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/10/25.
 */




public class main_list_adapter extends RecyclerView.Adapter<myViewHolder>{

    private Context mcontext;
    private  List <goods> mDatas;
    private int mlayoutid;
    private OnItemClickListener mOnItemClickListener;
//    boolean isScrollToTop;
    public main_list_adapter(Context context, int layoutid, List <goods> datas){
        mcontext=context;
        mlayoutid=layoutid;
        mDatas=datas;
    }

    @Override
    public myViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        myViewHolder viewholder = myViewHolder.get(mcontext,parent,mlayoutid);
        return viewholder;
    }


    public void  convert(myViewHolder holder,goods s){
         holder.name.setText(s.name_goods);
         holder.captial.setText(s.name_goods.substring(0,1));
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder,int position){
        convert(holder,mDatas.get(position));
//        if (isScrollToTop) {//根据滑动方向设置动画
//            holder.itemView.setAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.anim_rotate_in_top));
//        } else {
//            holder.itemView.setAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.anim_rotate_in_bottom));
//        }

        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    mOnItemClickListener.onClick((holder.getAdapterPosition()));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }
//    @Override   //视图不可见时回收动画
//    public void onViewDetachedFromWindow(myViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        holder.itemView.clearAnimation();
//    }
    @Override
    public  int getItemCount(){
        return mDatas.size();
    }

    public Object getItem(int i){
        if (mDatas.size()<i){
            return 0;
        }
        return mDatas.get(i);
    }

    public long getItemId(int i){
        return i;
    }

    public  interface OnItemClickListener{
        void onClick(int position);
        boolean onLongClick(int position);
    }
     void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

  //  main_list_adapter=new main_list_adapter<Map<String,Object>>(this,R.layout.)
//    @Override
//    public myViewHolder onBindViewHolder(final myViewHolder holder,int position){
//        convert(holder,)
//    }


}
