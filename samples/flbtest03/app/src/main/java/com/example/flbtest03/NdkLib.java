// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest03;

public class NdkLib {

	// NdkDefault

	// NdkLib
	public native String	stringFromJNI();
	public native long	getCommonLibInstance();

	// CommonLib
	public native long	CommonLibCreateInstance( int arg0, int arg1 );
	public native void	CommonLibReleaseInstance( long jj_this, float param );

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

