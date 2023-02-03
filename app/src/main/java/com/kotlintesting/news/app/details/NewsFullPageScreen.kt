package com.kotlintesting.news.app.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.kotlintesting.news.app.NewsFullPageElement

@Composable
fun NewsFullPageScreen(id: String, openLink: (String)->Unit) {

  val viewModel: NewsFullPageViewModel = koinViewModel(
    parameters = { parametersOf(id) }
  )

  val state = viewModel.state.collectAsState()

  val value = state.value

  when {
    value.displayable != null -> {
      NewsFullPageElement(displayable = value.displayable) {
        openLink(it)
      }
    }
  }
}