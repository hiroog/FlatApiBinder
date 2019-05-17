// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.flbtest01;

public class NativeClass {
	long	NativeThis;

	public static long	CreateInstance()
	{
		return	NdkLib.NativeAPI.NativeClassCreateInstance();
	}
	public void	ReleaseInstance()
	{
		NdkLib.NativeAPI.NativeClassReleaseInstance( NativeThis );
	}
	public void	SetParams( int a0, short a1, byte a2, long a3, float a4, double a5 )
	{
		NdkLib.NativeAPI.NativeClassSetParams( NativeThis, a0, a1, a2, a3, a4, a5 );
	}
	public int	GetIntParam()
	{
		return	NdkLib.NativeAPI.NativeClassGetIntParam( NativeThis );
	}
	public short	GetShortParam()
	{
		return	NdkLib.NativeAPI.NativeClassGetShortParam( NativeThis );
	}
	public byte	GetByteParam()
	{
		return	NdkLib.NativeAPI.NativeClassGetByteParam( NativeThis );
	}
	public long	GetLongParam()
	{
		return	NdkLib.NativeAPI.NativeClassGetLongParam( NativeThis );
	}
	public float	GetFloatParam()
	{
		return	NdkLib.NativeAPI.NativeClassGetFloatParam( NativeThis );
	}
	public double	GetDoubleParam()
	{
		return	NdkLib.NativeAPI.NativeClassGetDoubleParam( NativeThis );
	}
	public void	AccessJNIEnv( Object java_object )
	{
		NdkLib.NativeAPI.NativeClassAccessJNIEnv( NativeThis, java_object );
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
	public NativeClass( long api )
	{
		setNativeInstance( api );
	}
	public NativeClass()
	{
		setNativeInstance( CreateInstance() );
	}
	public void	release()
	{
		ReleaseInstance();
		NativeThis= 0;
	}
}

