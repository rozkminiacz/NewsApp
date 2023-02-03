@file:OptIn(ExperimentalAnimationApi::class)

package com.kotlintesting.news.ui.animations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun enterSlideTransition(
  initialDestinationRoute: String,
  slideDirection: AnimatedContentScope.SlideDirection
): AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition? =
  {
    when (initialState.destination.route) {
      initialDestinationRoute ->
        slideIntoContainer(
          slideDirection,
          animationSpec = tween(700)
        )
      else -> null
    }
  }

fun exitSlideTransition(
  exitTargetRoute: String,
  slideDirection: AnimatedContentScope.SlideDirection
): AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition? =
  {
    when (targetState.destination.route) {
      exitTargetRoute -> {
        slideOutOfContainer(
          slideDirection,
          animationSpec = tween(700)
        )
      }
      else -> null
    }
  }