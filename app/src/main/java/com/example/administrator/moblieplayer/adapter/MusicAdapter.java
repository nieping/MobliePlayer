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
import com.example.administrator.moblieplayer.view.activity.LocalMusicPlayActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/16.
 */

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MediaBaen> musicBaens = new ArrayList();
    private MediaBaen musicBaen;

    private String TAG = MusicAdapter.class.getSimpleName();
    private int mark;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    public MusicAdapter(Context context, ArrayList<MediaBaen> musicBaens) {
        this.context = context;
        this.musicBaens = musicBaens;
    }


    public void notifyDataSetChanged(ArrayList<MediaBaen> list) {
       notifyDataSetChanged();
        this.musicBaens = list;
    }

    @Override
    public int getCount() {
        return musicBaens.size();
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
        mark=i;
        ViewHodler hodler = null;
        if (view == null) {
           hodler = new ViewHodler();
            view = View.inflate(context, R.layout.list_title, null);
            hodler.imageView = view.findViewById(R.id.iv_icon);
            hodler.name = view.findViewById(R.id.tv_name);
            hodler.size = view.findViewById(R.id.tv_size);
            hodler.time = view.findViewById(R.id.tv_duration);
            hodler.artst = view.findViewById(R.id.tv_artst);
            view.setTag(hodler);

        } else {
            hodler = (ViewHodler) view.getTag();
        }
        musicBaen = (MediaBaen) musicBaens.get(i);
        hodler.name.setText(musicBaen.getName());
        hodler.time.setText(time.format(musicBaen.getDuration()));
        hodler.size.setText(Utli.formatter(context,musicBaen.getSize()));
        hodler.artst.setText(musicBaen.getArtist());
        hodler.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.music_default_bg));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, LocalMusicPlayActivity.class);
                intent.putExtra("musicList", musicBaens);
                intent.putExtra("mark",mark);
                context.startActivity(intent);
            }
        });
        return view;
    }


    class ViewHodler {
        private ImageView imageView;
        private TextView name;
        private TextView time;
        private TextView size;
        private TextView artst;
    }
}
