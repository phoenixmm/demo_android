package com.cjf.testdemo.floatwind;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Intent show = new Intent(this, MainService_testTopWindow.class);
//		show.putExtra(MainService_testTopWindow.OPERATION,
//		MainService_testTopWindow.OPERATION_SHOW);
//		this.getApplicationContext().startService(show);

public class MainService_testTopWindow extends Service
{
	public static final String OPERATION = "operation";
	public static final int OPERATION_SHOW = 100;
	public static final int OPERATION_HIDE = 101;

	private static final int HANDLE_CHECK_ACTIVITY = 200;

	private boolean isAdded = false; // �Ƿ���������
	private static WindowManager wm;
	private static WindowManager.LayoutParams params;
	private Button btn_floatView;

//	private List<String> homeList; // ����Ӧ�ó�������б�
	private ActivityManager mActivityManager;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

//		homeList = getHomes();
//		createFloatView();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);

		int operation = intent.getIntExtra(OPERATION, OPERATION_SHOW);
		switch (operation)
		{
		case OPERATION_SHOW:
			mHandler.removeMessages(HANDLE_CHECK_ACTIVITY);
			mHandler.sendEmptyMessage(HANDLE_CHECK_ACTIVITY);
			break;
		case OPERATION_HIDE:
			mHandler.removeMessages(HANDLE_CHECK_ACTIVITY);
			break;
		}
	}

	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case HANDLE_CHECK_ACTIVITY:
				if (isHome())
				{
					if (!isAdded)
					{
//						wm.addView(btn_floatView, params);
//						isAdded = true;
						createFloatView();
					}
				} else
				{
					if (isAdded)
					{
//						wm.removeView(btn_floatView);
//						isAdded = false;
					}
				}
				mHandler.sendEmptyMessageDelayed(HANDLE_CHECK_ACTIVITY, 1000);
				break;
			}
		}
	};

	private int getStatusBarHeight() {
		Resources resources = this.getApplicationContext().getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
		int height = resources.getDimensionPixelSize(resourceId);
		Log.v("cjf---", "Status height:" + height);
		return height;
	}

	private void createFloatView()
	{
		long time=System.currentTimeMillis();
		Date date=new Date(time);
		SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE");
		Log.e("cjf---","time1=" + format.format(date));
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Log.e("cjf---","time2=" + format.format(date));
		format=new SimpleDateFormat("yyyy/MM/dd");
		Log.e("cjf---","time3=" + format.format(date));
		format=new SimpleDateFormat("HH:mm:ss");
		Log.e("cjf---","time4=" + format.format(date));
		format=new SimpleDateFormat("EEEE");
		Log.e("cjf---","time5=" + format.format(date));
		format=new SimpleDateFormat("E");
		Log.e("cjf---","time6=" + format.format(date));

		format=new SimpleDateFormat("MM月dd日 EEE HH:mm");
		Log.e("cjf---","time7=" + format.format(date));

		btn_floatView = new Button(getApplicationContext());
		btn_floatView.setText(format.format(date).toString());
		btn_floatView.setBackgroundColor(Color.TRANSPARENT);
		btn_floatView.setTextColor(Color.WHITE);

		wm = (WindowManager) getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();

		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		params.format = PixelFormat.RGBA_8888;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;//| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

		btn_floatView.setOnTouchListener(new View.OnTouchListener()
		{
			int lastX, lastY;
			int paramX, paramY;

			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						paramX = params.x;
						paramY = params.y;
						break;
					case MotionEvent.ACTION_MOVE:
						int dx = (int) event.getRawX() - lastX;
						int dy = (int) event.getRawY() - lastY;
						params.x = paramX + dx;
						params.y = paramY + dy;
						wm.updateViewLayout(btn_floatView, params);
						Log.e("cjf---","params.x = " + params.x);
						Log.e("cjf---","params.y = " + params.y);


						break;
				}
				return true;
			}
		});


		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;//getStatusBarHeight();

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		Log.e("cjf---","width = " + width);
		Log.e("cjf---","height = " + height);
		Log.e("cjf---","getStatusBarHeight = " + getStatusBarHeight());

		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//		params.x = width / 4;
	params.y = - getStatusBarHeight() - 30; //?????

		wm.addView(btn_floatView, params);
		isAdded = true;
	}

	/**
	 *
	 * 
	 * @return
	 */
	private List<String> getHomes()
	{
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(
				intent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo ri : resolveInfo)
		{
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}

	/**
	 *
	 */
	public boolean isHome()
	{
		if (mActivityManager == null)
		{
			mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		}
		List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return true;
		// return homeList.contains(rti.get(0).topActivity.getPackageName());
	}

}
