package me.aravi.extensions.statusbar

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout


const val COLOR_TRANSPARENT = 0

/**
 * Translucent status bar
 * It will cause the keyboard to block the input box. Setting [View.setFitsSystemWindows] to true for the root layout can be solved
 *
 * @param translucent whether to display the transparent status bar
 * @param darkMode Whether to display the dark status bar text color
 */
@Deprecated(
    "It is recommended to use immersive instead, because this function will affect the keyboard occlusion solution",
    ReplaceWith("immersive"),
    DeprecationLevel.ERROR
)


/**
 * Increase the paddingTop of the View, and increase the height to the height of the status bar to prevent the view and the status bar from overlapping
 * If the padding value is set by RelativeLayout, the properties such as centerInParent will not be displayed normally
 * @param remove If the default paddingTop is greater than the height of the status bar, the addition is invalid, if it is less than the height of the status bar, it cannot be removed
 */
@JvmOverloads
fun View.statusPadding(remove: Boolean = false) {
    if (this is RelativeLayout) {
        throw UnsupportedOperationException(context.getString(R.string.status_padding_exeption))
    }
    if (Build.VERSION.SDK_INT >= 19) {
        val statusBarHeight = context.statusBarHeight
        val lp = layoutParams
        if (lp != null && lp.height > 0) {
            lp.height += statusBarHeight //增高
        }
        if (remove) {
            if (paddingTop < statusBarHeight) return
            setPadding(
                paddingLeft, paddingTop - statusBarHeight,
                paddingRight, paddingBottom
            )
        } else {
            if (paddingTop >= statusBarHeight) return
            setPadding(
                paddingLeft, paddingTop + statusBarHeight,
                paddingRight, paddingBottom
            )
        }
    }
}

/**
 * Increase the marginTop value of the View, and increase the height to the height of the status bar to prevent the view and the status bar from overlapping
 * @param remove If the default marginTop is greater than the height of the status bar, it will be invalid to add, if it is less than the height of the status bar, it cannot be removed
 */
@JvmOverloads
fun View.statusMargin(remove: Boolean = false) {
    if (Build.VERSION.SDK_INT >= 19) {
        val statusBarHeight = context.statusBarHeight
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        if (remove) {
            if (lp.topMargin < statusBarHeight) return
            lp.topMargin -= statusBarHeight
            layoutParams = lp
        } else {
            if (lp.topMargin >= statusBarHeight) return
            lp.topMargin += statusBarHeight
            layoutParams = lp
        }
    }
}


fun Context.setTranslucentView(container: ViewGroup, color: Int) {
    if (Build.VERSION.SDK_INT >= 19) {
        var simulateStatusBar: View? = container.findViewById(android.R.id.custom)
        if (simulateStatusBar == null && color != 0) {
            simulateStatusBar = View(container.context)
            simulateStatusBar.id = android.R.id.custom
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            container.addView(simulateStatusBar, lp)
        }
        simulateStatusBar?.setBackgroundColor(color)
    }
}

/**
 * Returns the height of the navigation bar if the current device has a navigation bar, otherwise 0
 */
val Context?.navigationBarHeight: Int
    get() {
        this ?: return 0
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var height = 0
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }


val Context?.statusBarHeight: Int
    get() {
        this ?: return 0
        var result = 24
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        result = if (resId > 0) {
            resources.getDimensionPixelSize(resId)
        } else {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                result.toFloat(), Resources.getSystem().displayMetrics
            ).toInt()
        }
        return result
    }
