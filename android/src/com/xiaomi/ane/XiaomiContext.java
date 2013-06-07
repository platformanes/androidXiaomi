package com.xiaomi.ane;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

/**
 * 隐射
 * @author Rect
 * @version  Time：2013-5-6 
 */
public class XiaomiContext extends FREContext {
	/**
	 * INIT sdk
	 */
	public static final String XIAOMI_FUNCTION_INIT = "xiaomi_function_init";
	/**
	 * 登录Key
	 */
	public static final String XIAOMI_FUNCTION_LOGIN = "xiaomi_function_login";
	/**
	 * 付费Key
	 */
	public static final String XIAOMI_FUNCTION_PAY = "xiaomi_function_pay";
	/**
	 * 退出Key
	 */
	public static final String XIAOMI_FUNCTION_EXIT = "xiaomi_function_exit";
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		// TODO Auto-generated method stub
		Map<String, FREFunction> map = new HashMap<String, FREFunction>();
	       //映射
		   map.put(XIAOMI_FUNCTION_INIT, new XiaomiInit());
	       map.put(XIAOMI_FUNCTION_LOGIN, new XiaomiLogin());
	       map.put(XIAOMI_FUNCTION_PAY, new XiaomiPay());
	       map.put(XIAOMI_FUNCTION_EXIT, new XiaomiExit());
	       return map;
	}

}
