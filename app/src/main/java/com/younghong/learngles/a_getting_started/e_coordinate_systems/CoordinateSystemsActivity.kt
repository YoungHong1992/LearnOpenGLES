package com.younghong.learngles.a_getting_started.e_coordinate_systems

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.R
import kotlinx.android.synthetic.main.activity_coordinate_systems.*

class CoordinateSystemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinate_systems)
        gl_surface_view.setEGLContextClientVersion(3)
        gl_surface_view.setRenderer(CoordinateSystemsRenderer())
    }
}
