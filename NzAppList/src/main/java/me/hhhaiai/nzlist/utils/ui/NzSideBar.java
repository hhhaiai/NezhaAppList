package me.hhhaiai.nzlist.utils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import me.hhhaiai.nzlist.R;
import me.hhhaiai.nzlist.utils.NzAppLog;


public class NzSideBar extends View {
    // 26个字母
    public static String[] baseTexts = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    /**
     * small -->large之间的判断依据
     */
    private static int SMALLINCH = 3;
    private static double SMALLINCHES = 3.4;
    private static double NORMALINCH = 4.2;
    private static double NORMALINCHES = 4.7;
    private static double LARGEINCH = 7.1;
    private WindowManager winmManager;
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private int choose = -1;// 选中
    private Paint paint = new Paint();
    private int textSize = 0;
    private TextView mTextDialog;

    public NzSideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NzSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        winmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        textSize = getScreenSizeOfDevice();
    }

    public NzSideBar(Context context) {
        super(context);
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        NzAppLog.i("onDraw");
        // 获取焦点改变背景颜色.
        // 获取对应高度
        int height = getHeight();
        // 获取对应宽度
        int width = getWidth();
        // 获取每一个字母的高度
        int singleHeight = height / baseTexts.length;
        for (int i = 0; i < baseTexts.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            /*
             * 此处设置右侧字母列表的字体大小
             */
            paint.setTextSize(textSize);
            // 选中的状态
            if (i == choose) {
//                paint.setColor(Color.parseColor("#3399ff"));
                paint.setColor(Color.parseColor("#FDFEFE"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(baseTexts[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(baseTexts[i], xPos, yPos, paint);
            // 重置画笔
            paint.reset();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        NzAppLog.i("dispatchTouchEvent event:" + event.toString());
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        final int c = (int) (y / getHeight() * baseTexts.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
//                setBackgroundResource(R.drawable.sidebar_background);
                setBackgroundColor(Color.parseColor("#8C3498DB"));
                if (oldChoose != c) {
                    if (c >= 0 && c < baseTexts.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(baseTexts[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(baseTexts[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * @return int
     * 获取inches值
     */
    private int getScreenSizeOfDevice() {
        Point point = new Point();
//        winmManager.getDefaultDisplay().getRealSize(point);
        winmManager.getDefaultDisplay().getSize(point);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        if (screenInches < SMALLINCHES) {
            return 10;
        }
        if (screenInches > SMALLINCH && screenInches < NORMALINCHES) {
            return 20;
        }
        if (screenInches > NORMALINCH && screenInches < LARGEINCH) {
            return 30;
        }
        if (screenInches > LARGEINCH) {
            return 35;

        }
        return 15;
    }

    /**
     * 接口
     *
     * @author coder
     */

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }


}