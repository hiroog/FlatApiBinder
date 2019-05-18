// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.common;
import	com.example.flbtest01.NdkLib;

public class ItemClass {
	long	NativeThis= 0;

	public void	SetItemID( int item_id )
	{
		NdkLib.NativeAPI.ItemClassSetItemID( NativeThis, item_id );
	}
	public int	GetItemID()
	{
		return	NdkLib.NativeAPI.ItemClassGetItemID( NativeThis );
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
	public ItemClass()
	{
	}
	public ItemClass( long api )
	{
		setNativeInstance( api );
	}
}

