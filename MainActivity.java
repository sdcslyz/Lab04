package com.example.user.lab03;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadFactory;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static com.example.user.lab03.R.id.main_list;
import static com.example.user.lab03.R.id.view;

public class MainActivity extends AppCompatActivity {
    List<goods> goods_list;
    List<goods> shop_trolley;
    ListView trolley_listview;
    FloatingActionButton trolley_button;
    RecyclerView  shop_recyclerview;
    goods goods_watching;
    main_list_adapter main_recycle_adapter;
    trolley_list_adapter list_adapter;
    int addTime;

    int position_temp,position_trolley;
    boolean list_or_goods;
    Intent mintent;
    private void inntialize(){
        goods_list = new ArrayList<>();
        shop_trolley = new ArrayList<>();
        trolley_button = (FloatingActionButton)findViewById(R.id.shopping_trolley_button);
        shop_recyclerview= (RecyclerView)findViewById(R.id.main_list);
        trolley_listview = (ListView)findViewById(R.id.list_shopping_trolley);
        main_recycle_adapter = new main_list_adapter(MainActivity.this,R.layout.trolley_items,goods_list);
        list_adapter = new trolley_list_adapter(MainActivity.this,shop_trolley);


        shop_trolley.add(new goods("购物车",            "价格   ",   "类型",   "产品信息",R.mipmap.shoplist));
        goods_list.add(new goods("Enchated Forest",   "¥ 5.00",    "作者",   "Johanna Basford",R.mipmap.enchatedforest));
        goods_list.add(new goods("Arla Milk",          "¥ 59.00",   "产地",   "德国",R.mipmap.arla));
        goods_list.add(new goods("Devondale Milk",    "¥ 79.00",   "产地",   "澳大利亚",R.mipmap.devondale));
        goods_list.add(new goods("Kindle Oasis",      "¥ 2399.00", "版本",   "8GB",R.mipmap.kindle));
        goods_list.add(new goods("waitrose 早餐麦片",  "¥ 179.00",  "重量",   "2kg",R.mipmap.waitrose));
        goods_list.add(new goods("Mcvitie's 饼干",    "¥ 14.90",   "产地",   "英国",R.mipmap.mcvitie));
        goods_list.add(new goods("Ferrero Rocher",    "¥ 132.59",  "重量",  "300g",R.mipmap.ferrero));
        goods_list.add(new goods("Maltesers",          "¥ 141.43",  "重量",  "118g",R.mipmap.maltesers));
        goods_list.add(new goods("Lindt",               "¥ 5.00",    "重量",  "249g",R.mipmap.lindt));
        goods_list.add(new goods("Borggreve",          "¥ 28.90",   "重量",  "640g",R.mipmap.borggreve));
        list_adapter.notifyDataSetChanged();
    }

   //final goods good_send =(goods)getIntent().getSerializableExtra("goods");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inntialize();
        shop_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        shop_recyclerview.setAdapter(main_recycle_adapter);
        shop_recyclerview.setItemAnimator(new DefaultItemAnimator());
//        ScaleInAnimationAdapter animationadapter = new ScaleInAnimationAdapter(main_recycle_adapter);
//        animationadapter.setDuration(1000);
//        shop_recyclerview.setAdapter(animationadapter);
//        shop_recyclerview.setItemAnimator(new OvershootInLeftAnimator());

        trolley_listview.setAdapter(list_adapter);
        trolley_listview.setVisibility(View.GONE);


        //static broadcast
        Intent intentBroadcast = new Intent("com.example.user.lab03.MySticFilter");
        Random random = new Random();
        position_temp=random.nextInt(goods_list.size());
        intentBroadcast.putExtra("rgoods",goods_list.get(position_temp));
        sendBroadcast(intentBroadcast);

        Intent static_widget = new Intent("android.appwidget.action.static_widget");
        static_widget.putExtra("wgoods",goods_list.get(position_temp));
        sendBroadcast(static_widget);


        main_recycle_adapter.setOnItemClickListener(new  main_list_adapter.OnItemClickListener(){
            @Override
            public void onClick(int position){
                Intent intent=new Intent(MainActivity.this,Interface_Goods.class);
                goods_watching=goods_list.get(position);
                position_temp=position;
                intent.putExtra("goods",goods_watching);
                list_or_goods=false;
                startActivityForResult(intent,2);
            }

            @Override
            public boolean onLongClick(int position){
                goods_list.remove(position);
                main_recycle_adapter.notifyItemRemoved(position);
                main_recycle_adapter.notifyItemChanged(position);
                Toast.makeText(MainActivity.this,"移除第"+String.valueOf(position+1)+"件商品",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
//        shop_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {//判断滑动方向
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    isScrollToTop = false;
//                } else {
//                    isScrollToTop = true;
//                }
//            }
//
//        });

        trolley_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (shop_recyclerview.getVisibility() == view.GONE) {
                    shop_recyclerview.setVisibility(view.VISIBLE);
                    trolley_listview.setVisibility(view.GONE);
                    trolley_button.setImageResource(R.mipmap.shoplist);
                }
                else {
                    shop_recyclerview.setVisibility(view.GONE);
                    trolley_listview.setVisibility(view.VISIBLE);
                    trolley_button.setImageResource(R.mipmap.mainpage);
                }
            }
        });

        trolley_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Intent intent =new Intent(MainActivity.this,Interface_Goods.class);
                if (position!=0){
                    goods_watching=shop_trolley.get(position);
                    intent.putExtra("goods",goods_watching);
                    list_or_goods=true;
                    position_trolley=position;
                    startActivityForResult(intent,2);
                }
    }
});

        trolley_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view,final int position,long id){
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
                mbuilder.setTitle("移除商品");
                mbuilder.setMessage("从购物车移除"+shop_trolley.get(position).name_goods);
                mbuilder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int i){
                       if (position!=0){

                           Toast.makeText(MainActivity.this,shop_trolley.get(position).name_goods+"已被你残忍移除",Toast.LENGTH_SHORT).show();
                           shop_trolley.remove(position);
                           list_adapter.notifyDataSetChanged();
                       }
                       else {
                           Toast.makeText(MainActivity.this,"Are you kidding me ?!",Toast.LENGTH_LONG).show();
                       }
                    }
                });
                mbuilder.setNegativeButton("取消",new  DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int i){
                    }
                });
                mbuilder.create();
                mbuilder.show();
                return true;


            }
        });
        EventBus.getDefault().register(this);
//Debug
//        list_adapter.setOnItemClickListener(new trolley_list_adapter.OnItemClickListener(){
//
//            @Override
//            public void onClick(int position){
//                Intent intent = new Intent(MainActivity.this,Interface_Goods.class);
//                goods_watching=shop_trolley.get(position);
//                intent.putExtra("goods",goods_watching);
//                startActivityForResult(intent,2);
//        }
//
//            @Override
//            public boolean onLongClick(int position){
//                shop_trolley.remove(position);
//                list_adapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity.this,"移除第"+String.valueOf(position+1)+"件商品",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode ==1){
            Bundle extras = intent.getExtras();
            if (extras!=null){
                addTime = intent.getExtras().getInt("times");
                //decide which view should be changed
                String string_t;
                boolean tag_t=intent.getExtras().getBoolean("TAG");
                if (list_or_goods == false){
                    string_t=goods_list.get(position_temp).name_goods;
                }
                else {
                    string_t=shop_trolley.get(position_trolley).name_goods;
                }
                for (int i=0; i < shop_trolley.size();i++){
                    if (shop_trolley.get(i).name_goods.equals(string_t)){
                        shop_trolley.get(i).tag=tag_t;
                    }
                }
                for (int i=0; i<goods_list.size();i++){
                    if (goods_list.get(i).name_goods.equals(string_t)){
                        goods_list.get(i).tag=tag_t;
                    }
                }
                for(int i = 0; i < addTime; i++)
                    shop_trolley.add(goods_list.get(position_temp));
                list_adapter.notifyDataSetChanged();
            }
        }
    }

    public static class MessageEvent {
        private int cnt;
        public MessageEvent(int a){
            cnt = a;
        }
        public int getCnt(){
            return cnt;
        }
    }
@Subscribe(threadMode = ThreadMode.MAIN)
    public  void onMessageEvent(MessageEvent event) {
      shop_trolley.add(goods_list.get(position_temp));
      list_adapter.notifyDataSetChanged();
}

//used by dynamic broadcast to return to shopping trolley
    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        EventBus.getDefault().post(new MainActivity.MessageEvent(1));
        trolley_listview.setVisibility(View.VISIBLE);
        shop_recyclerview.setVisibility(View.GONE);
        trolley_button.setImageResource(R.mipmap.mainpage);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
