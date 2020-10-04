package com.yoham.storieskids.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseActivity;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

public final class AppUtils {

    public AppUtils() {

    }

    public static void openPlayStoreForRatingApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static void openShareDialog(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String sharingMessage = context.getResources().getString(R.string.sharing_message) + "\n"
                + context.getResources().getString(R.string.app_google_play_store_link) + context.getPackageName();
        String subject = context.getResources().getString(R.string.sharing_subject);
        String sharingTitle = context.getResources().getString(R.string.sharing_title);

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sharingMessage);
        context.startActivity(Intent.createChooser(sharingIntent, sharingTitle));
    }


    public static String getDbPath(Context context) {
        if(Build.VERSION.SDK_INT >= 17) {
            return context.getApplicationInfo().dataDir + "/databases/";
        }
        else {
            return "data/data" + context.getPackageName() + "/databases/";
        }
    }
}
