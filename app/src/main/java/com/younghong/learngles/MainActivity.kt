package com.younghong.learngles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.younghong.learngles.a_getting_started.a_hello_triangle.HelloTriangleActivity
import com.younghong.learngles.a_getting_started.b_shaders.ShadersActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lesson_1.setOnClickListener(this)
        lesson_2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lesson_1 -> startActivity<HelloTriangleActivity>()
            R.id.lesson_2 -> startActivity<ShadersActivity>()
        }
    }

    private inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity() {
        startActivity(Intent(this, T::class.java))
    }
}
