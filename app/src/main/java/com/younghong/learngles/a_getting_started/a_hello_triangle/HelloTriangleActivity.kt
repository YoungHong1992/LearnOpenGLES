package com.younghong.learngles.a_getting_started.a_hello_triangle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.R
import kotlinx.android.synthetic.main.activity_hello_triangle.*

/**
 * 仅绘制一个红色三角形
 */
class HelloTriangleActivity : AppCompatActivity() {
    private val myRender = HelloTriangleRenderer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_triangle)
        //设置GLSurfaceView, 后续的所有显示操作，都交由MyRenderer处理

        gl_surface_view.setEGLContextClientVersion(3)
        gl_surface_view.setRenderer(myRender)
        draw_switch.setOnCheckedChangeListener { switchView, isChecked ->
            myRender.setDrawModel(isChecked)
        }
    }
}
