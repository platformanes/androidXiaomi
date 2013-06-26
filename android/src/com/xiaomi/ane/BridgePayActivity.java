package com.xiaomi.ane;


import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;
import com.xiaomi.gamecenter.sdk.MiCommplatform;
import com.xiaomi.gamecenter.sdk.MiErrorCode;
import com.xiaomi.gamecenter.sdk.OnPayProcessListener;
import com.xiaomi.gamecenter.sdk.entry.MiBuyInfoOnline;
import com.xiaomi.gamecenter.sdk.entry.PayMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BridgePayActivity extends Activity implements
OnClickListener ,OnPayProcessListener{
	//声明开启Activity的Action
	public static final String MYACTIVITY_ACTION = "com.xiaomi.ane.BridgePayActivity";
	private String TAG = "BridgePayActivity";
	
	public static String CpOrderId ;
	public static String CpUserInfo ; 
	public static String MiBi ;
	public static String ortherflag ;
	public static String otherkey ;
	public static int needPayCoins;
	
	
	public static Activity callBackActivity ;
	public static FREContext _context;
	private LinearLayout layout;
	private static Boolean isExit = false;

	protected static final int UPDATE_TEXT = 0;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case 1:
			Log.d(TAG, "---------退出-------");
			isExit = false;
			BridgePayActivity.this.finish(); 
			break;
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//构建界面

		super.onCreate(savedInstanceState);
		Log.d(TAG, "---------在这里调用JAR 执行付费-------");
		
		if(!isExit){
			//大众支付方式
			if(XiaomiInit.ispayMode)
				MiCommplatform.getInstance().update_pay_mode( PayMode.custom );
			else
				MiCommplatform.getInstance().update_pay_mode( PayMode.simple );
			
			Log.d(TAG, "---------支付开始-------");
			Log.d(TAG, "---------支付-------"+CpOrderId+CpUserInfo+MiBi+ortherflag+otherkey);
			int money = Integer.parseInt(MiBi);
			if(money == 0)money = 5;
			MiBuyInfoOnline online = new MiBuyInfoOnline();
			online.setCpOrderId( CpOrderId );
			online.setCpUserInfo( CpUserInfo );
			online.setMiBi(money );
			try
			{
				MiCommplatform.getInstance().miUniPayOnline(_context.getActivity(),online,this);
			}
			catch ( Exception e )
			{
				_context.dispatchStatusEventAsync(TAG, "PayError:"+e.getMessage());
			}
		}
			
		Log.d(TAG, "---------getResourceId--2-----");
		// 隐藏标题栏  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		// 隐藏状态栏  
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(_context.getResourceId("drawable.bg"));
		this.setContentView(layout);
		

		TextView textv = new TextView(this);
		String str_2 = "跳转支付验证！点击任意返回游戏....";
		textv.setText(str_2);
		layout.addView(textv);
		
		layout.setId(1);
		layout.setOnClickListener(this);

		
		isExit = true;

	}
	
	@Override
	public void finishPayProcess( int arg0 )
	{
		Log.d(TAG, "---------支付返回-------key:"+arg0);
		
		if ( arg0 == MiErrorCode.MI_XIAOMI_GAMECENTER_SUCCESS )// 成功
		{
			_context.dispatchStatusEventAsync(TAG, "购买成功");
		}
		else if ( arg0 == MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_CANCEL || arg0 == MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_PAY_CANCEL )// 取消
		{
			_context.dispatchStatusEventAsync(TAG, "取消购买");
		}
		else if ( arg0 == MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_PAY_FAILURE )// 失败
		{
			_context.dispatchStatusEventAsync(TAG, "购买失败");
		}
		else if ( arg0 == MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_ACTION_EXECUTED )
		{
			_context.dispatchStatusEventAsync(TAG, "正在执行，不要重复操作");
		}
		else if( arg0 == MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_LOGIN_FAIL )
		{
			_context.dispatchStatusEventAsync(TAG, "感谢充值，为确保安全，请先登陆");//您还没登录 请重新登录
		}
	}
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{  
		if (keyCode == KeyEvent.KEYCODE_BACK )  
		{  
			isExit = false;
		}  
		return super.onKeyDown(keyCode, event);

	}  

}
