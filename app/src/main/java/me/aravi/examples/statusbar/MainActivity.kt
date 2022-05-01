package me.aravi.examples.statusbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.aravi.extensions.statusbar.immersive
import me.aravi.extensions.statusbar.statusBarColor
import me.aravi.extensions.statusbar.statusBarHeight

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}