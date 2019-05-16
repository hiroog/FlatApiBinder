// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest01;

public class TestApiClass {
	long	NativeThis;

	public static long	CreateAPI()
	{
		return	NdkRoot.NativeInstance.TestApiClassCreateAPI();
	}
	public void	ReleaseAPI()
	{
		NdkRoot.NativeInstance.TestApiClassReleaseAPI( NativeThis );
	}
	public void	SetParams( int a0, short a1, byte a2, long a3, float a4, double a5 )
	{
		NdkRoot.NativeInstance.TestApiClassSetParams( NativeThis, a0, a1, a2, a3, a4, a5 );
	}
	public int	GetIntParam()
	{
		return	NdkRoot.NativeInstance.TestApiClassGetIntParam( NativeThis );
	}
	public short	GetShortParam()
	{
		return	NdkRoot.NativeInstance.TestApiClassGetShortParam( NativeThis );
	}
	public byte	GetByteParam()
	{
		return	NdkRoot.NativeInstance.TestApiClassGetByteParam( NativeThis );
	}
	public long	GetLongParam()
	{
		return	NdkRoot.NativeInstance.TestApiClassGetLongParam( NativeThis );
	}
	public float	GetFloatParam()
	{
		return	NdkRoot.NativeInstance.TestApiClassGetFloatParam( NativeThis );
	}
	public double	GetDoubleParam()
	{
		return	NdkRoot.NativeInstance.TestApiClassGetDoubleParam( NativeThis );
	}
	public void	AccessJNIEnv( Object java_object )
	{
		NdkRoot.NativeInstance.TestApiClassAccessJNIEnv( NativeThis, java_object );
	}
	// ------------------------------------------------------------------------
	public void	SetNativeAPI( long api )
	{
		NativeThis= api;
	}
	public long	GetNativeAPI()
	{
		return	NativeThis;
	}
	public TestApiClass()
	{
	}
	public TestApiClass( long api )
	{
		NativeThis= api;
	}
	public static TestApiClass	Create()
	{
		return	new TestApiClass( CreateAPI() );
	}
	public void	Release()
	{
		ReleaseAPI();
		NativeThis= 0;
	}
}

