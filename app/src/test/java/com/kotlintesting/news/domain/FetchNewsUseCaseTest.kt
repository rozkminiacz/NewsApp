package com.kotlintesting.news.domain

import com.kotlintesting.news.data.InMemoryDatabase
import com.kotlintesting.news.data.dto.ArticleDto
import com.kotlintesting.news.data.dto.NewsResponseDto
import com.kotlintesting.news.fakeArticleDtos
import io.kotest.core.spec.style.FreeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions

/**
 * Created by jaroslawmichalik on 15/06/2023
 */
class FetchNewsUseCaseTest : FreeSpec({
  "given query = twitter, when 10 articles present " - {

    val givenQuery = "twitter"
    val articleDtos = fakeArticleDtos(10)

    val inMemoryDatabase = mockk<InMemoryDatabase>(relaxUnitFun = true)

    val fetchNewsUseCase = FetchNewsUseCase(
      newsApi = mockk {
        coEvery { getBy(eq(givenQuery)) } returns NewsResponseDto(
          status = "asd",
          articles = articleDtos,
          totalResults = 10
        )
      },
      mapper = ArticleDtoToEntityMapper(),
      inMemoryDatabase = inMemoryDatabase
    )

    "then return that articles" {
      val articles = fetchNewsUseCase.fetchData(givenQuery)

      Assertions.assertEquals(10, articles.size)
    }

    "then update in memory database" {
      coVerify(exactly = 1) { inMemoryDatabase.update(any()) }
    }

  }

  "given query = facebook, when no articles present " - {

    val givenQuery = "facebook"
    val articleDtos = emptyList<ArticleDto>()

    val inMemoryDatabase = mockk<InMemoryDatabase>(relaxUnitFun = true)

    val fetchNewsUseCase = FetchNewsUseCase(
      newsApi = mockk {
        coEvery { getBy(eq(givenQuery)) } returns NewsResponseDto(
          status = "asd",
          articles = articleDtos,
          totalResults = 10
        )
      },
      mapper = ArticleDtoToEntityMapper(),
      inMemoryDatabase = inMemoryDatabase
    )

    "then return empty list" {
      val articles = fetchNewsUseCase.fetchData(givenQuery)

      Assertions.assertEquals(0, articles.size)
    }

    "then don't update in memory database" {
      coVerify(exactly = 0) { inMemoryDatabase.update(any()) }
    }
  }
})