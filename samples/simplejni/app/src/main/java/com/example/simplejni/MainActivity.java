// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

package com.example.simplejni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NdkLib  app= NdkLib.getAPI();

        TextView tv = findViewById(R.id.sample_text);
        tv.setText(app.stringFromJNI());
    }
}
