package com.smartplant.core_android.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SharedPrefs(val name: String)
