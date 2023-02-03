package com.kotlintesting.news.domain

/**
 * Created by jaroslawmichalik
 */
sealed class AppError(override val message: String?, override val cause: Throwable?) :
  Throwable(message, cause) {
  object Unknown : AppError("0", null)

  data class NetworkError(val httpCode: Int, val code: String, override val message: String) : AppError(message, null)
}