// FlatApiBinder sample
// vim:ts=4 sw=4 noet:
package com.example.flbtest02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.example.common.*

class MainActivity : AppCompatActivity() {

	fun	NdkLibTest()
	{
		var api= NdkLib.getAPI()

		var	result= api.SetParams( 123456789, 32767, 127, 987654321098765432L, 200.5f, 30000.456, "NATIVE-STRING-TEST" )
		api.Assert( result == 1122334455, "SetParams failed" )

		api.Assert( api.AddLong( 555555555, 100000000000000L ) == 100000555555555L, "AddLong failed" )
		api.Assert( api.AddFloat( 1234567.0f, 220 ) == 1234567.0f + 220, "AddFloat failed" )
		api.Assert( api.AddDouble( 123456789.01, 123.123f ) == 123456789.01 + 123.123f, "AddDouble failed" )
	}

	fun	NativeClassTest()
	{
		var api= NdkLib.getAPI()

		var	nc= NativeClass()
		nc.SetParams( 100100, 2020, 101, 303030303030303030L, 4400.44f, 555000.555 )
		api.Assert( nc.GetIntParam() == 100100, "IntParam failed" )
		api.Assert( nc.GetShortParam().compareTo( 2020 ) == 0, "ShortParam failed" )
		api.Assert( nc.GetByteParam().compareTo( 101 ) == 0, "ByteParam failed" )
		api.Assert( nc.GetLongParam() == 303030303030303030L, "LongParam failed" )
		api.Assert( nc.GetFloatParam() == 4400.44f, "FloatParam failed" )
		api.Assert( nc.GetDoubleParam() == 555000.555, "DoubleParam failed" )
		nc.AccessJNIEnv( intArrayOf( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ) );
		nc.release()
	}

	fun	CommonLibTest()
	{
		var	api= NdkLib.getAPI();

		var	lib1= CommonLib( 111, 222 )
		var	lib2= CommonLib( 100, 200 )
		api.Assert( lib1.GetAddParam( 333 ) == 111+222+333, "CommonLib failed" )
		api.Assert( lib2.GetAddParam( 300 ) == 100+200+300, "CommonLib failed" )
		lib1.release( 0 )
		lib2.release( 0 )
	}

	fun	ItemLibTest()
	{
		var	api= NdkLib.getAPI()

		val	ITEM_COUNT= 100
		var	manager= ManagerClass( ITEM_COUNT )
		var	item= ItemClass( 0 )
		for( i in 0..ITEM_COUNT-1 ){
			item.setNativeInstance( manager.GetItem( i ) )
			item.SetItemID( 1000 + i )
			api.Assert( item.GetItemID() == 1000 + i, "ItemLib failed" )
		}
		for( i in 0..ITEM_COUNT-1 ){
			item.setNativeInstance( manager.GetItem( i ) )
			api.Assert( item.GetItemID() == 1000 + i, "ItemLib failed" )
		}
		manager.release()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		NdkLibTest()
		NativeClassTest()
		CommonLibTest()
		ItemLibTest()

		var api= NdkLib.getAPI()
		sample_text.text = api.stringFromJNI()
	}
}

