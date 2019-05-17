// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

package com.example.flbtest03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.common.CommonLib;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NdkLib  api= NdkLib.getAPI();

        TextView tv = findViewById(R.id.sample_text);
        tv.setText(api.stringFromJNI());


		CommonLib	lib= new CommonLib( 123, 456 );
		lib.release( 78.9f );
    }
}
