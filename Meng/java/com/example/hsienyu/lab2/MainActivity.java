package com.example.hsienyu.lab2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.Override;

public class MainActivity extends Activity {

    // Use these as keys when you're saving state between reconfigurations
    private static final String RESTART_KEY = "restart";
    private static final String RESUME_KEY = "resume";
    private static final String START_KEY = "start";
    private static final String CREATE_KEY = "create";

    // String for LogCat documentation
    private final static String TAG = "Lab-ActivityOne";

    // Lifecycle counters

    // TODO:
    // Create variables named
    // mCreate, mRestart, mStart and mResume
    // to count calls to onCreate(), onRestart(), onStart() and
    // onResume(). These variables should not be defined as static.

    // You will need to increment these variables' values when their
    // corresponding lifecycle methods get called.
    private int mCreate, mRestart, mStart, mResume = 0;

    // TODO: Create variables for each of the TextViews
    // named mTvCreate, mTvRestart, mTvStart, mTvResume.
    // for displaying the current count of each counter variable
    private TextView mTvCreate, mTvRestart, mTvStart, mTvResume;

    private RelativeLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.my_layout);
        mLayout = (RelativeLayout) findViewById(R.id.my_layout);
        Resources res = this.getResources();
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = res.getDrawable(R.drawable.green, getTheme());
            mLayout.setBackground(drawable);
        } else {
            drawable = res.getDrawable(R.drawable.pink);
            mLayout.setBackgroundDrawable(drawable);
        }
       // mCreate++;
        // TODO: Assign the appropriate TextViews to the TextView variables
        // Hint: Access the TextView by calling Activity's findViewById()
        // textView1 = (TextView) findViewById(R.id.textView1);
        mTvCreate = (TextView)	findViewById(R.id.create);
        mTvRestart = (TextView) findViewById(R.id.restart);
        mTvStart = (TextView) findViewById(R.id.start);
        mTvResume = (TextView) findViewById(R.id.resume);
        //mTvCreate.append("()" + " " + "calls" + ":" + " "+ Integer.toString(mCreate));

        Button launchActivityTwoButton = (Button) findViewById(R.id.bLaunchActivityTwo);
        launchActivityTwoButton.setOnClickListener(new OnClickListener() {

            @Override

            public void onClick(View v) {
                // TODO:
                // Launch Activity Two
                // Hint: use Context's startActivity() method
                // Create an intent stating which Activity you would like to
                // start
                Intent activityTwo = new Intent("com.example.hsienyu.lab2.Main2Activity");
                // Launch the Activity using the intent
                //Intent activityTwo = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(activityTwo);
            }
        });

        Button launchSunActivityButton = (Button) findViewById(R.id.sun);
        launchSunActivityButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sunActivity = new Intent("com.example.hsienyu.lab2.sunActivity");
                startActivity(sunActivity);
            }
        });

        Button launchWaterActivityButton = (Button) findViewById(R.id.water);
        launchWaterActivityButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent waterActivity = new Intent("com.example.hsienyu.lab2.waterActivity");
                startActivity(waterActivity);
            }
        });

        Button launchHumidActivityButton = (Button) findViewById(R.id.humid);
        launchHumidActivityButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent humidActivity = new Intent("com.example.hsienyu.lab2.humidActivity");
                startActivity(humidActivity);
            }
        });

        // Has previous state been saved?
        if (savedInstanceState != null) {
            // TODO:
            // Restore value of counters from saved state
            // Only need 4 lines of code, one for every count variable
            savedInstanceState.getInt(CREATE_KEY);
            savedInstanceState.getInt(RESTART_KEY);
            savedInstanceState.getInt(START_KEY);
            savedInstanceState.getInt(RESUME_KEY);
        }

        // Emit LogCat message
        Log.i(TAG, "Entered the onCreate() method" + mCreate);

        // TODO:
        // Update the appropriate count variable
        // Update the user interface via the displayCounts() method
        mCreate++;
        displayCounts();
    }

    // Lifecycle callback overrides

    @Override
    public void onStart() {
        super.onStart();

        // Emit LogCat message
        Log.i(TAG, "Entered the onStart() method" + mStart);

        // TODO:
        // Update the appropriate count variable
        // Update the user interface
        mStart++;
        displayCounts();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Emit LogCat message
        Log.i(TAG, "Entered the onResume() method" + mResume);

        // TODO:
        // Update the appropriate count variable
        // Update the user interface
        mResume++;
        displayCounts();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Emit LogCat message
        Log.i(TAG, "Entered the onPause() method" + mStart);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Emit LogCat message
        Log.i(TAG, "Entered the onStop() method" + mStart);

    }

    @Override
    public void onRestart() {
        super.onRestart();

        // Emit LogCat message
        Log.i(TAG, "Entered the onRestart() method" + mResume);

        // TODO:
        // Update the appropriate count variable
        // Update the user interface
        mResume++;
        displayCounts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Emit LogCat message
        Log.i(TAG, "Entered the onDestroy() method");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // TODO:
        // Save state information with a collection of key-value pairs
        // 4 lines of code, one for every count variable
        savedInstanceState.putInt(CREATE_KEY, mCreate);
        savedInstanceState.putInt(RESTART_KEY, mRestart);
        savedInstanceState.putInt(START_KEY, mStart);
        savedInstanceState.putInt(RESUME_KEY, mResume);
    }

    // Updates the displayed counters
    // This method expects that the counters and TextView variables use the
    // names
    // specified above
    public void displayCounts() {

        // TODO - uncomment these lines

		mTvCreate.setText(String.format("onCreate() calls: %d", mCreate));
		mTvStart.setText(String.format("onStart() calls: %d", mStart));
		mTvResume.setText(String.format("onResume() calls: %d", mResume));
		mTvRestart.setText(String.format("onRestart() calls: %d", mRestart));

    }
}
