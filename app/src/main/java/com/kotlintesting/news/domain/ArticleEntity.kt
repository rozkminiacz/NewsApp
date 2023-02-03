package com.kotlintesting.news.domain

import kotlinx.datetime.Instant
import java.util.UUID

/**
 * Created by jaroslawmichalik
 */

data class ArticleEntity(
  val sourceName: String,
  val author: String?,
  val title: String?,
  val description: String?,
  val url: String,
  val urlToImage: String?,
  val publishedAt: Instant,
  val content: String
){
  // additional id
  val id: String = UUID.randomUUID().toString()
}