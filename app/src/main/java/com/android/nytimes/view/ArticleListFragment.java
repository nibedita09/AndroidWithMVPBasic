package com.android.nytimes.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nytimes.common.ui.BaseFragment;
import com.android.nytimes.common.ui.FragmentCallback;
import com.android.nytimes.R;
import com.android.nytimes.interactor.NYArticleServiceInteractorImpl;
import com.android.nytimes.model.Result;
import com.android.nytimes.presenter.ArticleListFragmentPresenter;
import com.android.nytimes.presenter.ArticleListFragmentPresenterImpl;
import com.android.nytimes.common.utility.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class ArticleListFragment extends BaseFragment implements ArticleListFragmentView, ArticleListItemClickListener {

    private RecyclerView mRecyclerView;
    private FragmentCallback mFragmentCallback;
    private ArticleListFragmentPresenter mArticleListFragmentPresenter;
    private FragmentManager mFragmentManager;
    private ArticleListAdapter mArticleListAdapter;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticleListFragmentPresenter = new ArticleListFragmentPresenterImpl(this, new NYArticleServiceInteractorImpl());
        mArticleListFragmentPresenter.getAllArticles("7");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null)
            mView = inflater.inflate(R.layout.article_list, null);
        else
            return mView;

        mRecyclerView = mView.findViewById(R.id.listView);
        mRecyclerView.addItemDecoration(CommonUtils.getListDivider(getActivity()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentManager = getActivity().getSupportFragmentManager();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null && context instanceof FragmentCallback){
            mFragmentCallback = (FragmentCallback) context;
        }
    }

    @Override
    public void updateList(List<Result> articleList) {
        if(mArticleListAdapter == null)
            mArticleListAdapter = new ArticleListAdapter(articleList, this, Picasso.with(getActivity()), this);
        mRecyclerView.setAdapter(mArticleListAdapter);
        if(mFragmentCallback.isTwoPane())
            onItemClick(articleList.get(0).getUrl());
    }

    @Override
    public void navigateTo(String url) {

        Fragment detailFragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        mFragmentManager.beginTransaction().replace(R.id.container_detail, detailFragment).commit();
    }

    @Override
    public void showLoadingSpinner() {
        mFragmentCallback.showSpinner(getString(R.string.loading));
    }

    @Override
    public void dismissLoadingSpinner() {
        mFragmentCallback.dismissSpinner();
    }

    @Override
    public void showNetworkErrorDialog() {
        mFragmentCallback.showErrorDialog(getString(R.string.network_error), "Please check your network connection", new FragmentCallback.ErrorDialogAction() {
            @Override
            public void onClickOk() {
                //nothing
            }

            @Override
            public void onClickRetry() {
                mArticleListFragmentPresenter.getAllArticles("7");
            }
        });

    }

    @Override
    public void onItemClick(String url) {
        final Fragment detailFragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        detailFragment.setArguments(bundle);
        if(mFragmentCallback.isTwoPane()){
            mFragmentCallback.updateRightPane(detailFragment);
        }else{
            mFragmentCallback.navigateTo(detailFragment);
        }
    }
}
