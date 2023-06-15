package com.kotlintesting.news.app.list

import app.cash.turbine.testIn
import com.kotlintesting.news.app.displayable.ArticleEntityToDisplayableMapper
import com.kotlintesting.news.app.displayable.NewsPageDisplayable
import com.kotlintesting.news.domain.FetchNewsUseCase
import com.kotlintesting.news.fakeArticles
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by jaroslawmichalik on 15/06/2023
 */
class NewsListViewModelTest : BehaviorSpec({
  Given("11 elements for query = Twitter") {
    val fetchNewsUseCase = mockk<FetchNewsUseCase> {
      coEvery { fetchData(eq("Twitter")) } returns fakeArticles(11)
    }

    // configuration
    When("viewmodel start") {

      val viewModel = createViewModel(
        fetchNewsUseCase = fetchNewsUseCase
      )

      Then("render state") {

        val viewrobot = StateFlowTurbineViewRobot<NewsListScreenState>(
          stateFlow = viewModel.state
        )

        viewrobot.collect()
        viewrobot.collect()

        viewrobot.close()

        Assertions.assertTrue(viewrobot.listOfState[0].loading)
        Assertions.assertFalse(viewrobot.listOfState[1].loading)
      }
    }
  When("new query"){
    Then("x"){

    }
  }
  }

})

fun createViewModel(
  fetchNewsUseCase: FetchNewsUseCase = mockk(relaxed = true)
) = NewsListViewModel(
  fetchNewsUseCase = fetchNewsUseCase,
  displayableMapper = ArticleEntityToDisplayableMapper(),
  resolveDefaultSearchQuery = mockk {
    every { fetchDefaultQuery() } returns "Twitter"
  }
)

class StateFlowTurbineViewRobot<T>(
  val scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext),
  val stateFlow: StateFlow<T>
){
  val listOfState = mutableListOf<T>()

  private val turbine = stateFlow.testIn(scope)

  suspend fun collect(){
    listOfState.add(turbine.awaitItem())
  }

  suspend fun close(){
    turbine.cancel()
  }
}