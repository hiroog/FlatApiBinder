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
	constructor( api : Long = 0 )
	{
		setNativeInstance( api )
	}
	fun	release()
	{
		ReleaseInstance()
		NativeThis= 0
	}
	companion object {
	//-------------------------------------------------------------------------
	fun	create( talbe_size : Int ) : ManagerClass
	{
		return	ManagerClass( NdkLib.NativeAPI.ManagerClassCreateInstance( talbe_size ) )
	}
	//-------------------------------------------------------------------------
	}
}

