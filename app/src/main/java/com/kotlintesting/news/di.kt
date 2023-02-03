package com.kotlintesting.news

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.kotlintesting.news.app.details.NewsFullPageViewModel
import com.kotlintesting.news.app.displayable.ArticleEntityToDisplayableMapper
import com.kotlintesting.news.app.list.NewsListViewModel
import com.kotlintesting.news.data.ErrorMapper
import com.kotlintesting.news.data.InMemoryDatabase
import com.kotlintesting.news.data.NewsApi
import com.kotlintesting.news.data.RemoteNewsApi
import com.kotlintesting.news.domain.ArticleDtoToEntityMapper
import com.kotlintesting.news.domain.FetchNewsItemUseCase
import com.kotlintesting.news.domain.FetchNewsUseCase
import com.kotlintesting.news.domain.ResolveDefaultSearchQueryUseCase

/**
 * Created by jaroslawmichalik
 */
val appModule = module {
  single {
    InMemoryDatabase()
  }

  single {
    FetchNewsItemUseCase(get())
  }

  single {
    FetchNewsUseCase(get(), get(), get())
  }

  single<NewsApi> {
    RemoteNewsApi(
      baseUrl = BuildConfig.BASE_URL,
      apiKey = BuildConfig.API_KEY,
      enableLogging = BuildConfig.ENABLE_NETWORK_LOGGING,
      errorMapper = get()
    )
  }

  single {
    ErrorMapper()
  }

  single {
    ArticleDtoToEntityMapper()
  }

  single {
    ArticleEntityToDisplayableMapper()
  }

  single {
    ResolveDefaultSearchQueryUseCase()
  }

  viewModel {
    NewsListViewModel(get(), get(), get())
  }

  viewModel { parameters ->
    NewsFullPageViewModel(newsId = parameters.get(), get(), get())
  }
}