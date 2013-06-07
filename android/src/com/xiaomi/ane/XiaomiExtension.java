package com.xiaomi.ane;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * 隐射
 * @author Rect
 * @version  Time：2013-5-6 
 */
public class XiaomiExtension implements FREExtension {

	@Override
	public FREContext createContext(String arg0) {
		// TODO Auto-generated method stub
		return new XiaomiContext();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

}
