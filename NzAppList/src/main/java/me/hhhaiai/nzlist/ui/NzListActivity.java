package me.hhhaiai.nzlist.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import me.hhhaiai.nzlist.R;
import me.hhhaiai.nzlist.interfaces.IProcesBase;
import me.hhhaiai.nzlist.memory.ProcessHolder;
import me.hhhaiai.nzlist.model.AppModel;
import me.hhhaiai.nzlist.utils.AppTypeStyle;
import me.hhhaiai.nzlist.utils.CharacterParser;
import me.hhhaiai.nzlist.utils.NzAppLog;
import me.hhhaiai.nzlist.utils.PinyinComparator;
import me.hhhaiai.nzlist.utils.ui.NzSearchEditText;
import me.hhhaiai.nzlist.utils.ui.NzSideBar;

/**
 * @Copyright © 2016 sanbo Inc. All rights reserved.
 * @Description: 应用列表
 * @Version: 1.0
 * @Create: 2016年5月15日 上午2:17:26
 * @Author: sanbo
 */
public class NzListActivity extends Activity {
    public static final String KEY_DATA_PROCESS = "kdp";
    public static final String SHOW_SYSTEM = "SHOW_SYSTEM";
    public static final int ESHOWSTATUS_SHOW_USERAPPS = 0;
    public static final int ESHOWSTATUS_SHOW_SYSTEM = 1;
    public static final int ESHOWSTATUS_SHOW_SYSTEM_HAS_LUNCH = 2;


    private Context mContext;
    private ListView sortListView;
    private NzSideBar sideBar;
    private TextView dialog;
    private NzAdapter adapter;
    private NzSearchEditText mClearEditText;
    private ProgressDialog proDialog;
    private int Width, Heigt;
    // 显示系统app状态
    private int Status_showSystemApp = -1;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<AppModel> appNameList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    /**
     * 刷新列表线程
     */
    @SuppressWarnings("unused")
    private Thread mFlushThread = new Thread(new ShowappHandler());
    // 更新UI
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initUI();

            // 根据a-z进行排序源数据
            Collections.sort(appNameList, pinyinComparator);
            adapter = new NzAdapter(NzListActivity.this, appNameList);
            sortListView.setAdapter(adapter);

            // 设置监听
            setListener();
            if (proDialog != null) {
                proDialog.dismiss();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.sortlist);
            parserIntent(getIntent());
            initSize();
            refInstallList();
        } catch (Throwable e) {
            NzAppLog.e(e);
        }
    }


    private void parserIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.hasExtra(SHOW_SYSTEM)) {
            Status_showSystemApp = intent.getIntExtra(SHOW_SYSTEM, -1);
        }
    }

    public void onClick(View view) {
//        if (R.id.ivSetting == view.getId()) {
//            Toast.makeText(this, "即将支持显示三方app或者所有app", Toast.LENGTH_SHORT).show();
//        }

    }

    private void refInstallList() {
        this.proDialog = ProgressDialog.show(this, "Searching..", "searching..wait....", true, true);
        new Thread(new ShowappHandler()).start();
    }

    private void initSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Heigt = size.y;
        Width = size.x;
    }

    private void initUI() {
        sideBar = (NzSideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        // listView
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        // 检索框
        mClearEditText = (NzSearchEditText) findViewById(R.id.filter_edit);
    }

    /**
     * 监听器设置
     */
    private void setListener() {
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new NzSideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // XxXGlobalContext.choosedModel = (SortModel) adapter.getItem(position);
                // setResult(RESULT_OK);
                //  finish();
            }
        });
        sortListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                processLongClick(position);
                return true;
            }
        });

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 处理某些按钮长按操作
     *
     * @param position
     */
    private void processLongClick(int position) {

        final String[] items = ProcessHolder.getInstance().getPorceserNames();
        Dialog alertDialog = new AlertDialog.Builder(NzListActivity.this).setTitle("操作列表")
                .setIcon(android.R.drawable.btn_star)
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppModel model = (AppModel) adapter.getItem(position);
                        String pkgName = model.getAppPackageName();
//                        NzAppLog.i("OnItemLongClickListener: " + pkgName + "------" + items.length + "----" + which);
                        if (which < items.length) {
                            String name = items[which];
                            final IProcesBase pb = ProcessHolder.getInstance().getPorceserByName(name);
//                            NzAppLog.i("pb: " + pb);

                            if (pb != null) {
                                new Thread(() -> {
                                    pb.work(mContext, pkgName);
                                }).start();
                            }
                        }
                    }
                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
                .create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(Width - (Width / 8), Heigt - (Heigt / 5));
    }

    /**
     * <pre>
     * 为ListView填充数据
     *      1.查看app列表
     *      2.列表组成list列表
     * </pre>
     *
     * @return
     */
    private List<AppModel> filledData() {

        // 1.
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        List<AppModel> mSortList = new ArrayList<AppModel>();
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            AppModel sortModel = new AppModel();
            sortModel.setName(appName);
            sortModel.setAppVersionName(packageInfo.versionName);
            sortModel.setAppPackageName(packageInfo.packageName);
            sortModel.setAppLaunchActivity(getLaunchActivityName(packageInfo.packageName));
            sortModel.setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));

            if (AppTypeStyle.isSystemApp(packageInfo) || AppTypeStyle.isSystemUpdateApp(packageInfo)) {
                sortModel.setType(AppModel.Etype.APP_SYSTEM);
            } else {
                sortModel.setType(AppModel.Etype.APP_USER);
            }

            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(appName);
            String sortString = pinyin.substring(0, 1).toUpperCase(Locale.getDefault());

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase(Locale.getDefault()));
            } else {
                sortModel.setSortLetters("#");
            }
//            // Log.d("sanbo", sortModel.toString());
//            // mSortList.add(sortModel);
//            // 非系统软件/非本身软件/非xposed即展示
//            if (AppModel.Etype.APP_SYSTEM != sortModel.getType() && !"com.xxx".equals(sortModel.getAppPackageName())
//                    && !"de.robv.android.xposed.installer".equals(sortModel.getAppPackageName())) {
//                mSortList.add(sortModel);
//            }


            if (Status_showSystemApp == ESHOWSTATUS_SHOW_USERAPPS) {
                if (AppModel.Etype.APP_SYSTEM != sortModel.getType()) {
                    mSortList.add(sortModel);
                }
            } else if (Status_showSystemApp == ESHOWSTATUS_SHOW_SYSTEM) {
                mSortList.add(sortModel);
            } else if (Status_showSystemApp == ESHOWSTATUS_SHOW_SYSTEM_HAS_LUNCH) {
                if (!TextUtils.isEmpty(sortModel.getAppLaunchActivity())) {
                    mSortList.add(sortModel);
                }
            }

        }
        return mSortList;

    }

    /**
     * 获取指定包中主Activity的类名，并不是所有包都有主Activity
     **/
    private String getLaunchActivityName(String packageName) {
        PackageManager pManager = this.getPackageManager();
        // 根据PackageInfo对象取不出其中的主Activity，须用Intent
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(packageName);
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(intent, 0);
        String mainActivityName = "";
        if (resolveInfos != null && resolveInfos.size() >= 1) {
            mainActivityName = resolveInfos.get(0).activityInfo.name;
        }
        return mainActivityName;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<AppModel> filterDateList = new ArrayList<AppModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = appNameList;
        } else {
            filterDateList.clear();
            for (AppModel sortModel : appNameList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    class ShowappHandler implements Runnable {
        @Override
        public void run() {
            // 实例化汉字转拼音类
            characterParser = CharacterParser.getInstance();
            pinyinComparator = new PinyinComparator();
            // 填充数据
            appNameList = filledData();

            Message localMessage = new Message();
            localMessage.what = 1;
            mHandler.sendMessage(localMessage);
        }
    }


}
