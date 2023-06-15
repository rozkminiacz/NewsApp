package com.kotlintesting.news.domain

import com.kotlintesting.news.data.InMemoryDatabase
import com.kotlintesting.news.data.NewsApi

/**
 * Created by jaroslawmichalik
 */
class FetchNewsUseCase(
  private val newsApi: NewsApi,
  private val mapper: ArticleDtoToEntityMapper,
  private val inMemoryDatabase: InMemoryDatabase
) {
  suspend fun fetchData(query: String): List<ArticleEntity> {

//    throw AppError.Unknown

    return newsApi.getBy(query).articles.map { mapper.map(it) }
      .also {
        inMemoryDatabase.update(it)
      }
  }
}

// step 1 – fetch data from API
// step 2 - map from DTO to ENTITY
// step 3 - update in memory database
// step 4 return list of entities