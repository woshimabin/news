package com.itemp.news.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.itemp.news.Been.NewsBeen;
import com.itemp.news.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/11/7.
 */
public class testAdapter extends RecyclerView.Adapter<testAdapter.Holder>{
    private static final String TAG = "test";
    private final LayoutInflater inflater;
    private List<NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = null;
    private Context context;
    private NewsBeen newsBeen=null;
    private String imageUrl;

    public testAdapter(Context context, List<NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
        this.context = context;
        this.contentlist = contentlist;
        inflater = LayoutInflater.from(context);
    }

    public void setContentlist(List<NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
        this.contentlist = contentlist;
    }

    private View view;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.item_rvadapter_linear, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final int layoutPosition = holder.getLayoutPosition();

        if (contentlist.size()>0) {
            NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = contentlist.get(position);
            String pubDate = contentlistBean.getPubDate();
            String title = contentlistBean.getTitle();
            String channelName = contentlistBean.getChannelName();
            List<NewsBeen.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageurlsBean> imageurls = contentlistBean.getImageurls();

            if(imageurls.size() > 0){
                imageUrl = imageurls.get(0).getUrl();
            }else{
                imageUrl=null;
            }

            Log.e(TAG, "contentlist: " + pubDate + title + channelName + imageurls + imageUrl);

            holder.tvNewsTitle.setText(title);
            if(imageUrl!=null){
                holder.sdvCover.setImageURI(Uri.parse(imageUrl));
            }else {
                holder.sdvCover.setImageResource(R.mipmap.loding_news);
            }
            holder.tvChannelName.setText(channelName);
            holder.tvPubDate.setText(pubDate);


            holder.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //把监听业务甩给外界实现
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(layoutPosition);
                    }
                }
            });


        }else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return contentlist.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.sdvCover)
        SimpleDraweeView sdvCover;
        @BindView(R.id.tvNewsTitle)
        TextView tvNewsTitle;
        @BindView(R.id.tvChannelName)
        TextView tvChannelName;
        @BindView(R.id.tvPubDate)
        TextView tvPubDate;
        @BindView(R.id.llRoot)
        LinearLayout llRoot;
        @BindView(R.id.cvRoot)
        CardView cvRoot;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
