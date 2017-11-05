package com.example.user.lab03;

import java.io.Serializable;

/**
 * Created by user on 2017/10/25.
 */

public class goods implements Serializable {

    String name_goods,price_goods,type_goods,infor_goods;
    int imageid;
    boolean tag;
    goods(String name_goods,String price_goods,String type_goods,String infor_goods,int imageid)
    {
        this.imageid=imageid;
        this.infor_goods=infor_goods;
        this.name_goods=name_goods;
        this.price_goods=price_goods;
        this.type_goods=type_goods;
        tag=false;
    }

}