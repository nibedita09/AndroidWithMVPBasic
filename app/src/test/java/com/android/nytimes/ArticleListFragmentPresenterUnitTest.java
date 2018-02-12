package com.android.nytimes;

import com.android.nytimes.common.network.RestRequestListener;
import com.android.nytimes.interactor.NYArticleServiceInteractor;
import com.android.nytimes.model.Result;
import com.android.nytimes.presenter.ArticleListFragmentPresenter;
import com.android.nytimes.presenter.ArticleListFragmentPresenterImpl;
import com.android.nytimes.view.ArticleListFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.stubbing.answers.ThrowsExceptionClass;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Nibedita on 12/02/2018.
 */

public class ArticleListFragmentPresenterUnitTest {

    private ArticleListFragmentPresenter mArticleListFragmentPresenter;
    private FakeArticleListFragmentView fakeArticleListFragmentView;

    @Before
    public void setUp(){
        fakeArticleListFragmentView = new FakeArticleListFragmentView();
    }


    @Test
    public void test_getArticleList_Success(){
        NYArticleServiceInteractor fakeNYArticleServiceInteractor = new FakeNYArticleServiceInteractor(true);
        mArticleListFragmentPresenter = new ArticleListFragmentPresenterImpl(fakeArticleListFragmentView, fakeNYArticleServiceInteractor);
        assertFalse(fakeArticleListFragmentView.size == 2);
        mArticleListFragmentPresenter.getAllArticles("1");
        assertTrue(fakeArticleListFragmentView.size == 2);
        assertFalse(fakeArticleListFragmentView.size == 4);
    }

    @Test
    public void test_getArticleList_Fail(){
        NYArticleServiceInteractor fakeNYArticleServiceInteractor = new FakeNYArticleServiceInteractor(false);
        mArticleListFragmentPresenter = new ArticleListFragmentPresenterImpl(fakeArticleListFragmentView, fakeNYArticleServiceInteractor);
        assertFalse(fakeArticleListFragmentView.isErrorDialogShown == true);
        mArticleListFragmentPresenter.getAllArticles("1");
        assertTrue(fakeArticleListFragmentView.isErrorDialogShown == true);
    }


    public class FakeArticleListFragmentView implements ArticleListFragmentView{

        public int size;
        private boolean isErrorDialogShown = false;

        @Override
        public void updateList(List<Result> articleList) {
            if(articleList != null){
                size = articleList.size();
            }
        }

        @Override
        public void navigateTo(String url) {

        }

        @Override
        public void showLoadingSpinner() {
        }

        @Override
        public void dismissLoadingSpinner() {
        }

        @Override
        public void showNetworkErrorDialog() {
            isErrorDialogShown = true;
        }
    }

    public class FakeNYArticleServiceInteractor implements NYArticleServiceInteractor{

        private final boolean mIsSuccess;

        public FakeNYArticleServiceInteractor(boolean isSuccess){
            mIsSuccess = isSuccess;
        }
        @Override
        public void getArticles(String period, RestRequestListener restRequestListener) {
            if(mIsSuccess)
                restRequestListener.onSuccess(createArticleList());
            else {
                restRequestListener.onFailure(new UnknownHostException());
            }
        }
    }

    private List<Result> createArticleList(){
        List<Result> list = new ArrayList<>();
        list.add(new Result());
        list.add(new Result());
        return list;
    }
}
