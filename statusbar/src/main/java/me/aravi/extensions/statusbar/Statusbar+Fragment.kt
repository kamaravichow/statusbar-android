package me.aravi.extensions.statusbar

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

/** Set the status bar color */
fun Fragment.statusBarColor(@ColorInt color: Int) = activity?.statusBarColor(color)


/** Set the status bar color */
fun Fragment.statusBarColorRes(@ColorRes colorRes: Int) = activity?.statusBarColorRes(colorRes)
