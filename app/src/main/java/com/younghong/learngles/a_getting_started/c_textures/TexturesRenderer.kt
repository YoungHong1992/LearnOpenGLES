package com.younghong.learngles.a_getting_started.c_textures

import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.younghong.learngles.GLUtil
import com.younghong.learngles.MyApplication
import com.younghong.learngles.R
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TexturesRenderer : GLSurfaceView.Renderer {

    private var programID = -1
    private var brickTexture = -1
    private var smileTexture = -1

    private val vertexPosition = 0
    private val texCoordPosition = 1
    private val brickTexPosition = 0
    private val smileTexPosition = 1

    //矩形顶点信息：xyz坐标+纹理xy坐标
    private val vertices = floatArrayOf(
        -0.5f, 0.5f, 0.0f, 0.0f, 0.0f,
        -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
        0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
        0.5f, -0.5f, 0.0f, 1.0f, 1.0f
    )
    private var verticesBuffer: FloatBuffer
    private var vbo = -1


    init {
        verticesBuffer = GLUtil.getFloatBuffer(vertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_VERTEX_SHADER,
            R.raw.textures_vertex_shader
        )
        val fragmentShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_FRAGMENT_SHADER,
            R.raw.textures_fragment_shader
        )
        programID = GLES30.glCreateProgram()
        GLES30.glAttachShader(programID, vertexShader)
        GLES30.glAttachShader(programID, fragmentShader)
        GLES30.glLinkProgram(programID)
        GLES30.glUseProgram(programID)
        GLES30.glDeleteShader(vertexShader)
        GLES30.glDeleteShader(fragmentShader)
        GLUtil.checkGLError()

        //数组放入缓冲区
        val buffers = IntArray(1)
        GLES30.glGenBuffers(1, buffers, 0)
        vbo = buffers[0]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER,
            4 * vertices.size,
            verticesBuffer,
            GLES30.GL_STATIC_DRAW
        )
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
        GLUtil.checkGLError()

        //生成纹理
        val texBuffers = IntArray(2)
        GLES30.glGenTextures(2, texBuffers, 0)
        brickTexture = texBuffers[0]
        smileTexture = texBuffers[1]
        val brickBitmap = BitmapFactory.decodeResource(
            MyApplication.appContext!!.resources,
            R.drawable.texture_brick
        )
        val smileBitmap = BitmapFactory.decodeResource(
            MyApplication.appContext!!.resources,
            R.drawable.texture_smile
        )
        GLUtil.checkGLError()

        GLUtil.bindTextures(brickTexture, brickBitmap)
        GLUtil.bindTextures(smileTexture, smileBitmap)
        brickBitmap.recycle()//生成纹理后，即可销毁
        smileBitmap.recycle()
        //激活
        GLES30.glEnableVertexAttribArray(vertexPosition)
        GLES30.glEnableVertexAttribArray(texCoordPosition)
        GLUtil.checkGLError()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
        GLUtil.checkGLError()
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
        GLES30.glVertexAttribPointer(vertexPosition, 3, GLES30.GL_FLOAT, false, 4 * 5, 0)
        GLES30.glVertexAttribPointer(texCoordPosition, 2, GLES30.GL_FLOAT, false, 4 * 5, 4 * 3)

        //激活纹理单元、绑定纹理到该单元、指定采样器对应的纹理单元
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, brickTexture)
        GLES30.glUniform1i(brickTexPosition, 0)

        GLES30.glActiveTexture(GLES30.GL_TEXTURE1)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, smileTexture)
        GLES30.glUniform1i(smileTexPosition, 1)

        //绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4)
        GLUtil.checkGLError()
    }


}