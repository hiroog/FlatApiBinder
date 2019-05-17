// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common
import	com.example.flbtest02.NdkLib

class ManagerClass {
	var	NativeThis : Long = 0

	fun	ReleaseInstance() : Unit = NdkLib.NativeAPI.ManagerClassReleaseInstance( NativeThis )
	fun	GetItem( index : Int ) : Long = NdkLib.NativeAPI.ManagerClassGetItem( NativeThis, index )
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
	constructor( talbe_size : Int )
	{
		setNativeInstance( NdkLib.NativeAPI.ManagerClassCreateInstance( talbe_size ) )
	}
	fun	release()
	{
		ReleaseInstance()
		NativeThis= 0
	}
}

