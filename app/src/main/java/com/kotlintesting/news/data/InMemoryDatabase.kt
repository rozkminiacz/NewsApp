package com.kotlintesting.news.data

import com.kotlintesting.news.domain.ArticleEntity

class InMemoryDatabase() {
  private val data = HashMap<String, ArticleEntity>()

  fun findById(id: String): ArticleEntity? {
    return data[id]
  }

  suspend fun update(elements: List<ArticleEntity>) {
    data.clear()
    data.putAll(
      elements.map {
        it.id to it
      }.toMap()
    )
  }
}