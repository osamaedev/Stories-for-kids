package com.yoham.storieskids.ui.splash;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.PuffInAnimation;
import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseActivity;
import com.yoham.storieskids.ui.home.HomeActivity;
import com.yoham.storieskids.utils.AppConstants;

public class SplashActivity extends BaseActivity implements ISplashView {

    private SplashPresenter<ISplashView> mPresenter;

    private Handler mHandler;

    // region Bind View

    @BindView(R.id.img_splash)
    ImageView mImgSplash;

    @BindView(R.id.splash_title)
    View mAppTitle;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUnBinder(ButterKnife.bind(this));

        mPresenter = new SplashPresenter<>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());

        mHandler = new Handler();

        mPresenter.onAttach(this);

        setUpDatabase();

        setUp();

        mPresenter.onStartSplashAnimation();

        mPresenter.onStartHomeActivity();

    }


    @Override
    public void setUp() {
        mPresenter.onShowRateUsDecision();
    }

    @Override
    public void setUpAds() {

    }


    private void startHomeActivity() {
        this.startActivity(HomeActivity.getStartIntent(SplashActivity.this));
        this.finish();
    }
    @Override
    public void showSplashAnimation() {
        mHandler.postDelayed(() -> new PuffInAnimation(mImgSplash).animate(), 1000);
        mHandler.postDelayed(() -> new FadeInAnimation(mAppTitle).animate(), 1500);
    }

    @Override
    public void openHomeActivity() {
        mHandler.postDelayed(this::startHomeActivity, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!this.isFinishing()) {
            mHandler.removeCallbacksAndMessages(null);
            getIntent().putExtra(AppConstants.SPLASH_ACTIVITY_PAUSED, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras() != null
                && getIntent().getExtras().getBoolean(AppConstants.SPLASH_ACTIVITY_PAUSED)) {
            startHomeActivity();
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mPresenter.onDetach();
        super.onDestroy();
    }
}
