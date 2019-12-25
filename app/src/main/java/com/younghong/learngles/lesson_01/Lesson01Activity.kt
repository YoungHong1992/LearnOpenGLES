package com.younghong.learngles.lesson_01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.R
import kotlinx.android.synthetic.main.activity_lesson01.*

class Lesson01Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson01)
        //设置GLSurfaceView, 后续的所有显示操作，都交由MyRenderer处理
        gl_surface_view.setEGLContextClientVersion(3)
        gl_surface_view.setRenderer(MyRenderer())
    }
}
