package com.younghong.learngles.a_getting_started.b_shaders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.R
import kotlinx.android.synthetic.main.activity_shaders.*

class ShadersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shaders)
        gl_surface_view.setEGLContextClientVersion(3)
        gl_surface_view.setRenderer(ShadersRenderer())
    }
}
