package com.nz.sdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.hhhaiai.nzlist.ui.NezhaListActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoGo:
                startActivity(new Intent(MainActivity.this, NezhaListActivity.class));
                break;
            default:
                break;
        }
    }
}