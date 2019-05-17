// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common;
import	com.example.flbtest03.NdkLib;

public class CommonLib {
	long	NativeThis;

	public static long	CreateInstance( int arg0, int arg1 )
	{
		return	NdkLib.NativeAPI.CommonLibCreateInstance( arg0, arg1 );
	}
	public void	ReleaseInstance( float param )
	{
		NdkLib.NativeAPI.CommonLibReleaseInstance( NativeThis, param );
	}
	//-------------------------------------------------------------------------
	public void	setNativeInstance( long api )
	{
		NativeThis= api;
	}
	public long	getNativeInstance()
	{
		return	NativeThis;
	}
	public CommonLib( long api )
	{
		setNativeInstance( api );
	}
	public CommonLib( int arg0, int arg1 )
	{
		setNativeInstance( CreateInstance( arg0, arg1 ) );
	}
	public void	release( float param )
	{
		ReleaseInstance( param );
		NativeThis= 0;
	}
}

