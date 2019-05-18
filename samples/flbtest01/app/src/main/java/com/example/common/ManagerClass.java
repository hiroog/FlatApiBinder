// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common;
import	com.example.flbtest01.NdkLib;

public class ManagerClass {
	long	NativeThis= 0;

	public static long	CreateInstance( int talbe_size )
	{
		return	NdkLib.NativeAPI.ManagerClassCreateInstance( talbe_size );
	}
	public void	ReleaseInstance()
	{
		NdkLib.NativeAPI.ManagerClassReleaseInstance( NativeThis );
	}
	public long	GetItem( int index )
	{
		return	NdkLib.NativeAPI.ManagerClassGetItem( NativeThis, index );
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
	public ManagerClass()
	{
	}
	public ManagerClass( long api )
	{
		setNativeInstance( api );
	}
	public static ManagerClass	create( int talbe_size )
	{
		return	new ManagerClass( CreateInstance( talbe_size ) );
	}
	public void	release()
	{
		ReleaseInstance();
		NativeThis= 0;
	}
}

