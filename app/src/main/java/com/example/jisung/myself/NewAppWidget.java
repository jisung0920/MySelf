package com.example.jisung.myself;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    SharedPreferences tmp;
    SharedPreferences.Editor editor;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String str) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text,str);
        Intent intent=new Intent(context, MainActivity.class);
        PendingIntent pe=PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pe);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        tmp = context.getSharedPreferences("test",MODE_PRIVATE);

        String str = tmp.getString("first"," ");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,str);
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

