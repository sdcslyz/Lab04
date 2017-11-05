package com.example.user.lab03;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2017/10/25.
 */

public class trolley_list_adapter extends BaseAdapter{
    private Context context;
    private List<goods> list;
    public trolley_list_adapter(Context context,List<goods> list){
        super();
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount(){
        if (list == null){
            return 0;
        }
        else return list.size();
    }

    @Override
    public Object getItem(int i){
        if (list.size()<=i){
            return 0;
        }
        else return list.get(i);
    }

    @Override
    public long getItemId(int i){
        return (long)i;
    }
    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view;
        trolley_list_viewholder viewholder;
        if (convertview == null){
            view = LayoutInflater.from(context).inflate(R.layout.trolley_items,null);
            viewholder = new trolley_list_viewholder(view);
            view.setTag(viewholder);
        }
        else{
            view = convertview;
            viewholder=(trolley_list_viewholder) view.getTag();
        }
        if (list.get(i).name_goods.equals("购物车") ){
            viewholder.captial.setText("*");
        }
       else {
            viewholder.captial.setText(list.get(i).name_goods.substring(0,1));
       }
        viewholder.name.setText(list.get(i).name_goods);
        viewholder.price.setText(list.get(i).price_goods);

        return view;
    }
//Debug
//    public  interface OnItemClickListener{
//        void onClick(int position);
//        boolean onLongClick(int position);
//    }
//    void setOnItemClickListener(trolley_list_adapter.OnItemClickListener onItemClickListener){
//        this.mOnItemClickListener=onItemClickListener;
//    }
    //list viewholder
    private class trolley_list_viewholder{
        TextView captial, name, price;
        trolley_list_viewholder(View view) {
            captial = (TextView) view.findViewById(R.id.capital);
            name = (TextView) view.findViewById(R.id.goods_name);
            price = (TextView) view.findViewById(R.id.price);
        }
    }
}
