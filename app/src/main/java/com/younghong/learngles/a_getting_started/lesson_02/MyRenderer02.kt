package com.younghong.learngles.a_getting_started.lesson_02

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.younghong.learngles.GLUtil
import com.younghong.learngles.MyApplication
import com.younghong.learngles.R
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRenderer02 : GLSurfaceView.Renderer {
    private var programID = -1
    private var positionIndx = -1
    //每2个数组成一个点的坐标，总共3个坐标，对应三角形三个顶点
    private var points = floatArrayOf(
        0.0f, 0.5f,
        -0.5f, -0.5f,
        0.5f, -0.5f
    )
    private val pointsBuffer: FloatBuffer
    private var vertexBuffer: IntArray = IntArray(1)

    init {
        pointsBuffer = GLUtil.getFloatBuffer(points)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        var vertexShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_VERTEX_SHADER,
            R.raw.lesson02_vertex_shader
        )
        var fragmentShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_FRAGMENT_SHADER,
            R.raw.lesson02_fragment_shader
        )
        programID = GLES30.glCreateProgram()
        GLES30.glAttachShader(programID, vertexShader)
        GLES30.glAttachShader(programID, fragmentShader)
        GLES30.glLinkProgram(programID)
        GLES30.glUseProgram(programID)
        GLES30.glDeleteShader(vertexShader)
        GLES30.glDeleteShader(fragmentShader)

        positionIndx = GLES30.glGetAttribLocation(programID, "aPosition")
        GLES30.glEnableVertexAttribArray(positionIndx)

        //顶点数组放入缓冲区
        GLES30.glGenBuffers(1, vertexBuffer, 0)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vertexBuffer[0])
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER,
            4 * points.size,
            pointsBuffer,
            GLES30.GL_STATIC_DRAW
        )
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }


    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClearColor(0f, 0f, 0f, 1f)
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        GLES30.glVertexAttribPointer(positionIndx, 2, GLES30.GL_FLOAT, false, 2 * 4, 0)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 3)
    }


}