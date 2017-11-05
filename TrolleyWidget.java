package com.example.user.lab03;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class TrolleyWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trolley_widget);
        Intent i = new Intent(context,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.mwidget,pi);
        ComponentName me = new ComponentName(context,TrolleyWidget.class);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(me, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onReceive(Context context,Intent intent) {
        super.onReceive(context,intent);
        if (intent.getAction().equals("android.appwidget.action.static_widget")){
        Bundle bundle = intent.getExtras();
        goods wid_goods=(goods)bundle.getSerializable("wgoods");
        RemoteViews updateview = new RemoteViews(context.getPackageName(),R.layout.trolley_widget);
        Intent i = new Intent(context,Interface_Goods.class);
            i.putExtra("goods",wid_goods);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        i.putExtras(intent.getExtras());
        PendingIntent pi = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        updateview.setTextViewText(R.id.appwidget_text,wid_goods.name_goods+"仅售"+wid_goods.price_goods+"!");
        updateview.setImageViewResource(R.id.appimage,wid_goods.imageid);
        updateview.setOnClickPendingIntent(R.id.mwidget,pi);
        ComponentName me = new ComponentName(context,TrolleyWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(me,updateview);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

