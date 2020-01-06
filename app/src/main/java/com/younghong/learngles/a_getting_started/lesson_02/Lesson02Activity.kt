package com.younghong.learngles.a_getting_started.lesson_02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.R
import kotlinx.android.synthetic.main.activity_lesson02.*

class Lesson02Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson02)
        gl_surface_view.setEGLContextClientVersion(3)
        gl_surface_view.setRenderer(MyRenderer02())
    }
}
