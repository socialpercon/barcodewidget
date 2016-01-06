package net.woocheol.barcode.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import net.woocheol.barcode.R;

public class MyCustomWidget extends AppWidgetProvider {
	
	private static final String TAG = "MyCustomWidget";
	private Context context;
	
	@Override
	public void onEnabled(Context context) {
		Log.i(TAG, "======================= onEnabled() =======================");
		super.onEnabled(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		Log.i(TAG, "======================= onUpdate() =======================");
		
		this.context = context;
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for(int i=0; i<appWidgetIds.length; i++){ 
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mycustomwidget);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		} 
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.i(TAG, "======================= onDeleted() =======================");
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		Log.i(TAG, "======================= onDisabled() =======================");
		super.onDisabled(context);
	}
	
	/**
	 * UI 설정 이벤트 설정
	 */
	public void initUI(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.i(TAG, "======================= initUI() =======================");
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mycustomwidget);

		Intent activityIntent 			= new Intent(Const.ACTION_CALL_ACTIVITY);

		PendingIntent activityPIntent 		= PendingIntent.getBroadcast(context, 0, activityIntent		, 0);

		views.setOnClickPendingIntent(R.id.btn_call_activity, activityPIntent);

		for(int appWidgetId : appWidgetIds) {
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	/**
	 * Receiver 수신
	 */
	@Override
	public void onReceive(Context context, Intent intent) { 
		super.onReceive(context, intent);
		
		String action = intent.getAction();
		Log.d(TAG, "onReceive() action = " + action);
		
		// Default Recevier
		if(AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action)){
			
		}
		else if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)){
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			initUI(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)){
			
		}
		else if(AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action)){
			
		}
		
		// Custom Recevier
		else if(Const.ACTION_CALL_ACTIVITY.equals(action)){
			callActivity(context);
		}

	}
	
	/**
	 * Activity 호출 (Intent.FLAG_ACTIVITY_NEW_TASK)
	 */
	private void callActivity(Context context){  
		Log.d(TAG, "callActivity()");
		Intent intent = new Intent("net.woocheol.barcode.widget.CALL_ACTIVITY");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
}
