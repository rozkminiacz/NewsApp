package com.kotlintesting.news

import android.app.Application
import android.util.Log
import com.google.android.gms.common.util.CrashUtils
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * Created by jaroslawmichalik
 */
class App : Application() {
  override fun onCreate() {
    super.onCreate()

    if(enableLogging()){
      Timber.plant(Timber.DebugTree())
    }

    startKoin{
      if(enableLogging()){
        androidLogger()
      }
      androidContext(this@App)
      modules(appModule)
    }

  }

  private fun enableLogging(): Boolean {
    return BuildConfig.ENABLE_LOGGING
  }
}