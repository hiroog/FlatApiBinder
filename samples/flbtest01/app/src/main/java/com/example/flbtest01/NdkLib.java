// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest01;

public class NdkLib {

	// NdkDefault

	// NdkLib
	public native String	stringFromJNI();
	public native void	Assert( boolean expr, String message );
	public native int	SetParams( int a0, short a1, byte a2, long a3, float a4, double a5, String a6 );
	public native long	AddLong( int a0, long a1 );
	public native float	AddFloat( float a0, int a1 );
	public native double	AddDouble( double a0, float a1 );

	// NativeClass
	public native long	NativeClassCreateInstance();
	public native void	NativeClassReleaseInstance( long jj_this );
	public native void	NativeClassSetParams( long jj_this, int a0, short a1, byte a2, long a3, float a4, double a5 );
	public native int	NativeClassGetIntParam( long jj_this );
	public native short	NativeClassGetShortParam( long jj_this );
	public native byte	NativeClassGetByteParam( long jj_this );
	public native long	NativeClassGetLongParam( long jj_this );
	public native float	NativeClassGetFloatParam( long jj_this );
	public native double	NativeClassGetDoubleParam( long jj_this );
	public native void	NativeClassAccessJNIEnv( long jj_this, Object java_object );

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

