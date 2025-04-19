package com.smartplant.smartplantandroid.core.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Transition
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun AppCompatActivity.startNewActivity(newActivity: Class<out AppCompatActivity>) {
    val intent = Intent(this, newActivity)
    startActivity(intent)
}

fun AppCompatActivity.startNewActivity(newActivity: Class<out AppCompatActivity>, extras: Bundle) {
    val intent = Intent(this, newActivity).apply { putExtras(extras) }
    startActivity(intent)
}

fun AppCompatActivity.replaceFragment(@IdRes fragmentId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        replace(fragmentId, fragment)
        commit()
    }
}

fun AppCompatActivity.replaceFragment(
    @IdRes fragmentId: Int,
    fragment: Fragment,
    enterAnim: Int,
    exitAnim: Int,
    popEnterAnim: Int,
    popExitAnim: Int
) {
    supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        replace(fragmentId, fragment)
        commit()
    }
}

fun AppCompatActivity.replaceFragment(
    @IdRes fragmentId: Int,
    fragment: Fragment,
    animation: IntArray
) {
    require(animation.size == 4) { "Animations array must have exactly 4 elements." }

    supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(animation[0], animation[1], animation[2], animation[3])
        replace(fragmentId, fragment)
        commit()
    }
}
