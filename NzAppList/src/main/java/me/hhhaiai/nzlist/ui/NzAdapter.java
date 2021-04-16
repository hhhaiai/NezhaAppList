package me.hhhaiai.nzlist.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import me.hhhaiai.nzlist.R;
import me.hhhaiai.nzlist.model.AppModel;

import java.util.List;
import java.util.Locale;

public class NzAdapter extends BaseAdapter implements SectionIndexer {
    private List<AppModel> list = null;
    private Context mContext;

    public NzAdapter(Context mContext, List<AppModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<AppModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressLint("InflateParams")
    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final AppModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.sortlist_item, null);
            viewHolder.appName = (TextView) view.findViewById(R.id.appName);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.appPackage = (TextView) view.findViewById(R.id.appPackage);
            viewHolder.appIcon = (TextView) view.findViewById(R.id.appIcon);
            viewHolder.appVersionCode = (TextView) view.findViewById(R.id.appVersionCode);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.appName.setText(this.list.get(position).getName());
        viewHolder.appVersionCode.setText(this.list.get(position).getAppVersionName());
        viewHolder.appPackage.setText(this.list.get(position).getAppPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            viewHolder.appIcon.setBackground(this.list.get(position).getIcon());
        }

        return view;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase(Locale.getDefault()).charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase(Locale.getDefault());
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    final static class ViewHolder {
        TextView tvLetter;// app名字字母
        TextView appName;// app名字
        TextView appPackage;// app包名
        TextView appVersionCode;// app版本号
        TextView appIcon;// app icon
    }
}