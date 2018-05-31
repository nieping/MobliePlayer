package com.example.administrator.moblieplayer.view.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {
    @BindView(R.id.et_titlebar_sousuokuang)
    TextView titleBarSousuoKuang;
    @BindView(R.id.view_title_point)
    View tieleBarPoint;
    @BindView(R.id.iv_title_lishi)
    ImageView titleBarLishi;
    @BindView(R.id.rl_titlebar_game)
    RelativeLayout titleBarGame;

    private TitleOnClickListener titleOnClickListener;
    private String TAG = TitleBar.class.getSimpleName();


    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar_layout,this, true);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        titleBarSousuoKuang.setOnClickListener(this);
        titleBarGame.setOnClickListener(this);
        titleBarLishi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (titleOnClickListener != null) {
            switch (v.getId()) {
                case R.id.et_titlebar_sousuokuang:
                    titleOnClickListener.onTiemSousuoClick();
                    break;
                case R.id.iv_title_lishi:
                    titleOnClickListener.onItemLishiClick();
                    break;
                case R.id.rl_titlebar_game:
                    titleOnClickListener.onTiemGameClick();
                    break;
            }
        }

    }


    public interface TitleOnClickListener {
        void onItemLishiClick();
        void onTiemSousuoClick();
        void onTiemGameClick();
    }
    public TitleOnClickListener getTitleOnClickListener() {
        return titleOnClickListener;
    }

    public void setTitleOnClickListener(TitleOnClickListener titleOnClickListener) {
        this.titleOnClickListener = titleOnClickListener;
    }

}
