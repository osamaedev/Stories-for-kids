package com.yoham.storieskids.ui.reading;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseActivity;
import com.yoham.storieskids.ui.home.HomeActivity;
import com.yoham.storieskids.utils.AppConstants;
import com.yoham.storieskids.utils.TextViewEx;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadingActivity extends BaseActivity implements IReadingView {

    private static final String TAG = "ReadingActivity";

    //region local variables

    private ReadingPresenter<IReadingView> mPresenter;

    private Toolbar mToolbar;

    private InterstitialAd mInterstitial;

    private Handler mHandler;

    private NewReadingPresenter<IReadingView> newReadingPresenter;

    //endregion

    //region binding views

    @BindView(R.id.story_image)
    AppCompatImageView mImageStory;

    @BindView(R.id.story_title)
    AppCompatTextView mStoryTitle;

    @BindView(R.id.toolbar_title)
    AppCompatTextView mToolbarStoryTitle;

    @BindView(R.id.story_body)
    TextViewEx mStoryBody;

    @BindView(R.id.adMobView)
    View mAdContainer;

    MenuItem favouriteItem;

    //endregion

    public static Intent getStartIntent(Context context, int storyId) {
        Intent intent = new Intent(context, ReadingActivity.class);
        intent.putExtra(AppConstants.STORY_ID_KEY, storyId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        mToolbar = findViewById(R.id.toolbar);
        setUnBinder(ButterKnife.bind(this));

        mPresenter = new ReadingPresenter<>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());

        mPresenter.onAttach(this);

        mPresenter.onLoadStoryById(getIntent().getExtras().getInt(AppConstants.STORY_ID_KEY));

        mHandler = new Handler();


        newReadingPresenter = new NewReadingPresenter<>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());
        newReadingPresenter.onAttach(this);
        newReadingPresenter.loadStoryById(getIntent().getExtras().getInt(AppConstants.STORY_ID_KEY));

        setUp();

        setUpAds();
    }


    @Override
    public void setUp() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        }
    }

    @Override
    public void setUpAds() {
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getDecryptedValue(AppConstants.BANNER_2));
        ((RelativeLayout) mAdContainer).addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("998E7426AF3880E009A64950CAD6CE68").build();
        mAdView.loadAd(adRequest);

        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(getDecryptedValue(AppConstants.INTERSTITIAL_2));
        mHandler.postDelayed(() -> mInterstitial.loadAd(adRequest), 10000);
    }


    @Override
    public void showInterstitialAd() {
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }
    }

    @Override
    public void setEnableFavouriteIcon(boolean state) {
        if (this.favouriteItem != null) {
            if (state) {
                this.favouriteItem.setIcon(R.drawable.ic_favorite_normal);
            } else {
                this.favouriteItem.setIcon(R.drawable.ic_favorite);
            }
        } else {
            mPresenter.onLoadIconFavorite();
        }
    }

    @Override
    public void setStoryTitle(String storyTitle) {
        mStoryTitle.setText(storyTitle);
    }

    @Override
    public void setStoryBody(String storyBody) {
        mStoryBody.setDrawingCacheEnabled(true);
        mStoryBody.setText(storyBody, true);
    }

    @Override
    public void setStoryImage(Bitmap image) {
        mImageStory.setImageBitmap(image);
    }

    @Override
    public void setStoryToolBarTitle(String toolBarTitle) {
        mToolbarStoryTitle.setText(toolBarTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reading_menu, menu);
        favouriteItem = menu.findItem(R.id.add_favoris_menu_item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                this.onBackPressed();
                startActivity(HomeActivity.getStartIntent(this));
                return true;
            }

            case R.id.add_favoris_menu_item: {
                mHandler.postDelayed(() -> mPresenter.onAddToFavourite(), 100);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mPresenter.onShowInterstitialAd();
        super.onBackPressed();
    }
}
