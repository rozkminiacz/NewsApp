package com.kotlintesting.news.app.displayable

import com.kotlintesting.news.domain.ArticleEntity
import com.kotlintesting.news.domain.Mapper

class ArticleEntityToDisplayableMapper : Mapper<ArticleEntity, NewsDisplayable> {
  override fun map(from: ArticleEntity): NewsDisplayable {
    return NewsDisplayable(
      id = from.id,
      publishedAt = from.publishedAt.toString(),
      author = from.author ?: Defaults.UnknownAuthor,
      content = from.content,
      title = from.title ?: Defaults.EmptyText,
      description = from.description ?: Defaults.EmptyText,
      source = from.sourceName,
      image = from.urlToImage ?: Defaults.DefaultImage,
      linkToSource = from.url
    )
  }
}

object Defaults {

  val DefaultImage: String = ""
  val EmptyText: String = ""
  val UnknownAuthor: String = "Unknown"
}
