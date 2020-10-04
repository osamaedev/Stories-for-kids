package com.yoham.storieskids.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.aerserv.sdk.AerServSdk;
import com.google.android.gms.ads.MobileAds;
import com.yoham.storieskids.R;
import com.yoham.storieskids.data.DataManager;
import com.yoham.storieskids.data.db.DBHelper;
import com.yoham.storieskids.utils.AppConstants;
import com.yoham.storieskids.utils.CommonUtils;
import com.yoham.storieskids.utils.NetworkUtils;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;
import com.yoham.storieskids.utils.rx.SchedulerProvider;

import net.sqlcipher.database.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

import static com.yoham.storieskids.utils.AppConstants.IN_AD_APP_ID;
import static com.yoham.storieskids.utils.SecurityUtils.getSP;
import static com.yoham.storieskids.utils.SecurityUtils.decryptString;
import static com.yoham.storieskids.utils.SecurityUtils.getFP;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    //region Local variables
    private DBHelper mDBHelper;
    private CompositeDisposable mCompositeDisposable;
    private ISchedulerProvider mSchedulerProvider;
    private DataManager mDataManager;

    private Unbinder mUnBinder;

    private ProgressDialog mProgressDialog;
    //endregion

    public void setUnBinder(Unbinder mUnBinder) {
        this.mUnBinder = mUnBinder;
    }

    public DBHelper getDBHelper() {
        return mDBHelper;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ISchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase.loadLibs(this);
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = new SchedulerProvider();
        mDataManager = new DataManager(this, mDBHelper);

        MobileAds.initialize(this, getDecryptedValue(AppConstants.AD_MOB_APP_ID));
        AerServSdk.init(this, IN_AD_APP_ID);
    }


    public void setUpDatabase() {
        mDBHelper = new DBHelper(this);
        mDBHelper.createDatabase();
        mDBHelper.close();
    }


    public String gtss() {
        return getSP(getFP());
    }


    public String getDecryptedValue(String encString) {
        return decryptString(encString, gtss());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public abstract void setUp();

    public abstract void setUpAds();

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if(message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        if(mUnBinder != null)
        {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }
}
