package com.kotlintesting.news.app.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.kotlintesting.news.app.displayable.ArticleEntityToDisplayableMapper
import com.kotlintesting.news.domain.FetchNewsItemUseCase
import kotlinx.coroutines.launch

class NewsFullPageViewModel(
  newsId: String,
  fetchNewsItem: FetchNewsItemUseCase,
  private val mapper: ArticleEntityToDisplayableMapper
) : ViewModel() {
  private val _state: MutableStateFlow<NewsFullPageState> = MutableStateFlow(NewsFullPageState())

  val state: StateFlow<NewsFullPageState>
    get() = _state.asStateFlow()

  init {
    viewModelScope.launch {

      val displayable = fetchNewsItem.fetchNewsItemById(newsId).let(mapper::map)
      _state.value = _state.value.copy(
        displayable = displayable
      )
    }
  }
}