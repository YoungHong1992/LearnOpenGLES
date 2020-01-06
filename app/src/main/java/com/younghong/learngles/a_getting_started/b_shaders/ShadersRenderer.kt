package com.younghong.learngles.a_getting_started.b_shaders

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.younghong.learngles.GLUtil
import com.younghong.learngles.MyApplication
import com.younghong.learngles.R
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ShadersRenderer : GLSurfaceView.Renderer {
    private var programID = -1
    private var vertexPosition = 0
    private var colorPosition = 1
    //三个顶点的数据，分别为xy坐标，rgb颜色
    private var vertices = floatArrayOf(
        0.0f, 0.5f, 1.0f, 0.0f, 0.0f,
        -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
        0.5f, -0.5f, 0.0f, 0.0f, 1.0f
    )
    private val verticesBuffer: FloatBuffer
    private var vbo = -1

    init {
        verticesBuffer = GLUtil.getFloatBuffer(vertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        var vertexShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_VERTEX_SHADER,
            R.raw.shaders_vertex_shader
        )
        var fragmentShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_FRAGMENT_SHADER,
            R.raw.shaders_fragment_shader
        )
        programID = GLES30.glCreateProgram()
        GLES30.glAttachShader(programID, vertexShader)
        GLES30.glAttachShader(programID, fragmentShader)
        GLES30.glLinkProgram(programID)
        GLES30.glUseProgram(programID)
        GLES30.glDeleteShader(vertexShader)
        GLES30.glDeleteShader(fragmentShader)


        //顶点数组放入缓冲区
        var buffers = IntArray(1)
        GLES30.glGenBuffers(1, buffers, 0)
        vbo = buffers[0]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER,
            4 * vertices.size,
            verticesBuffer,
            GLES30.GL_STATIC_DRAW
        )

        //开启顶点属性
        GLES30.glEnableVertexAttribArray(vertexPosition)
        GLES30.glEnableVertexAttribArray(colorPosition)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }


    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClearColor(0f, 0f, 0f, 1f)
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        GLES30.glVertexAttribPointer(vertexPosition, 2, GLES30.GL_FLOAT, false, 4 * 5, 0)
        GLES30.glVertexAttribPointer(colorPosition, 3, GLES30.GL_FLOAT, false, 4 * 5, 4 * 2)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 3)
    }


}