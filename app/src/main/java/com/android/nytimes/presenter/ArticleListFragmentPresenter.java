package com.android.nytimes.presenter;

import com.android.nytimes.model.AllItems;
import com.android.nytimes.model.Result;

import java.util.List;

/**
 * Created by Nibedita on 11/02/2018.
 */

public interface ArticleListFragmentPresenter {

    List<Result> getAllArticles(String period);

}
