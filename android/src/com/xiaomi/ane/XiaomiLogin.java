package com.xiaomi.ane;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.xiaomi.gamecenter.sdk.MiCommplatform;
import com.xiaomi.gamecenter.sdk.MiErrorCode;
import com.xiaomi.gamecenter.sdk.OnLoginProcessListener;
import com.xiaomi.gamecenter.sdk.entry.MiAccountInfo;

/**
 * @author Rect
 * @version  Time：2013-5-6 
 */
public class XiaomiLogin implements FREFunction , OnLoginProcessListener{
	private String TAG = "XiaomiLogin";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null; 
		Log.d(TAG, "---------Login -------");
		MiCommplatform.getInstance().miLogin( _context.getActivity(), XiaomiLogin.this );
		return result;
	}
	
	@Override
	public void finishLoginProcess( int arg0, MiAccountInfo arg1 )
	{
		
//		System.out.println("----login-------" + arg0);
		if ( MiErrorCode.MI_XIAOMI_GAMECENTER_SUCCESS == arg0 )//0x0
		{
			Log.d(TAG, "---------登录返回-------");
			// 登陆成功
			//获取用户的登陆后的UID（即用户唯一标识）
			String uid = ""+arg1.getUid();
			//获取用户的登陆的Session（请参考4.2.3.3 流程校验Session 有效性）
			String session = arg1.getSessionId();//若没有登录返回null
			Log.d(TAG, "---------用户登录-------");
			String str = "登陆成功";
			str += "*"+0;
			str += "*"+uid;
			str += "*"+session;
			str += "*"+"first is uid";
			str += "*"+"second is session";
			_context.dispatchStatusEventAsync(TAG, str);
		}
		else if ( MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_LOGINOUT_SUCCESS == arg0 )//-104
		{
			_context.dispatchStatusEventAsync(TAG, "注销成功");
			// 注销成功
		}
		else if ( MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_LOGINOUT_FAIL == arg0 )//-103
		{
			_context.dispatchStatusEventAsync(TAG, "注销失败");
			// 注销失败
		}
		else if ( MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_ACTION_EXECUTED == arg0 )//-18006
		{
			_context.dispatchStatusEventAsync(TAG, "登录操作正在进行中");
			//登录操作正在进行中
		}
		else
		{
			String str = "登录失败";
			str += "*"+"-2";
			_context.dispatchStatusEventAsync(TAG, str);
			// 登录失败
		}
	}
}
