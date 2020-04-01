package com.younghong.learngles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.a_getting_started.a_hello_triangle.HelloTriangleActivity
import com.younghong.learngles.a_getting_started.b_shaders.ShadersActivity
import com.younghong.learngles.a_getting_started.c_textures.TexturesActivity
import com.younghong.learngles.a_getting_started.d_transformations.TransformationsActivity
import com.younghong.learngles.a_getting_started.e_coordinate_systems.CoordinateSystemsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lesson_1.setOnClickListener(this)
        lesson_2.setOnClickListener(this)
        lesson_3.setOnClickListener(this)
        lesson_4.setOnClickListener(this)
        lesson_5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lesson_1 -> startActivity<HelloTriangleActivity>()
            R.id.lesson_2 -> startActivity<ShadersActivity>()
            R.id.lesson_3 -> startActivity<TexturesActivity>()
            R.id.lesson_4 -> startActivity<TransformationsActivity>()
            R.id.lesson_5 -> startActivity<CoordinateSystemsActivity>()
        }
    }

    private inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity() {
        startActivity(Intent(this, T::class.java))
    }
}
