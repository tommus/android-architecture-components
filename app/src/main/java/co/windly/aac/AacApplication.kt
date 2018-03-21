package co.windly.aac

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import co.windly.aac.utilities.AacLogger
import co.windly.aac.utilities.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class AacApplication : Application(), HasActivityInjector {

  @Inject
  lateinit var injector: DispatchingAndroidInjector<Activity>

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  override fun onCreate() {
    super.onCreate()

    DaggerApplicationComponent.builder()
      .application(this)
      .build()
      .inject(this)

    AacLogger.init()
  }

  override fun activityInjector(): AndroidInjector<Activity> = this.injector
}
