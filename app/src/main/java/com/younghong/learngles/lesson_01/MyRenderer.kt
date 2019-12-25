package com.younghong.learngles.lesson_01

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.younghong.learngles.GLUtil
import com.younghong.learngles.MyApplication
import com.younghong.learngles.R
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRenderer : GLSurfaceView.Renderer {

    private var programID = -1
    private var positionIndx = -1
    //每2个数组成一个点的坐标，总共3个坐标，对应三角形三个顶点
    private var points = floatArrayOf(
        0.0f, 0.5f,
        -0.5f, -0.5f,
        0.5f, -0.5f
    )
    private val pointsBuffer: FloatBuffer

    init {
        //gl绘制时需要buffer形式的数据，将points数组转变为buffer
        pointsBuffer = GLUtil.getFloatBuffer(points)
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        //创建shader
        val fragmentShaderID = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_FRAGMENT_SHADER,
            R.raw.lesson01_fragment_shader
        )
        val vertexShaderID = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_VERTEX_SHADER,
            R.raw.lesson01_vertex_shader
        )

        //创建program,绑定shader,链接、使用program,删除内存中的shader
        programID = GLES30.glCreateProgram()
        GLES30.glAttachShader(programID, vertexShaderID)
        GLES30.glAttachShader(programID, fragmentShaderID)
        GLES30.glLinkProgram(programID)
        GLES30.glUseProgram(programID)
        GLES30.glDeleteShader(vertexShaderID)
        GLES30.glDeleteShader(fragmentShaderID)
        //找到vertex中的坐标变量
        positionIndx = GLES30.glGetAttribLocation(programID, "aPosition")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        GLES30.glVertexAttribPointer(positionIndx, 2, GLES30.GL_FLOAT, false, 0, pointsBuffer)
        GLES30.glEnableVertexAttribArray(positionIndx)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 3)
        GLES30.glDisableVertexAttribArray(positionIndx)
    }


}