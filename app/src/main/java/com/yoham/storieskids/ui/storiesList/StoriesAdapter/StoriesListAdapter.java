package com.yoham.storieskids.ui.storiesList.StoriesAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yoham.storieskids.R;
import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.ui.reading.ReadingActivity;
import com.yoham.storieskids.utils.AppConstants;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StoriesListAdapter extends RecyclerView.Adapter<StoriesListAdapter.CustomViewHolder> {


    //region Local variables

    private StoriesListAdapterPresenter mPresenter;

    private Context mContext;

    //endregion

    public StoriesListAdapterPresenter getPresenter() {
        return mPresenter;
    }

    public StoriesListAdapter (@NonNull Activity context, StoriesListAdapterPresenter presenter) {
        mPresenter = presenter;
        mContext = context;
        switch (context.getIntent().getExtras().getString(AppConstants.STORIES_LIST_TO_SHOW)) {

            case AppConstants.CATEGORY_ANBIAA_KEY: mPresenter.getAnbiaaStories(); break ;

            case AppConstants.CATEGORY_ANIMALS_KEY: mPresenter.getAnimalsStories(); break;

            case AppConstants.CATEGORY_FAVOURITES_KEY: mPresenter.getFavouritesStories(); break;

            case AppConstants.CATEGORY_GENERAL_KEY: mPresenter.getGeneralStories(); break;

            case AppConstants.CATEGORY_JOHA_KEY: mPresenter.getJohaStories(); break;

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        mPresenter.onBindRepositoryRowViewAtPosition(position, holder);

        holder.setStoryItemClickListener(new OnStoryItemClickListener() {
            @Override
            public void onItemClicked(int id, View view) {
                if (id != 0) {
                    mContext.startActivity(ReadingActivity.getStartIntent(mContext, id));
                }
            }

            @Override
            public void onLongItemClicked(int position, View view) {
                Toast.makeText(mContext, R.string.click_once, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPresenter.onGetItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }




    public interface OnStoryItemClickListener {

        void onItemClicked(int id, View view);

        void onLongItemClicked(int id, View view);

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements IItemListView, View.OnClickListener, View.OnLongClickListener {

        // region Bind views

        @BindView(R.id.title_list_item)
        AppCompatTextView title;

        @BindView(R.id.image_list_item)
        AppCompatImageView imageView;

        @BindView(R.id.description_list_item)
        AppCompatTextView description;

        // endregion

        private OnStoryItemClickListener mStoryItemClickListener;
        private int mStoryId = 0;

        public void setStoryItemClickListener (OnStoryItemClickListener mStoryItemClickListener) {
            this.mStoryItemClickListener = mStoryItemClickListener;
        }

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void setDescription(String description) {
            this.description.setText(description);
        }

        @Override
        public void setImage(Bitmap image) {
            this.imageView.setImageBitmap(image);
        }

        @Override
        public void setStoryId(int storyId) {
            this.mStoryId = storyId;
        }

        @Override
        public void onClick(View v) {
            mStoryItemClickListener.onItemClicked(mStoryId, v);
        }

        @Override
        public boolean onLongClick(View v) {
            mStoryItemClickListener.onLongItemClicked(mStoryId, v);
            return true;
        }

        //region not
		
        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void showMessage(int resId) {

        }

        @Override
        public void showMessage(String message) {

        }

        @Override
        public boolean isNetworkConnected() {
            return false;
        }

        @Override
        public void hideKeyboard() {

        }
		
        //endregion

    }
}
