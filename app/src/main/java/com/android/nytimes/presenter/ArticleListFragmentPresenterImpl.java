package com.android.nytimes.presenter;

import com.android.nytimes.interactor.NYArticleServiceInteractor;
import com.android.nytimes.model.Result;
import com.android.nytimes.common.network.RestRequestListener;
import com.android.nytimes.view.ArticleListFragmentView;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class ArticleListFragmentPresenterImpl implements ArticleListFragmentPresenter {

    private final ArticleListFragmentView mArticleListFragmentView;
    private final NYArticleServiceInteractor mNYArticleServiceInteractor;

    public ArticleListFragmentPresenterImpl(ArticleListFragmentView articleListFragmentView, NYArticleServiceInteractor nyArticleServiceInteractor){
        mArticleListFragmentView = articleListFragmentView;
        mNYArticleServiceInteractor = nyArticleServiceInteractor;

    }

    @Override
    public void getAllArticles(String period) {

        mArticleListFragmentView.showLoadingSpinner();
        mNYArticleServiceInteractor.getArticles(period, new RestRequestListener<List<Result>>() {
            @Override
            public void onSuccess(List<Result> list) {
                mArticleListFragmentView.dismissLoadingSpinner();
                if(list != null)
                    mArticleListFragmentView.updateList(list);
            }

            @Override
            public void onFailure(Throwable t) {
                mArticleListFragmentView.dismissLoadingSpinner();
                if(t instanceof UnknownHostException) {
                    mArticleListFragmentView.showNetworkErrorDialog();
                }

            }
        });
    }
}
