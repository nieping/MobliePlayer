package com.example.administrator.moblieplayer.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.view.base.BaseActivity;
import com.example.administrator.moblieplayer.view.fragment.LocalMusicFragment;
import com.example.administrator.moblieplayer.view.fragment.LocalVideoFragment;
import com.example.administrator.moblieplayer.view.fragment.NetMusicFragment;
import com.example.administrator.moblieplayer.view.fragment.NetVideoFragment;
import com.example.administrator.moblieplayer.view.ui.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 导航页面
 */
public class NavigationActivity extends BaseActivity implements TitleBar.TitleOnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.rp_radio)
    RadioGroup radioGroup;
    @BindView(R.id.rbt_localmusic)
    RadioButton rbtLocalMusic;
    @BindView(R.id.rbt_netmusic)
    RadioButton rbtNetMusic;
    @BindView(R.id.rbt_localvideo)
    RadioButton rbtLocalVideo;
    @BindView(R.id.rbt_netvideo)
    RadioButton rbtnetVideo;


    private String TAG = NavigationActivity.class.getSimpleName();
    private Context mContext;
    private Fragment mFragment;
    private LocalMusicFragment localMusicFragment;
    private NetMusicFragment netMusicFragment;
    private LocalVideoFragment localVideoFragment;
    private NetVideoFragment netVideoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

    }

    private void initView() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        localVideoFragment = new LocalVideoFragment();
        transaction.add(R.id.fl_content, localVideoFragment);
        transaction.commit();
        Log.e(TAG, "initView: " + radioGroup.getChildCount());



        mFragment = localVideoFragment;
        setFragment(radioGroup.getChildAt(0).getId());
        radioGroup.setOnCheckedChangeListener(this);
        titleBar.setTitleOnClickListener(this);
    }


    @Override
    public void onItemLishiClick() {
        Toast.makeText(this, "onItemLishiClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTiemSousuoClick() {
        Toast.makeText(this, "onTiemSousuoClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTiemGameClick() {
        Toast.makeText(this, "onTiemGameClick", Toast.LENGTH_SHORT).show();
    }

    /**
     * Radio Group的点击事件
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        setFragment(checkedId);
    }

    /**
     * 设置fragment
     */

    private void setFragment(int checkedId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        for (int i = 0; i <= radioGroup.getChildCount() - 1; i++) {
            if (checkedId == radioGroup.getChildAt(i).getId()) {
                ((RadioButton) radioGroup.getChildAt(i)).setTextColor(getResources().getColor(R.color.blue));
            } else {
                ((RadioButton) radioGroup.getChildAt(i)).setTextColor(getResources().getColor(R.color.green));
            }
        }
        if (mFragment != null) {
            transaction.hide(mFragment);
        }
        switch (checkedId) {
            case R.id.rbt_localmusic:
                localMusicFragment = new LocalMusicFragment();
                transaction.replace(R.id.fl_content, localMusicFragment);
                mFragment = localMusicFragment;

                break;
            case R.id.rbt_localvideo:
                localVideoFragment = new LocalVideoFragment();
                transaction.replace(R.id.fl_content, localVideoFragment);
                mFragment = localVideoFragment;
                break;
            case R.id.rbt_netvideo:
                netVideoFragment = new NetVideoFragment();
                transaction.replace(R.id.fl_content, netVideoFragment);
                mFragment = netVideoFragment;
                break;
            case R.id.rbt_netmusic:
                netMusicFragment = new NetMusicFragment();
                transaction.replace(R.id.fl_content, netMusicFragment);
                mFragment = netMusicFragment;
                break;


        }
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
}
