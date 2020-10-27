package com.harsh.tpf.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.harsh.tpf.R
import timber.log.Timber

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions = androidx.navigation.navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    },
    navExtras: Navigator.Extras? = null,
) {
    try {
        navigate(resId, args, navOptions, navExtras)
    } catch (e: Exception) {
        Timber.log(0, e)
    }
}