package com.yoham.storieskids.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.PuffInAnimation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.yoham.storieskids.BuildConfig;
import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.about.AboutDialog;
import com.yoham.storieskids.ui.base.BaseActivity;
import com.yoham.storieskids.ui.categories.CategoriesActivity;
import com.yoham.storieskids.ui.contact.ReportContactDialog;
import com.yoham.storieskids.ui.rate.RateUsDialog;
import com.yoham.storieskids.ui.storiesList.StoriesListActivity;
import com.yoham.storieskids.utils.AppConstants;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.yoham.storieskids.utils.AppUtils.openPlayStoreForRatingApp;
import static com.yoham.storieskids.utils.AppUtils.openShareDialog;

public class HomeActivity extends BaseActivity implements IHomeView {

    private static final String TAG = HomeActivity.class.getSimpleName();

    //  region Local variables

    private Toolbar mToolbar;

    private HomePresenter<IHomeView> mPresenter;

    private Handler mHandler;

    // endregion

    //region Binding views

    @BindView(R.id.button_read)
    AppCompatImageButton mBtnRead;

    @BindView(R.id.button_share)
    AppCompatImageButton mBtnShare;

    @BindView(R.id.button_rate)
    AppCompatImageButton mBtnRate;

    @BindView(R.id.home_app_version)
    AppCompatTextView mAppVersion;

    //endregion

    @NotNull
    @Contract("_ -> new")
    public static Intent getStartIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setUnBinder(ButterKnife.bind(this));
        mPresenter = new HomePresenter<>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());
        mPresenter.onAttach(this);
        mHandler = new Handler();
        setUp();
        startMainAnimation();
    }

    private void startMainAnimation() {
        mHandler.postDelayed(() -> mPresenter.onShowReadButtonAnimation(), 1000);
        mHandler.postDelayed(() -> mPresenter.onShowRateButtonAnimation(), 1500);
        mHandler.postDelayed(() -> {
            mPresenter.onShowShareButtonAnimation();
            mPresenter.onSetAllVisible();
        }, 2200);

    }

    @Override
    public void setAllButtonsVisible() {
        mBtnRead.setVisibility(View.VISIBLE);
        mBtnShare.setVisibility(View.VISIBLE);
        mBtnRate.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    public void setUp() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        this.updateVersion();
    }

    @Override
    public void setUpAds() {

    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favoris_menu_item : {
                mPresenter.onOpenFavoritesActivity();
                return true;
            }

            case R.id.contact_report_menu_item : {
                mPresenter.onOpenReportContactDialog();
                return true;
            }

            case R.id.about_menu_item : {
                mPresenter.onOpenAboutUsDialog();
                return true;
            }

            default: return super.onOptionsItemSelected(item);
        }
    }

    // region Buttons click
    @OnClick(R.id.button_read)
    public void ReadButtonOnClick() {
        mPresenter.onOpenCategoriesActivity();
    }

    @OnClick(R.id.button_share)
    public void ShareButtonOnClick() {
        mPresenter.onOpenShareDialog();
    }

    @OnClick(R.id.button_rate)
    public void RateButtonsOnclick() {
        mPresenter.onOpenRateUsDialog();
    }
    //endregion


    @Override
    public void updateVersion() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mAppVersion.setText(version);
    }

    @Override
    public void openCategoriesActivity() {
        startActivity(CategoriesActivity.getStartIntent(this));
    }

    @Override
    public void showShareDialog() {
        openShareDialog(HomeActivity.this);
    }

    @Override
    public void openRateUsDialog() {
        openPlayStoreForRatingApp(this);
    }

    @Override
    public void openFavoritesActivity() {
        startActivity(StoriesListActivity.getStartIntent(this, AppConstants.CATEGORY_FAVOURITES_KEY, R.string.favorites_list));
    }

    @Override
    public void openContactAndReportDialog() {
        ReportContactDialog.newInstance().show(getSupportFragmentManager(), ReportContactDialog.TAG);
    }

    @Override
    public void openAboutDialog() {
        AboutDialog.newInstance().show(getSupportFragmentManager(), AboutDialog.TAG);
    }

    @Override
    public void showReadButtonsAnimation() {
        new SlideInAnimation(mBtnRead)
                .setDirection(Animation.DIRECTION_UP)
                .animate();
    }

    @Override
    public void showShareButtonsAnimation() {
        new PuffInAnimation(mBtnShare).animate();
    }

    @Override
    public void showRateButtonsAnimation() {
        new SlideInAnimation(mBtnRate)
                .setDirection(Animation.DIRECTION_DOWN)
                .animate();
    }

    @Override
    public void showRatingDialog() {
        RateUsDialog.newInstance().show(getSupportFragmentManager(), RateUsDialog.TAG);
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "Back pressed " + AppConstants.SHOULD_START_RATE_US);
        if (AppConstants.SHOULD_START_RATE_US) {
            mPresenter.onExit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mPresenter.onDetach();
        super.onDestroy();
    }
}
