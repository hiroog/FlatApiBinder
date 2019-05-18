// FlatApiBinder sample
// vim:ts=4 sw=4 noet:
package com.example.flbtest01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.common.*;

public class MainActivity extends AppCompatActivity {

	void	NdkLibTest()
	{
		NdkLib	api= NdkLib.getAPI();

		int	result= api.SetParams( 123456789, (short)32767, (byte)127, 987654321098765432L, 200.5f, 30000.456, "NATIVE-STRING-TEST" );
		api.Assert( result == 1122334455, "SetParams failed" );

		api.Assert( api.AddLong( 555555555, 100000000000000L ) == 100000555555555L, "AddLong failed" );
		api.Assert( api.AddFloat( 1234567.0f, 220 ) == 1234567.0f + 220, "AddFloat failed" );
		api.Assert( api.AddDouble( 123456789.01, 123.123f ) == 123456789.01 + 123.123f, "AddDouble failed" );
	}

	void	NativeClassTest()
	{
		NdkLib	api= NdkLib.getAPI();

		NativeClass	nc= NativeClass.create();
		nc.SetParams( 100100, (short)2020, (byte)101, 303030303030303030L, 4400.44f, 555000.555 );
		api.Assert( nc.GetIntParam() == 100100, "IntParam failed" );
		api.Assert( nc.GetShortParam() == 2020, "ShortParam failed" );
		api.Assert( nc.GetByteParam() == 101, "ByteParam failed" );
		api.Assert( nc.GetLongParam() == 303030303030303030L, "LongParam failed" );
		api.Assert( nc.GetFloatParam() == 4400.44f, "FloatParam failed" );
		api.Assert( nc.GetDoubleParam() == 555000.555, "DoubleParam failed" );
		nc.AccessJNIEnv( new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 } );
		nc.release();
	}

	void	CommonLibTest()
	{
		NdkLib	api= NdkLib.getAPI();

		CommonLib	lib1= CommonLib.create( 111, 222 );
		CommonLib	lib2= CommonLib.create( 100, 200 );
		api.Assert( lib1.GetAddParam( 333 ) == 111+222+333, "CommonLib failed" );
		api.Assert( lib2.GetAddParam( 300 ) == 100+200+300, "CommonLib failed" );
		lib1.release( 0 );
		lib2.release( 0 );
	}

	void	ItemLibTest()
	{
		NdkLib	api= NdkLib.getAPI();

		final int		ITEM_COUNT= 100;
		ManagerClass	manager= ManagerClass.create( ITEM_COUNT );
		ItemClass	item= new ItemClass();
		for( int i= 0 ; i< ITEM_COUNT ; i++ ){
			item.setNativeInstance( manager.GetItem( i ) );
			item.SetItemID( 1000 + i );
			api.Assert( item.GetItemID() == 1000 + i, "ItemLib failed" );
		}
		for( int i= 0 ; i< ITEM_COUNT ; i++ ){
			item.setNativeInstance( manager.GetItem( i ) );
			api.Assert( item.GetItemID() == 1000 + i, "ItemLib failed" );
		}
		manager.release();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NdkLibTest();
		NativeClassTest();
		CommonLibTest();
		ItemLibTest();

		NdkLib	api= NdkLib.getAPI();
		TextView tv = findViewById(R.id.sample_text);
		tv.setText( api.stringFromJNI() );
	}
}
