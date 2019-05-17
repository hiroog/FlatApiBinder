// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common
import	com.example.flbtest02.NdkLib

class ItemClass {
	var	NativeThis : Long = 0

	fun	SetItemID( item_id : Int ) : Unit = NdkLib.NativeAPI.ItemClassSetItemID( NativeThis, item_id )
	fun	GetItemID() : Int = NdkLib.NativeAPI.ItemClassGetItemID( NativeThis )
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
}

