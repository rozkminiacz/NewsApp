package com.kotlintesting.news.domain

import kotlinx.datetime.Instant
import com.kotlintesting.news.data.dto.ArticleDto

class ArticleDtoToEntityMapper : Mapper<ArticleDto, ArticleEntity> {
  override fun map(from: ArticleDto): ArticleEntity {
    return ArticleEntity(
      sourceName = from.source.name,
      title = from.title,
      url = from.url,
      description = from.description,
      content = from.content,
      author = from.author,
      publishedAt = Instant.parse(from.publishedAt),
      urlToImage = from.urlToImage
    )
  }
}

interface Mapper<in From, out To> {
  fun map(from: From): To
}
