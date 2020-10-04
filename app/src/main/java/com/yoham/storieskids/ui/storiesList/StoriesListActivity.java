package com.yoham.storieskids.ui.storiesList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseActivity;
import com.yoham.storieskids.ui.storiesList.StoriesAdapter.StoriesListAdapter;
import com.yoham.storieskids.ui.storiesList.StoriesAdapter.StoriesListAdapterPresenter;
import com.yoham.storieskids.utils.AppConstants;

import org.jetbrains.annotations.NotNull;

import static com.yoham.storieskids.utils.AppConstants.STORIES_LIST_TO_SHOW;

public class StoriesListActivity extends BaseActivity implements IStoriesListView {

    private static final String TAG = StoriesListActivity.class.getSimpleName();

    // region local variables

    private StoriesListPresenter<IStoriesListView> mPresenter;

    private StoriesListAdapterPresenter<IStoriesListView> mAdapterPresenter;

    private Toolbar mToolbar;

    private InterstitialAd mInterstitialAd;

    private StoriesListAdapter adapter;

    private Handler mHandler;

    //endregion

    //region Binding views

    @BindView(R.id.adMobView)
    View adContainer;

    @BindView(R.id.toolbar_title)
    AppCompatTextView mActivityTitle;

    @BindView(R.id.stories_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.no_story_in_favourites)
    AppCompatTextView mNoStoryMsg;

    //endregion

    public static Intent getStartIntent(Context context, String categoryToStart, int resStringId) {
        Intent intent = new Intent(context, StoriesListActivity.class);
        intent.putExtra(STORIES_LIST_TO_SHOW, categoryToStart);
        intent.putExtra(AppConstants.STORIES_LIST_ID_TITLE_KEY, resStringId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_list);
        mToolbar = findViewById(R.id.stories_list_toolbar);
        setUnBinder(ButterKnife.bind(this));

        mPresenter = new StoriesListPresenter<>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());

        mAdapterPresenter = new StoriesListAdapterPresenter<>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());

        mHandler = new Handler();

        mPresenter.onAttach(this);

        mAdapterPresenter.onAttach(this);

        mPresenter.onSetUpRecyclerView();

        setUpAds();

        setUp();
    }


    @Override
    public void setUp() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        int resString = getIntent().getExtras().getInt(AppConstants.STORIES_LIST_ID_TITLE_KEY);
        if (resString != 0) {
            mActivityTitle.setText(resString);
        }
    }


    @Override
    public void setUpAds() {
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getDecryptedValue(AppConstants.BANNER_1));
        ((RelativeLayout) adContainer).addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("998E7426AF3880E009A64950CAD6CE68").build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getDecryptedValue(AppConstants.INTERSTITIAL_1));
        mHandler.postDelayed(() -> mInterstitialAd.loadAd(adRequest), 10000);
    }

    @Override
    public void setUpAndShowRecyclerView() {
        mRecyclerView = findViewById(R.id.stories_recycle_view);
        adapter = new StoriesListAdapter(this, mAdapterPresenter);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void updateRecyclerView() {
        if (this.adapter.getItemCount() == 0
                && this.getIntent().getExtras().getString(STORIES_LIST_TO_SHOW).equals(AppConstants.CATEGORY_FAVOURITES_KEY)) {
            mNoStoryMsg.setVisibility(View.VISIBLE);
            return;
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d(TAG, "Interstitial not loaded !");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mAdapterPresenter.onDetach();    // I think this useless
        adapter.getPresenter().onDetach();
        mHandler.removeCallbacksAndMessages(null);
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mPresenter.onInterstitialAdCalled();
        super.onBackPressed();
    }

}
