package com.kotlintesting.news.domain

import com.kotlintesting.news.data.InMemoryDatabase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Created by jaroslawmichalik on 15/06/2023
 */
class FetchNewsItemUseCaseTest {

  // what test cases should cover here?
  //
  /**
   * GIVEN newsID
   * WHEN fetch article with id = newsID EXISTS
   * THEN return that that article
   */

  /**
   * GIVEN newsID
   * WHEN fetch article with id = newsId NOT EXISTS
   * THEN then throw ArticleNotFoundException
   */

  // fetching by id is working

  @Test
  fun `GIVEN newsID WHEN fetch article with id = newsID exists then return that article`() = runTest {
      //    * GIVEN newsID
      //    * WHEN fetch article with id = newsID EXISTS
      //    * THEN return that that article

      val articleEntity = ArticleEntity(
        sourceName = "Source #",
        title = "Title #",
        content = "Content",
        author = "Author",
        publishedAt = Instant.DISTANT_PAST,
        urlToImage = null,
        description = null,
        url = "https://link.to.article/"
      )

      val articleId = articleEntity.id

      val useCase = FetchNewsItemUseCase(
        inMemoryDatabase = mockk {
          coEvery { findById(eq(articleId)) } returns articleEntity
        }
      )

      val expectedArticle: ArticleEntity = articleEntity

      val actualArticle = useCase.fetchNewsItemById(articleId)


      assertEquals(expectedArticle, actualArticle)
    }

  @Test
  fun `GIVEN newsID WHEN fetch article with id = newsID not exists then throw exception`() =
    runTest {
      //    * GIVEN newsID
      //    * WHEN fetch article with id = newsID NOT EXISTS
      //    * THEN return that that article

      val givenId = "aksjdhas7812"

      val useCase = FetchNewsItemUseCase(
        inMemoryDatabase = mockk {
          coEvery { findById(eq(givenId)) } returns null
        }
      )

      val exception = assertThrows<ArticleNotFoundException> {
        useCase.fetchNewsItemById(givenId)
      }

      println(exception)
    }
}