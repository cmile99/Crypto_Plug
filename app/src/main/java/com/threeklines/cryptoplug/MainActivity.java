package com.threeklines.cryptoplug;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.threeklines.cryptoplug.backside.ApiCenter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private static final String DB_NAME = "mydb.db";
    public static final int DB_VERSION = 1;
    ApiCenter apiCenter = new ApiCenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}