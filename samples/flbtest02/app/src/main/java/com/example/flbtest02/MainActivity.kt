// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

package com.example.flbtest02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		var api= NdkLib.getAPI()

		sample_text.text = api.stringFromJNI()


		var	result= api.SetParams( 123456789, 32767, 127, 987654321098765432L, 200.5f, 30000.456, "NATIVE-STRING-TEST" )
		api.Assert( result == 1122334455, "SetParams failed" )

		api.Assert( api.AddLong( 555555555, 100000000000000L ) == 100000555555555L, "AddLong failed" )
		api.Assert( api.AddFloat( 1234567.0f, 220 ) == 1234567.0f + 220, "AddFloat failed" )
		api.Assert( api.AddDouble( 123456789.01, 123.123f ) == 123456789.01 + 123.123f, "AddDouble failed" )


		var	test= NativeClass()
		test.SetParams( 100100, 2020, 101, 303030303030303030L, 4400.44f, 555000.555 )
		api.Assert( test.GetIntParam() == 100100, "IntParam failed" )
		api.Assert( test.GetShortParam().compareTo( 2020 ) == 0, "ShortParam failed" )
		api.Assert( test.GetByteParam().compareTo( 101 ) == 0, "ByteParam failed" )
		api.Assert( test.GetLongParam() == 303030303030303030L, "LongParam failed" )
		api.Assert( test.GetFloatParam() == 4400.44f, "FloatParam failed" )
		api.Assert( test.GetDoubleParam() == 555000.555, "DoubleParam failed" )
		test.AccessJNIEnv( intArrayOf( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ) );
		test.release()
	}
}

