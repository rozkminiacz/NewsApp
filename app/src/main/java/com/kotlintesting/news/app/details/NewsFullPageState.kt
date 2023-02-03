package com.kotlintesting.news.app.details

import com.kotlintesting.news.app.displayable.NewsDisplayable

data class NewsFullPageState(
  val displayable: NewsDisplayable? = null,
  val loading: Boolean = false,
  val error: Throwable? = null
)