package com.example.administrator.moblieplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.MediaBaen;
import com.example.administrator.moblieplayer.utli.Utli;
import com.example.administrator.moblieplayer.view.activity.VideoPlayViewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/16.
 */

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MediaBaen> videoList = new ArrayList();
    private int mark = 0;
    private String TAG = VideoAdapter.class.getSimpleName();
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");

    public VideoAdapter(Context context, ArrayList<MediaBaen> videoList) {
        this.context = context;
        this.videoList = videoList;
    }


    public void notifyDataSetChanged(ArrayList<MediaBaen> list) {
        notifyDataSetChanged();
        videoList = list;

    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        mark = i;
        ViewHodler hodler = null;
        if (view == null) {
            hodler = new ViewHodler();
            view = View.inflate(context, R.layout.list_title, null);
            hodler.imageView = view.findViewById(R.id.iv_icon);
            hodler.name = view.findViewById(R.id.tv_name);
            hodler.size = view.findViewById(R.id.tv_size);
            hodler.time = view.findViewById(R.id.tv_duration);
            view.setTag(hodler);

        } else {
            hodler = (ViewHodler) view.getTag();
        }

        if (!videoList.isEmpty()) {
            final MediaBaen baen = videoList.get(i);
            hodler.name.setText(baen.getName());
             hodler.size.setText(Utli.formatter(context,baen.getSize()));
            hodler.time.setText(time.format(baen.getDuration()));
            hodler.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.video_default_icon));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, VideoPlayViewActivity.class);
                    intent.putExtra("videoList", videoList);
                    intent.putExtra("mark", i);
                    context.startActivity(intent);

                }
            });
        }

        return view;
    }

    class ViewHodler {
        private ImageView imageView;
        private TextView name;
        private TextView time;
        private TextView size;
    }
}
