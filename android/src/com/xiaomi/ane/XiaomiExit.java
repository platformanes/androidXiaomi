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
public class XiaomiExit implements FREFunction ,OnLoginProcessListener{

	private String TAG = "XiaomiExit";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null; 
		MiCommplatform.getInstance().miLogout( XiaomiExit.this );
		return result;
	}
	@Override
	public void finishLoginProcess( int arg0, MiAccountInfo arg1 )
	{
		
		if ( MiErrorCode.MI_XIAOMI_GAMECENTER_SUCCESS == arg0 )
		{
			Log.d(TAG, "---------登录返回-------");
			_context.dispatchStatusEventAsync(TAG, "登录返回");
		}
		else if ( MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_LOGINOUT_SUCCESS == arg0 )
		{
			Log.d(TAG, "---------注销成功------");
			_context.dispatchStatusEventAsync(TAG, "注销成功");
			// 注销成功
		}
		else if ( MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_LOGINOUT_FAIL == arg0 )
		{
			// 注销失败
			_context.dispatchStatusEventAsync(TAG, "注销失败");
		}
		else if ( MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_ACTION_EXECUTED == arg0 )
		{
			//登录操作正在进行中
			_context.dispatchStatusEventAsync(TAG, "登录操作正在进行中");
		}
		else
		{
			// 登录失败
			String str = "登录失败";
			str += "*"+"-2";
			_context.dispatchStatusEventAsync(TAG, str);
		}
	}
}
