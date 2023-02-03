package com.kotlintesting.news.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import com.kotlintesting.news.data.dto.NewsResponseDto
import com.kotlintesting.news.domain.AppError
import com.kotlintesting.news.domain.Mapper
import timber.log.Timber

/**
 * Created by jaroslawmichalik
 */
class RemoteNewsApi(
  baseUrl: String,
  apiKey: String,
  enableLogging: Boolean = false,
  private val errorMapper: ErrorMapper
) : NewsApi {
  private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(apiKeyInterceptor(apiKey))
    .apply {
      if(enableLogging){
        addInterceptor(loggingInterceptor())
      }
    }
    .build()

  private fun loggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor{
      Timber.d(it)
    }.setLevel(HttpLoggingInterceptor.Level.BODY)
  }

  private fun apiKeyInterceptor(apiKey: String): Interceptor {
    return Interceptor {
      val requestWithKey = it.request().newBuilder().addHeader(
        "X-Api-Key", apiKey
      ).build()
      it.proceed(requestWithKey)
    }
  }

  private val api by lazy {
    Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(baseUrl)
      .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
      .build()
      .create(NewsApi::class.java)
  }

  override suspend fun getBy(query: String): NewsResponseDto {
    return try {
      api.getBy(query)
    } catch (httpException: HttpException){
      throw errorMapper.map(httpException)
    }
  }
}

class ErrorMapper(): Mapper<HttpException, AppError>{
  override fun map(from: HttpException): AppError {
    return from.response()?.errorBody()?.let {
      Json.decodeFromStream<ErrorDto>(it.byteStream())
    }?.let {
      AppError.NetworkError(
        message = it.message,
        code = it.code,
        httpCode = from.code()
      )
    }?: AppError.Unknown
  }


  //{"status":"error","code":"rateLimited","message":"You have made too many requests recently. Developer accounts are limited to 100 requests over a 24 hour period (50 requests available every 12 hours). Please upgrade to a paid plan if you need more requests."}
  @Serializable
  data class ErrorDto(
    @SerialName("status")
    val status: String,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String
  )

}

interface NewsApi {
  @GET("/v2/everything?sortby=publishedat")
  suspend fun getBy(@Query("q") query: String): NewsResponseDto
}