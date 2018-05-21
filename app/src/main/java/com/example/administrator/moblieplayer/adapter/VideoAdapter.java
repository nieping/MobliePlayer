package com.example.administrator.moblieplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.VideoBaen;
import com.example.administrator.moblieplayer.utli.Utli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private List videoList = new ArrayList();
    private VideoBaen videoBaen;

    private String TAG = VideoAdapter.class.getSimpleName();

    public VideoAdapter(Context context, List videoList) {
        this.context = context;
        this.videoList = videoList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler = null;
        Log.e(TAG, "===============getView: " );
        if (view == null) {
            ViewHodler viewHodler = new ViewHodler();
            view = View.inflate(context, R.layout.list_title, null);
            hodler.imageView = view.findViewById(R.id.iv_icon);
            hodler.name = view.findViewById(R.id.tv_name);
            hodler.size = view.findViewById(R.id.tv_size);
            hodler.time = view.findViewById(R.id.tv_duration);
            view.setTag(viewHodler);

        } else {
            hodler = (ViewHodler) view.getTag();
        }
        videoBaen = (VideoBaen) videoList.get(i);
        hodler.name.setText(videoBaen.getName());
        hodler.time.setText(videoBaen.getDuration());
        hodler.size.setText(Utli.formatter(context,videoBaen.getSize()));
        hodler.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.bg_item));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "电科", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHodler {
        private ImageView imageView;
        private TextView name;
        private TextView time;
        private TextView size;
    }
}
