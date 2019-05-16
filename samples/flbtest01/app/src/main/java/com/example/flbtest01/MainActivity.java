package com.example.flbtest01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NdkRoot	api= NdkRoot.Init();

		TextView tv = findViewById(R.id.sample_text);
		tv.setText( api.stringFromJNI() );


		int	result= api.SetParams( 123456789, (short)32767, (byte)127, 987654321098765432L, 200.5f, 30000.456, "NATIVE-STRING-TEST" );
		api.Assert( result == 1122334455, "SetParams failed" );

		api.Assert( api.AddLong( 555555555, 100000000000000L ) == 100000555555555L, "AddLong failed" );
		api.Assert( api.AddFloat( 1234567.0f, 220 ) == 1234567.0f + 220, "AddFloat failed" );
		api.Assert( api.AddDouble( 123456789.01, 123.123f ) == 123456789.01 + 123.123f, "AddDouble failed" );


		TestApiClass	test= TestApiClass.Create();
		test.SetParams( 100100, (short)2020, (byte)101, 303030303030303030L, 4400.44f, 555000.555 );
		api.Assert( test.GetIntParam() == 100100, "IntParam failed" );
		api.Assert( test.GetShortParam() == 2020, "ShortParam failed" );
		api.Assert( test.GetByteParam() == 101, "ByteParam failed" );
		api.Assert( test.GetLongParam() == 303030303030303030L, "LongParam failed" );
		api.Assert( test.GetFloatParam() == 4400.44f, "FloatParam failed" );
		api.Assert( test.GetDoubleParam() == 555000.555, "DoubleParam failed" );
		test.AccessJNIEnv( this );
		test.Release();
	}
}
