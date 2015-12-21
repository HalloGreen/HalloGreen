package com.example.hsienyu.lab2;

/**
 * Created by hsienyu on 2015/12/21.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Override;

public class sunActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun);
        Button closeButton = (Button) findViewById(R.id.Close);
        closeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO:
                // This function closes Activity Two
                // Hint: use Context's finish() method
                finish();
            }
        });
    }
}
