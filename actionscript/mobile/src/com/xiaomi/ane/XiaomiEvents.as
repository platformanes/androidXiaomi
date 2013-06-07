package com.xiaomi.ane 
{ 
	/**
	 * 
	 * @author Rect  2013-5-6 
	 * 
	 */
	public class XiaomiEvents 
	{ 
		public function XiaomiEvents()
		{
		} 
		/**************************平台通知************************************/
		/**
		 *init 
		 */		
		public static const XIAOMI_SDK_STATUS:String = "XiaomiInit";
		/**
		 * 用户登录
		 */
		public static const XIAOMI_LOGIN_STATUS : String = "XiaomiLogin";
		
		/**
		 * 用户注销
		 */
		public static const XIAOMI_LOGOUT_STATUS : String = "XiaomiExit";
		
		/**
		 * 充值
		 */
		public static const XIAOMI_PAY_STATUS : String = "XiaomiPay";
	} 
}