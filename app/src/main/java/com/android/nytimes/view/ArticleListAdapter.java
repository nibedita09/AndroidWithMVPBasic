package com.android.nytimes.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nytimes.R;
import com.android.nytimes.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class ArticleListAdapter<T> extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private final List<T> mData;
    private final ArticleListFragmentView mArticleListFragmentView;
    private final Picasso mPicasso;
    private final ArticleListItemClickListener mArticleListItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mTitle;
        public final TextView mSubTitle;
        public final ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mSubTitle = itemView.findViewById(R.id.sub_title);
            mImageView = itemView.findViewById(R.id.articleImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int position = getAdapterPosition();
            String url = ((Result)mData.get(position)).getUrl();
            mArticleListItemClickListener.onItemClick(url);
        }
    }

    public ArticleListAdapter(List<T> data, ArticleListFragmentView articleListFragmentView, Picasso picasso, ArticleListItemClickListener articleListItemClickListener){
        mData = data;
        mArticleListFragmentView = articleListFragmentView;
        mPicasso = picasso;
        mArticleListItemClickListener = articleListItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleListAdapter.ViewHolder holder, int position) {
        final T data = this.mData.get(position);
        if(data != null && data instanceof Result){
            Result result = (Result) data;
            holder.mTitle.setText(result.getTitle());
            holder.mSubTitle.setText(result.getPublishedDate());

            mPicasso.load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                    .resize(50,50)
                    .into(holder.mImageView);
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void refreshList(){
        notifyDataSetChanged();
    }

}
