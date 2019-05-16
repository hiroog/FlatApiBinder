// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest01;

public class NdkRoot {
	static {
		System.loadLibrary("native-lib");
	}

	// NdkApiDefault

	// NdkRoot
	public native String	stringFromJNI();
	public native void	Assert( boolean expr, String message );
	public native int	SetParams( int a0, short a1, byte a2, long a3, float a4, double a5, String a6 );
	public native long	AddLong( int a0, long a1 );
	public native float	AddFloat( float a0, int a1 );
	public native double	AddDouble( double a0, float a1 );

	// TestApiClass
	public native long	TestApiClassCreateAPI();
	public native void	TestApiClassReleaseAPI( long jj_this );
	public native void	TestApiClassSetParams( long jj_this, int a0, short a1, byte a2, long a3, float a4, double a5 );
	public native int	TestApiClassGetIntParam( long jj_this );
	public native short	TestApiClassGetShortParam( long jj_this );
	public native byte	TestApiClassGetByteParam( long jj_this );
	public native long	TestApiClassGetLongParam( long jj_this );
	public native float	TestApiClassGetFloatParam( long jj_this );
	public native double	TestApiClassGetDoubleParam( long jj_this );
	public native void	TestApiClassAccessJNIEnv( long jj_this, Object java_object );

	// ------------------------------------------------------------------------
	public static NdkRoot	NativeInstance;
	public static NdkRoot	Init()
	{
		NativeInstance= new NdkRoot();
		return	NativeInstance;
	}
}

