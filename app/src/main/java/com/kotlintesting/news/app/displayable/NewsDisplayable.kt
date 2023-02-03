package com.kotlintesting.news.app.displayable

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class NewsDisplayable(
  val id: String,
  val image: String,
  val title: String,
  val description: String,
  val publishedAt: String,
  val content: String,
  val linkToSource: String,
  val source: String,
  val author: String
) {

  val imageDescription: String = title

  val readableDate: String = publishedAt.let {
    val date = ZonedDateTime.parse(it)
    timeFormatter.format(date)
  }

  companion object {
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
  }

}