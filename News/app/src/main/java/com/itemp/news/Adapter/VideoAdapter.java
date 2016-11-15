package com.itemp.news.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itemp.news.Been.VideoBean;
import com.itemp.news.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/11/9.
 */
public class VideoAdapter extends BaseAdapter {

    private static final String TAG = "VideoAdapter";
    Context context;

    private List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = new ArrayList<>();
    private final LayoutInflater inflater;
    private Holder holder;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int currentPlayingPosition = -1;

    public VideoAdapter(Context context, List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (contentlist != null) {
            this.contentlist = contentlist;
        }
    }

    public void setContentlist(List<VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_video, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((Holder) convertView.getTag());
        }

        Log.e(TAG, "getView: contentlist" + contentlist);
        VideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = contentlist.get(position);
        String title = contentlistBean.getText();
        String time = contentlistBean.getCreate_time();
        final String videoUri = contentlistBean.getVideo_uri();
        Log.e(TAG, "getView: videoUri" + videoUri);
        holder.tvNewsTitle.setText(title);
        holder.tvPubDate.setText(time);

        holder.tvplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayingPosition = position;
                notifyDataSetChanged();

            }
        });

        holder.sfv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });

        if (currentPlayingPosition == position) {
            holder.tvplay.setVisibility(View.INVISIBLE);
            holder.sfv.setVisibility(View.VISIBLE);
            PlayVideo(videoUri,holder);
            if (!mediaPlayer.isPlaying()) {
                holder.sfv.setAlpha(0.f);
            }
        } else {
            holder.tvplay.setVisibility(View.VISIBLE);
            holder.sfv.setVisibility(View.INVISIBLE);
        }

        Integer formerPosition = (Integer) holder.sfv.getTag();

        if (position != currentPlayingPosition && formerPosition != null && formerPosition == currentPlayingPosition) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            currentPlayingPosition = -1;
            holder.progressbar.setVisibility(View.INVISIBLE);
        }

        holder.sfv.setTag(position);

        return convertView;
    }

    private void PlayVideo(String videoUri,final Holder holder) {
        holder.progressbar.setVisibility(View.VISIBLE);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(videoUri));
            mediaPlayer.setDisplay(holder.sfv.getHolder());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    holder.progressbar.setVisibility(View.INVISIBLE);
                    mediaPlayer.start();
                    holder.sfv.setAlpha(1.f);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Holder {
        @BindView(R.id.tvNewsTitle)
        TextView tvNewsTitle;
        @BindView(R.id.tvPubDate)
        TextView tvPubDate;
        @BindView(R.id.sfvImage)
        ImageView sfvImage;
        @BindView(R.id.sfv)
        SurfaceView sfv;
        @BindView(R.id.tvplay)
        TextView tvplay;
        @BindView(R.id.progressbar)
        ProgressBar progressbar;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
