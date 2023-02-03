package com.kotlintesting.news.app

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.kotlintesting.news.R
import com.kotlintesting.news.app.displayable.NewsDisplayable

@Composable
fun NewsListElement(displayable: NewsDisplayable, onClick: () -> Unit = {}) {
  Card(Modifier.padding(8.dp)) {
    Column(modifier = Modifier
      .clickable { onClick() }) {
      Row {
        NewsHeadline(displayable = displayable, modifier = Modifier.fillMaxWidth())
      }
      Row(modifier = Modifier.padding(8.dp)) {
        NewsShortDescription(displayable)
      }
    }
  }
}

@Composable
fun NewsFullPageElement(
  displayable: NewsDisplayable,
  modifier: Modifier = Modifier,
  openDetailsClick: (String) -> Unit
) {
  Column(modifier) {
    Row {
      NewsHeadline(displayable = displayable)
    }
    Row() {
      NewsLongContent(displayable = displayable)
    }
    Row() {
      ReadMore(displayable = displayable) {
        openDetailsClick(it)
      }
    }
  }
}

@Composable
fun ReadMore(displayable: NewsDisplayable, click: (String) -> Unit) {
  TextButton(onClick = {
    click(displayable.linkToSource)
  }) {
    Text(text = stringResource(R.string.read_more, displayable.source))
  }
}

@Composable
fun NewsHeadline(displayable: NewsDisplayable, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row {
      AsyncImage(
        model = displayable.image,
        contentDescription = displayable.imageDescription,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
      )
    }

    val modifierWithSidePadding = Modifier.padding(8.dp)

    Row(modifierWithSidePadding) {
      Text(
        text = displayable.title,
        style = MaterialTheme.typography.titleMedium
      )
    }

    Row(modifierWithSidePadding.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
      Column(
        verticalArrangement = Arrangement.Center,
      ) {
        Label(
          displayable.source, modifier = Modifier.align(Start)
        )
      }

      Column(
        verticalArrangement = Arrangement.Center,
      ) {
        Text(
          text = displayable.readableDate,
          style = MaterialTheme.typography.labelMedium,
          modifier = Modifier.align(End),
          textAlign = TextAlign.End
        )
      }
    }
  }
}

@Composable
private fun Label(text: String, modifier: Modifier) {
  Box(
    modifier = modifier
      .background(MaterialTheme.colorScheme.primary)
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onPrimary,
      modifier = Modifier.padding(4.dp)
    )
  }
}

@Composable
fun NewsLongContent(displayable: NewsDisplayable, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row(Modifier.padding(8.dp)) {
      Html(text = displayable.content)
    }
  }
}

@Composable
fun NewsShortDescription(displayable: NewsDisplayable, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row() {
      Html(text = displayable.description)
    }
  }
}

@Composable
fun Html(text: String) {
  AndroidView(factory = { context ->
    TextView(context).apply {
      setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }
  })
}