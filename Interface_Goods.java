package com.example.user.lab03;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by user on 2017/10/25.
 */

public class Interface_Goods extends AppCompatActivity {
    ImageButton back, favorite, shoplist;
    ImageView goods_photo;
    TextView goods_name,goods_details;
    int add_times;
    ListView infor;
    ArrayAdapter<String> adapter;
    String[] operations;
    DynamicReceiver dynm_receiver;
    void initial() {
        back = (ImageButton) findViewById(R.id.back);
        favorite = (ImageButton) findViewById(R.id.favorite);
        shoplist = (ImageButton) findViewById(R.id.shoplist);
        goods_photo = (ImageView) findViewById(R.id.photo);
        goods_name = (TextView) findViewById(R.id.name_infor);
        goods_details=(TextView) findViewById(R.id.detail);
        add_times = 0;
        operations = new String[]{"一键下单", "分享宝贝", "不感兴趣", "查看更多宝贝详情"};
        infor = (ListView) findViewById(R.id.more_infor);
        adapter = new ArrayAdapter<String>(this, R.layout.textlayout, operations);
        infor.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);
        initial();
        final goods good_send =(goods)getIntent().getSerializableExtra("goods");
        if (good_send.tag == false ){
            favorite.setImageResource(R.mipmap.empty_star);
        }
        else {
            favorite.setImageResource(R.mipmap.full_star);
        }
        goods_name.setText(good_send.name_goods);
        goods_photo.setImageResource(good_send.imageid);
        goods_details.setText(good_send.infor_goods);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Interface_Goods.this,MainActivity.class);
                intent.putExtra("times",add_times);
                intent.putExtra("TAG",good_send.tag);
                setResult(1,intent);
                finish();
            }
        });

        favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (good_send.tag==false){
                    favorite.setImageResource(R.mipmap.full_star);
                    good_send.tag=true;
                    Toast.makeText(Interface_Goods.this,"商品已加入收藏",Toast.LENGTH_SHORT).show();
                }
                else{
                    favorite.setImageResource(R.mipmap.empty_star);
                    good_send.tag=false;
                    Toast.makeText(Interface_Goods.this,"商品已取消收藏",Toast.LENGTH_SHORT).show();
                }
            }
        });

        shoplist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                add_times++;
                if (add_times==1)Toast.makeText(Interface_Goods.this,"商品已添加至购物车",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(Interface_Goods.this,"连击X"+String.valueOf(add_times)+"!!!",Toast.LENGTH_SHORT).show();
                }

                final String DYNMF="com.example.user.lab03.MyDynmFilter";
                IntentFilter dynamic_filter = new IntentFilter();
                dynamic_filter.addAction(DYNMF);
                dynm_receiver = new DynamicReceiver();
                registerReceiver(dynm_receiver,dynamic_filter);
                Intent dynm_broadcast = new Intent(DYNMF);
                dynm_broadcast.putExtra("pgoods",good_send);
                sendBroadcast(dynm_broadcast);

           //     EventBus.getDefault().post(new MainActivity.MessageEvent(1));
            }
        });

    }
//    @Override
    public void onDestroy(){
        super.onDestroy();
        if(dynm_receiver!=null)unregisterReceiver(dynm_receiver);
    }
}
