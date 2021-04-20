package com.nz.sdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.hhhaiai.nzlist.memory.ProcessHolder;
import me.hhhaiai.nzlist.ui.NzListActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoGo:
                start();
                break;
            default:
                break;
        }
    }

    private void start() {
//        startActivity(new Intent(MainActivity.this, NzListActivity.class));
//        try {
//            Bundle b = new Bundle();
//
//            ArrayList value = new ArrayList();
//            value.add(new P1());
//            value.add(new P2());
//            value.add(new P3());
//            value.add(new P4());
//            b.putParcelableArrayList(NzListActivity.KEY_DATA_PROCESS, value);
//            Intent intent = new Intent(MainActivity.this, NzListActivity.class);
////            intent.putExtras(b);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            startActivity(intent);
//
//
//            intent.putExtra(NzListActivity.KEY_DATA_PROCESS, value);
//            startActivity(intent);
//
//        } catch (Throwable e) {
//            NzAppLog.e(e);
//        }

        ProcessHolder.getInstance().addPorceser(new P1());
        ProcessHolder.getInstance().addPorceser(new P2());
        ProcessHolder.getInstance().addPorceser(new P3());
        ProcessHolder.getInstance().addPorceser(new P4());
        startActivity(new Intent(MainActivity.this, NzListActivity.class));
    }

}