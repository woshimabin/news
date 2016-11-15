package com.itemp.news.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itemp.news.Been.easyBean;
import com.itemp.news.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/11/7.
 */
public class EasyAdapter extends BaseAdapter {

    Context context;
    private List<easyBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = new ArrayList<>();
    private final LayoutInflater inflater;
    private Holder holder;

    public EasyAdapter(Context context, List<easyBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
        this.context = context;
        this.contentlist = contentlist;
        inflater = LayoutInflater.from(context);
    }

    public void setContentlist(List<easyBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
        this.contentlist = contentlist;
    }

    @Override
    public int getCount() {
        return contentlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_easy, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder= ((Holder) convertView.getTag());
        }
        String text = contentlist.get(position).getText();
        String time = contentlist.get(position).getCreate_time();
        holder.tvPubDate.setText(time);
        holder.tvNewsTitle.setText(text);


        return convertView;
    }

    static class Holder {
        @BindView(R.id.tvNewsTitle)
        TextView tvNewsTitle;
        @BindView(R.id.tvPubDate)
        TextView tvPubDate;
        @BindView(R.id.llRoot)
        LinearLayout llRoot;
        @BindView(R.id.cvRoot)
        CardView cvRoot;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
