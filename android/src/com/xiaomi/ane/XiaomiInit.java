package com.xiaomi.ane;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.xiaomi.gamecenter.sdk.MiCommplatform;
import com.xiaomi.gamecenter.sdk.entry.MiAppInfo;
import com.xiaomi.gamecenter.sdk.entry.MiGameType;
import com.xiaomi.gamecenter.sdk.entry.PayMode;
import com.xiaomi.gamecenter.sdk.entry.ScreenOrientation;

/**
 * @author Rect
 * @version  Time：2013-5-6 
 */
public class XiaomiInit implements FREFunction {
	private String TAG = "XiaomiInit";
	private FREContext _context;
	public static Boolean ispayMode = false;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null; 
		/**SDK初始化*/
		MiAppInfo appInfo = new MiAppInfo();
		int appID = 0;
		String appKey = null;
		Boolean appType = null;
		Boolean appPayMode = null;
		Boolean OrinentationMode = null;
		try
		{
			appID = arg1[0].getAsInt();
			appKey = arg1[1].getAsString();
			appType = arg1[2].getAsBool();
			appPayMode = arg1[3].getAsBool();
			OrinentationMode = arg1[4].getAsBool();
			ispayMode = appPayMode;
		}
		catch(Exception e)
		{
			_context.dispatchStatusEventAsync(TAG, "初始化参数错误");
			return null;
		}
		appInfo.setAppId( appID );
		appInfo.setAppKey( appKey );
		
		if(appType)
			appInfo.setAppType( MiGameType.online );
		else 
			appInfo.setAppType( MiGameType.offline );
		
		if(appPayMode)
			appInfo.setPayMode( PayMode.custom ); //支付方式
		else 
			appInfo.setPayMode( PayMode.simple ); //支付方式
		
		if(OrinentationMode)
			appInfo.setOrientation( ScreenOrientation.horizontal ); //横竖屏
		else 
			appInfo.setOrientation( ScreenOrientation.vertical ); //横竖屏
		
		

		MiCommplatform.Init( _context.getActivity(), appInfo );
		Log.d(TAG, "---------初始化返回-------");
		_context.dispatchStatusEventAsync(TAG, "初始化返回:"+appID+"|"+appKey+"|"+appType+"|"+appPayMode+"|"+OrinentationMode);
		return result;
	}

}
