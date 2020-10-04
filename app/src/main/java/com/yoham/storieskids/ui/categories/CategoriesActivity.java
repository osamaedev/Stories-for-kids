package com.yoham.storieskids.ui.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.yoham.storieskids.R;
import com.yoham.storieskids.data.DataManager;
import com.yoham.storieskids.ui.base.BaseActivity;
import com.yoham.storieskids.ui.storiesList.StoriesListActivity;
import com.yoham.storieskids.utils.AppConstants;

public class CategoriesActivity extends BaseActivity implements ICategoriesView {


    //region Local variables
    private Toolbar mToolbar;

    private CategoriesPresenter<ICategoriesView> mPresenter;

    private Handler mHandler;
    //endregion


    //region bind views

    @BindView(R.id.button_anbiaa_stories)
    AppCompatImageButton mButtonAnbiaa;

    @BindView(R.id.button_joha_stories)
    AppCompatImageButton mButtonJoha;

    @BindView(R.id.button_animals_stories)
    AppCompatImageButton mButtonAnimals;

    @BindView(R.id.button_general_stories)
    AppCompatImageButton mButtonGeneral;

    //endregion

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CategoriesActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mToolbar = findViewById(R.id.categories_toolbar);

        setUnBinder(ButterKnife.bind(this));

        mPresenter = new CategoriesPresenter<ICategoriesView>(getDataManager(), getSchedulerProvider(), getCompositeDisposable());

        mPresenter.onAttach(this);

        mHandler = new Handler();

        setUp();

        startAnimation();
    }


    private void startAnimation() {
        mHandler.postDelayed(() -> mPresenter.onShowJohaButtonAnimation(), 1200);

        mHandler.postDelayed(() -> mPresenter.onShowAnimalsButtonAnimation(), 1400);

        mHandler.postDelayed(() -> mPresenter.onShowAnbiaaButtonAnimation(), 1000);

        mHandler.postDelayed(() -> {
            mPresenter.onShowGeneralButtonAnimation();
            mPresenter.onShowAllButtons();
        }, 1600);
    }

    @Override
    public void setUp() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void setUpAds() {
        // No ads in this
    }


    // region click Buttons

    @OnClick((R.id.button_anbiaa_stories))
    void onButtonAnbiaaClick() {
        startActivity(StoriesListActivity.getStartIntent(this, AppConstants.CATEGORY_ANBIAA_KEY, R.string.anbiaa_stories));
    }


    @OnClick(R.id.button_joha_stories)
    void onButtonJohaClick() {
        startActivity(StoriesListActivity.getStartIntent(this, AppConstants.CATEGORY_JOHA_KEY, R.string.joha_stories));
    }

    @OnClick(R.id.button_animals_stories)
    void onButtonAnimalsClick() {
        startActivity(StoriesListActivity.getStartIntent(this, AppConstants.CATEGORY_ANIMALS_KEY, R.string.animals_stories));
    }

    @OnClick(R.id.button_general_stories)
    void onButtonGeneralClick() {
        startActivity(StoriesListActivity.getStartIntent(this, AppConstants.CATEGORY_GENERAL_KEY, R.string.general_stories));
    }

    // endregion


    @Override
    public void setAllButtonsVisible() {
        mButtonAnbiaa.setVisibility(View.VISIBLE);
        mButtonJoha.setVisibility(View.VISIBLE);
        mButtonAnimals.setVisibility(View.VISIBLE);
        mButtonGeneral.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAnbiaaButtonAnimation() {
        new SlideInAnimation(mButtonAnbiaa)
                .setDirection(Animation.DIRECTION_UP)
                .animate();
    }

    @Override
    public void showJohaButtonAnimation() {
        new SlideInAnimation(mButtonJoha)
                .setDirection(Animation.DIRECTION_UP)
                .animate();
    }

    @Override
    public void showAnimalsButtonAnimation() {
        new SlideInAnimation(mButtonAnimals)
                .setDirection(Animation.DIRECTION_UP)
                .animate();
    }

    @Override
    public void showGeneralButtonAnimation() {
        new SlideInAnimation(mButtonGeneral)
                .setDirection(Animation.DIRECTION_UP)
                .animate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home :
                this.onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
