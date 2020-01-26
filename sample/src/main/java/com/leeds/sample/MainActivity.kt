package com.leeds.sample

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.leeds.lib.ui.viewstack.screen.ScreenStackManager
import com.leeds.sample.screen.StartScreen
import com.leeds.sample.screen.SubAScreen
import com.leeds.sample.screen.SubBScreen

class MainActivity : AppCompatActivity() {
    private val screenStackManager = ScreenStackManager()
    private var container: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
        setStart()
    }

    fun setStart() {
        screenStackManager.replace(container, StartScreen())
    }

    fun goSubA() {
        screenStackManager.add(container, SubAScreen())
    }

    fun goSubB() {
        screenStackManager.add(container, SubBScreen())
    }

    fun popStart() {
        screenStackManager.back(container, StartScreen())
    }

    fun pop() {
        screenStackManager.back(container)
    }
}
