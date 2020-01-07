package com.younghong.learngles.a_getting_started.c_textures

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.R
import kotlinx.android.synthetic.main.activity_textures.*

class TexturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textures)
        gl_surface_view.setEGLContextClientVersion(3)
        gl_surface_view.setRenderer(TexturesRenderer())
    }
}
