//package com.gooker.bloomingvioletssimple.automatic;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.gooker.bloomingvioletssimple.R;
//import com.gooker.bloomingvioletssimple.untiltools.SmoothCheckBox;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by socoy on 2016/12/10.
// */
//
//public class AutomationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
//    private Spinner SurvivalRate, Operator;
//    private ListView LItem;
//    private Button Stop, Loop, Single, Add, Reset, Edit, Upload, Download, Read, Analysis;
//    private SmoothCheckBox StartupScriptBox, UserBox, SecStuBox, ClrDataBox, Sud15sBox, Sud3sBox, ChgRsBox;
//    private EditText NAmount, Time, DayNum, ChanNe;
//    private TextView RunNum, AppNameTx;
//    private LinearLayout StaScript, AddUsers, SecStup, ClrData, Sud15s, Sud3s, ChgRstion;
//    private AutoAdapter Adp;
//    private List<String> OpData, Probity;
//    private ArrayAdapter<String> RetAdp, OpeAdp;
//    private Toolbar Bar;
//    private String AppName = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.automation_main);
//        init();
//    }
//
//    private void init() {
//        Intent intent = getIntent();
//        AppName = intent.getStringExtra("AppName");
//        Probity = new LinkedList<>(Arrays.asList("随机", "移动", "联通", "电信"));
//        Adp = new AutoAdapter(this, new ArrayList<String>(Arrays.asList("5.55%", "6.66%", "7.77%")));
//        OpData = new LinkedList<>(Arrays.asList("10%", "20%", "25%", "30%", "45%", "50%", "55%", "65%", "75%", "90%"));
//        RetAdp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, OpData);
//        RetAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        OpeAdp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Probity);
//        OpeAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        SurvivalRate = (Spinner) findViewById(R.id.SURVIVAL_RATE);
//        Operator = (Spinner) findViewById(R.id.OPERATORS);
//        LItem = (ListView) findViewById(R.id.LIST_ITEM);
//        Stop = (Button) findViewById(R.id.STOP);
//        Loop = (Button) findViewById(R.id.LOOP);
//        Single = (Button) findViewById(R.id.SINGLE);
//        Add = (Button) findViewById(R.id.ADD);
//        Reset = (Button) findViewById(R.id.RESET);
//        Edit = (Button) findViewById(R.id.EDIT);
//        Upload = (Button) findViewById(R.id.UPLOAD);
//        Download = (Button) findViewById(R.id.DOWNLOAD);
//        Read = (Button) findViewById(R.id.READ);
//        Analysis = (Button) findViewById(R.id.ANALYSIS);
//        StartupScriptBox = (SmoothCheckBox) findViewById(R.id.STARTUP_SCRIPTBOX);
//        UserBox = (SmoothCheckBox) findViewById(R.id.ADD_USERSBOX);
//        SecStuBox = (SmoothCheckBox) findViewById(R.id.SECOND_STARTUPBOX);
//        ClrDataBox = (SmoothCheckBox) findViewById(R.id.CLEARD_DATABOX);
//        Sud15sBox = (SmoothCheckBox) findViewById(R.id.SUSPENDED_15_SBOX);
//        Sud3sBox = (SmoothCheckBox) findViewById(R.id.SUSPENDED_3_SBOX);
//        ChgRsBox = (SmoothCheckBox) findViewById(R.id.CHANGE_RESOLUTIONBOX);
//        Bar = (Toolbar) findViewById(R.id.TOOLBAR);
//        StaScript = (LinearLayout) findViewById(R.id.STARTUPSCRIPT);
//        AddUsers = (LinearLayout) findViewById(R.id.ADD_USERS);
//        SecStup = (LinearLayout) findViewById(R.id.SECOND_STARTUP);
//        ClrData = (LinearLayout) findViewById(R.id.CLEADATA);
//        Sud15s = (LinearLayout) findViewById(R.id.SUSPENDED_15_S);
//        Sud3s = (LinearLayout) findViewById(R.id.SUSPENDED_3_S);
//        ChgRstion = (LinearLayout) findViewById(R.id.CHGE_RESOLUTION);
//        NAmount = (EditText) findViewById(R.id.ADD_AMOUNT);
//        Time = (EditText) findViewById(R.id.TIME);
//        DayNum = (EditText) findViewById(R.id.DAY_NUMBER);
//        ChanNe = (EditText) findViewById(R.id.CHANNEL);
//        RunNum = (TextView) findViewById(R.id.RUN_NUMBER);
//        AppNameTx = (TextView) findViewById(R.id.APP_NAME_TX);
//        StaScript.setOnClickListener(this);
//        AddUsers.setOnClickListener(this);
//        SecStup.setOnClickListener(this);
//        ClrData.setOnClickListener(this);
//        Sud15s.setOnClickListener(this);
//        Sud3s.setOnClickListener(this);
//        ChgRstion.setOnClickListener(this);
//        Stop.setOnClickListener(this);
//        Loop.setOnClickListener(this);
//        Single.setOnClickListener(this);
//        Add.setOnClickListener(this);
//        Reset.setOnClickListener(this);
//        Edit.setOnClickListener(this);
//        Upload.setOnClickListener(this);
//        Download.setOnClickListener(this);
//        Read.setOnClickListener(this);
//        Analysis.setOnClickListener(this);
//        SurvivalRate.setOnItemSelectedListener(this);
//        Operator.setOnItemSelectedListener(this);
//        SurvivalRate.setAdapter(RetAdp);
//        Operator.setAdapter(OpeAdp);
//        LItem.setAdapter(Adp);
//        Bar.setTitle(AppName);
//        setSupportActionBar(Bar);
//        Bar.setNavigationOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            //停止
//            case R.id.STOP:
//                break;
//
//            //重置
//            case R.id.RESET:
//                break;
//
//            //循环
//            case R.id.LOOP:
//                break;
//
//            //单次
//            case R.id.SINGLE:
//                break;
//
//            //新增
//            case R.id.ADD:
//                break;
//
//            //编辑
//            case R.id.EDIT:
//                break;
//
//            //上传
//            case R.id.UPLOAD:
//                break;
//
//            //下载
//            case R.id.DOWNLOAD:
//                break;
//
//            //解析
//            case R.id.ANALYSIS:
//                break;
//
//            //读取
//            case R.id.READ:
//                break;
//
//            //启动脚本
//            case R.id.STARTUPSCRIPT:
//                isChecked(StartupScriptBox);
//                break;
//
//            //新增用户
//            case R.id.ADD_USERS:
//                isChecked(UserBox);
//                break;
//
//            //二次启动
//            case R.id.SECOND_STARTUP:
//                isChecked(SecStuBox);
//                break;
//
//            //清除数据
//            case R.id.CLEADATA:
//                isChecked(ClrDataBox);
//                break;
//            //15S暂停
//            case R.id.SUSPENDED_15_S:
//                isChecked(Sud15sBox);
//                break;
//            case R.id.SUSPENDED_3_S:
//                isChecked(Sud3sBox);
//                break;
//
//            //更改分辨率
//            case R.id.CHGE_RESOLUTION:
//                isChecked(ChgRsBox);
//                break;
//        }
//    }
//
//    private void isChecked(SmoothCheckBox box) {
//        if (box.isChecked()) {
//            box.setChecked(false);
//        } else {
//            box.setChecked(true);
//        }
//
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //录制脚本
//        if (R.id.RECORDING == id) {
//            Toast.makeText(AutomationActivity.this, "111", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        //脚本留存
//        if (R.id.RETAINED == id) {
//            return true;
//        }
//        //参数设置
//        if (R.id.INFO_SETING == id) {
//            return true;
//        }
//        //保存本页参数
//        if (R.id.SAVE_PAGER_INFO == id) {
//            return true;
//        }
//        //关闭屏幕
//        if (R.id.CLOSE_SCREEN == id) {
//            return true;
//        }
//        //上传脚本
//        if (R.id.UPLOAD == id) {
//            return true;
//        }
//        //下载脚本
//        if (R.id.DOWNLOAD == id) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//}
