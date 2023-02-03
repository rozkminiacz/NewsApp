@file:OptIn(ExperimentalAnimationApi::class)

package com.kotlintesting.news.app

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kotlintesting.news.app.Routes.Companion.NAV_ARGUMENT_ID
import com.kotlintesting.news.app.details.NewsFullPageScreen
import com.kotlintesting.news.app.list.NewsListScreen
import com.kotlintesting.news.ui.animations.enterSlideTransition
import com.kotlintesting.news.ui.animations.exitSlideTransition

@Composable
fun NavContainer() {
  val navController = rememberAnimatedNavController()

  AnimatedNavHost(navController = navController, startDestination = Routes.List.routeSchema) {

    composable(route = Routes.List.routeSchema) {
      NewsListScreen(openDetails = {
        navController.navigate(route = Routes.Details.create(it.id))
      })
    }

    composable(
      route = Routes.Details.routeSchema,
      arguments = listOf(navArgument(NAV_ARGUMENT_ID) { type = NavType.StringType }),
      enterTransition = enterSlideTransition(
        initialDestinationRoute = Routes.List.routeSchema,
        slideDirection = AnimatedContentScope.SlideDirection.Start
      ),
      exitTransition = exitSlideTransition(
        exitTargetRoute = Routes.List.routeSchema,
        slideDirection = AnimatedContentScope.SlideDirection.End
      )
    ) {
      val newsId = it.arguments?.getString(NAV_ARGUMENT_ID)

      NewsFullPageScreen(
        id = newsId.orEmpty(),
        openLink = { url ->
          navController.openUrl(url)
        }
      )
    }
  }
}

sealed class Routes(val routeSchema: String) {
  object List : Routes("list")
  object Details : Routes("details/{$NAV_ARGUMENT_ID}") {
    fun create(id: String) = routeSchema.replace("{$NAV_ARGUMENT_ID}", id)
  }

  companion object {
    const val NAV_ARGUMENT_ID = "id"
  }
}

private fun NavController.openUrl(url: String) {
  val i = Intent(Intent.ACTION_VIEW)
  i.data = Uri.parse(url)
  context.startActivity(i)
}