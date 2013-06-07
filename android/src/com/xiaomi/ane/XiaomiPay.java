package com.xiaomi.ane;


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

			CpOrderId = __array.getObjectAt(0).getAsString();
			CpUserInfo = __array.getObjectAt(1).getAsString();
			MiBi = (__array.getObjectAt(2).getAsString()); 
			ortherflag = __array.getObjectAt(3).getAsString();
			otherkey = __array.getObjectAt(4).getAsString();

			//call back string
		}catch (Exception e) { 
			// TODO: handle exception
			_context.dispatchStatusEventAsync(TAG, "PayError:"+e.getMessage());
		}
		
		//大众支付方式
		if(XiaomiInit.ispayMode)
			MiCommplatform.getInstance().update_pay_mode( PayMode.custom );
		else
			MiCommplatform.getInstance().update_pay_mode( PayMode.simple );
		
		Log.d(TAG, "---------支付开始-------");
		Log.d(TAG, "---------支付-------"+CpOrderId+CpUserInfo+MiBi+ortherflag+otherkey);
		money = Integer.parseInt(MiBi);
		if(money == 0)money = 5;
		MiBuyInfoOnline online = new MiBuyInfoOnline();
		online.setCpOrderId( CpOrderId );
		online.setCpUserInfo( CpUserInfo );
		online.setMiBi(money );
		try
		{
			MiCommplatform.getInstance().miUniPayOnline(_context.getActivity(),online,XiaomiPay.this);
		}
		catch ( Exception e )
		{
			_context.dispatchStatusEventAsync(TAG, "PayError:"+e.getMessage());
		}
		
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
			_context.dispatchStatusEventAsync(TAG, "您还没有登陆，请先登陆");
		}
	}
}
