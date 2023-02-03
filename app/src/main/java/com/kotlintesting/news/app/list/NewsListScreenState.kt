package com.kotlintesting.news.app.list

import com.kotlintesting.news.app.displayable.NewsPageDisplayable
import com.kotlintesting.news.domain.AppError

data class NewsListScreenState(
  val pageDisplayable: NewsPageDisplayable,
  val loading: Boolean,
  val error: AppError?,
  val searchQuery: String,
) {
  fun isError() = error != null
  fun hasEmptyResults() = pageDisplayable.isEmpty()
  fun hasResults() = pageDisplayable.isNotEmpty()

  companion object {
    val INITIAL = NewsListScreenState(
      NewsPageDisplayable(emptyList()),
      loading = true,
      error = null,
      searchQuery = ""
    )
  }
}