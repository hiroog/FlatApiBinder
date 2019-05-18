// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common;
import	com.example.flbtest01.NdkLib;

public class CommonLib {
	long	NativeThis= 0;

	public static long	CreateInstance( int arg0, int arg1 )
	{
		return	NdkLib.NativeAPI.CommonLibCreateInstance( arg0, arg1 );
	}
	public void	ReleaseInstance( int arg )
	{
		NdkLib.NativeAPI.CommonLibReleaseInstance( NativeThis, arg );
	}
	public int	GetAddParam( int param )
	{
		return	NdkLib.NativeAPI.CommonLibGetAddParam( NativeThis, param );
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
	public CommonLib()
	{
	}
	public CommonLib( long api )
	{
		setNativeInstance( api );
	}
	public static CommonLib	create( int arg0, int arg1 )
	{
		return	new CommonLib( CreateInstance( arg0, arg1 ) );
	}
	public void	release( int arg )
	{
		ReleaseInstance( arg );
		NativeThis= 0;
	}
}

