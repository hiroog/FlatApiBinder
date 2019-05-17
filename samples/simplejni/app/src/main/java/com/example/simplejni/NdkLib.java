// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.simplejni;

public class NdkLib {

	// NdkDefault

	// NdkLib
	public native String	stringFromJNI();

	//-------------------------------------------------------------------------
	static {
		System.loadLibrary("native-lib");
	}
	public static NdkLib	NativeAPI= new NdkLib();
	public static NdkLib	getAPI()
	{
		return	NativeAPI;
	}
}

