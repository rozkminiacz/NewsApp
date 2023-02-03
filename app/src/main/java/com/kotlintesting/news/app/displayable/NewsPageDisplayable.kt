package com.kotlintesting.news.app.displayable

data class NewsPageDisplayable(
  val elements: List<NewsDisplayable>
) : List<NewsDisplayable> by elements
