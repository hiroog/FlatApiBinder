// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common
import	com.example.flbtest02.NdkLib

class CommonLib {
	var	NativeThis : Long = 0

	fun	ReleaseInstance( arg : Int ) : Unit = NdkLib.NativeAPI.CommonLibReleaseInstance( NativeThis, arg )
	fun	GetAddParam( param : Int ) : Int = NdkLib.NativeAPI.CommonLibGetAddParam( NativeThis, param )
	//-------------------------------------------------------------------------
	fun	setNativeInstance( api : Long )
	{
		NativeThis= api
	}
	fun	getNativeInstance() : Long = NativeThis
	constructor( api : Long )
	{
		setNativeInstance( api )
	}
	constructor( arg0 : Int, arg1 : Int )
	{
		setNativeInstance( NdkLib.NativeAPI.CommonLibCreateInstance( arg0, arg1 ) )
	}
	fun	release( arg : Int )
	{
		ReleaseInstance( arg )
		NativeThis= 0
	}
}

