package com.kotlintesting.news.app.list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.kotlintesting.news.R
import com.kotlintesting.news.app.NewsListElement
import com.kotlintesting.news.app.displayable.NewsDisplayable
import com.kotlintesting.news.app.displayable.NewsPageDisplayable

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(openDetails: (NewsDisplayable) -> Unit) {
  val viewModel: NewsListViewModel = koinViewModel()

  val state = viewModel.state.collectAsState()

  Scaffold(topBar = {
    TopAppBar(
      title = {},
      actions = {

        Box() {
          TextField(
            value = state.value.searchQuery,
            onValueChange = { text: String -> viewModel.search(text) },
            modifier = Modifier
              .padding(8.dp)
              .align(Alignment.TopStart)
              .fillMaxWidth(),
            placeholder = {
              Text(text = stringResource(id = R.string.search_query_hint))
            }
          )
          if (state.value.loading) {
            CircularProgressIndicator(
              modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterEnd)
            )
          }
        }
      }
    )
  }) { padding ->
    val value = state.value
    when {
      value.hasEmptyResults() && value.isError() -> {
        Column {
          Row() {
            ErrorMessage(value.error!!, Modifier.padding(padding)) {
              viewModel.rerunCurrentSearch()
            }
          }
          Row() {
            NoData(Modifier.padding(padding))
          }
        }
      }
      value.isError() -> {
        ErrorMessage(value.error!!, Modifier.padding(padding)) {
          viewModel.rerunCurrentSearch()
        }
      }
      value.hasEmptyResults() && value.loading.not() -> {
        NoData(Modifier.padding(padding))
      }
      value.hasResults() -> {
        NewsList(value.pageDisplayable, openDetails, Modifier.padding(padding))
      }
    }
  }

}

@Composable
fun NoData(modifier: Modifier) {
  Box(modifier = modifier) {
    Column(verticalArrangement = Arrangement.Center) {
      Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Image(
          painter = painterResource(id = R.drawable.ic_baseline_view_list_24),
          contentDescription = "no data",
          modifier = Modifier.size(72.dp),
        )
      }
      Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text(
          text = stringResource(id = R.string.no_results),
          color = MaterialTheme.colorScheme.secondary,
          style = MaterialTheme.typography.labelMedium,
          textAlign = TextAlign.Center
        )
      }
    }

  }
}

@Composable
fun ErrorMessage(appError: Throwable, modifier: Modifier = Modifier, retry: () -> Unit) {
  Box(modifier.padding(horizontal = 8.dp)) {
    Column(
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer),
    ) {
      Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text(
          text = appError.message.orEmpty(),
          color = MaterialTheme.colorScheme.onErrorContainer,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(8.dp),
        )
      }
      Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Button(
          onClick = { retry() }, modifier = Modifier
            .align(CenterVertically)
            .padding(8.dp)
        ) {
          Text(text = stringResource(id = R.string.retry))
        }
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewsList(
  page: NewsPageDisplayable,
  openDetails: (NewsDisplayable) -> Unit,
  modifier: Modifier
) {
  LazyColumn(
    modifier = modifier
  ) {
    items(items = page, key = {
      it.id
    }) {
      Row(
        modifier = Modifier.animateItemPlacement(
          tween(durationMillis = 250)
        )
      ) {
        NewsListElement(displayable = it) {
          openDetails(it)
        }
      }
      Row {
        Spacer(modifier = Modifier.size(16.dp))
      }
    }
  }
}

