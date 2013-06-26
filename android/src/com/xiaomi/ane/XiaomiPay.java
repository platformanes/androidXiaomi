package com.xiaomi.ane;


import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.xiaomi.gamecenter.sdk.MiCommplatform;
import com.xiaomi.gamecenter.sdk.MiErrorCode;
import com.xiaomi.gamecenter.sdk.OnPayProcessListener;
import com.xiaomi.gamecenter.sdk.entry.MiBuyInfoOnline;
import com.xiaomi.gamecenter.sdk.entry.PayMode;

/**
 * @author Rect
 * @version  Time：2013-5-6 
 */
public class XiaomiPay implements FREFunction ,OnPayProcessListener{

	private String TAG = "XiaomiPay";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] $args) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null; 
		String CpOrderId = null;
		String CpUserInfo = null;
		String MiBi = null;
		//以下两个参数备选
		String ortherflag = null;
		String otherkey = null;
		int money = 5;
		
		if($args.length<1)
		{
			_context.dispatchStatusEventAsync(TAG,"参数不正确！");
			return null;
		}
		
		try{
			FREArray __array = (FREArray) $args[0];
			int __len = (int)__array.getLength();
			if(__len != 5)
			{
				_context.dispatchStatusEventAsync(TAG,"传入数组参数不正确！");
				return null;
			}

			BridgePayActivity.CpOrderId = __array.getObjectAt(0).getAsString();
			BridgePayActivity.CpUserInfo = __array.getObjectAt(1).getAsString();
			BridgePayActivity.MiBi = (__array.getObjectAt(2).getAsString()); 
			BridgePayActivity.ortherflag = __array.getObjectAt(3).getAsString();
			BridgePayActivity.otherkey = __array.getObjectAt(4).getAsString();

			//call back string
		}catch (Exception e) { 
			// TODO: handle exception
			_context.dispatchStatusEventAsync(TAG, "PayError:"+e.getMessage());
			return null;
		}
		
		
		BridgePayActivity._context = _context;
		Log.d(TAG, "---------Intent处理-------");
		Intent intent = new Intent(BridgePayActivity.MYACTIVITY_ACTION);
		_context.getActivity().startActivity(intent);
		Log.d(TAG, "---------start处理-------");
		Log.d(TAG, "---------函数返回-------");
		
		return result;
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
}
