package com.younghong.learngles.a_getting_started.a_hello_triangle

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.younghong.learngles.GLUtil
import com.younghong.learngles.MyApplication
import com.younghong.learngles.R
import java.nio.FloatBuffer
import java.nio.IntBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class HelloTriangleRenderer : GLSurfaceView.Renderer {

    private var programID = -1

    //顶点数据，分为三角形和矩形
    private var vertexLocation = 0
    private var vertices = floatArrayOf(
        0.0f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f
    )
    private var verticesRect = floatArrayOf(
        -0.5f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.5f, 0.5f, 0.0f
    )
    private var elementRect = intArrayOf(
        0, 1, 2,
        0, 2, 3
    )

    private val verticesBuffer: FloatBuffer
    private val verticesRectBuffer: FloatBuffer
    private val elementRectBuffer: IntBuffer

    private var vbo = -1
    private var vboRect = -1
    private var eboRect = -1

    private var vao = -1
    private var vaoRect = -1

    init {
        //gl绘制时需要buffer形式的数据，将points数组转变为buffer
        verticesBuffer = GLUtil.getFloatBuffer(vertices)
        verticesRectBuffer = GLUtil.getFloatBuffer(verticesRect)
        elementRectBuffer = GLUtil.getIntBuffer(elementRect)
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

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
        GLUtil.checkProgramError(programID)
        GLES30.glUseProgram(programID)
        GLES30.glDeleteShader(vertexShaderID)
        GLES30.glDeleteShader(fragmentShaderID)
        GLUtil.checkGLError()

        //创建vbo、ebo,并绑定对应的数据
        var buffers = IntArray(3)
        GLES30.glGenBuffers(3, buffers, 0)
        vbo = buffers[0]
        vboRect = buffers[1]
        eboRect = buffers[2]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER,
            4 * vertices.size,
            verticesBuffer,
            GLES30.GL_STATIC_DRAW
        )
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboRect)
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER,
            4 * verticesRect.size,
            verticesRectBuffer,
            GLES30.GL_STATIC_DRAW
        )
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, eboRect)
        GLES30.glBufferData(
            GLES30.GL_ELEMENT_ARRAY_BUFFER,
            4 * elementRect.size,
            elementRectBuffer,
            GLES30.GL_STATIC_DRAW
        )
        GLUtil.checkGLError()
        //创建vao,并绑定对应的操作
        val intArray = IntArray(2)
        GLES30.glGenVertexArrays(2, intArray, 0)
        vao = intArray[0]
        vaoRect = intArray[1]
        GLUtil.checkGLError()
        //绑定三角形操作流程
        GLES30.glBindVertexArray(vao)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
        GLES30.glVertexAttribPointer(vertexLocation, 3, GLES30.GL_FLOAT, false, 0, 0)
        GLES30.glEnableVertexAttribArray(vertexLocation)
        GLUtil.checkGLError()
        //绑定四边形操作流程
        GLES30.glBindVertexArray(vaoRect)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboRect)
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, eboRect)
        GLES30.glVertexAttribPointer(vertexLocation, 3, GLES30.GL_FLOAT, false, 0, 0)
        GLES30.glEnableVertexAttribArray(vertexLocation)
        GLUtil.checkGLError()
        //取消绑定
        GLES30.glBindVertexArray(0)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        if (!drawRect) {
            GLES30.glBindVertexArray(vao)
            GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 3)
        } else {
            GLES30.glBindVertexArray(vaoRect)
            GLES30.glDrawElements(GLES30.GL_TRIANGLES, elementRect.size, GLES30.GL_UNSIGNED_INT, 0)
        }

        GLUtil.checkGLError()
    }

    var drawRect = false
    fun setDrawModel(drawRect: Boolean) {
        this.drawRect = drawRect
    }

}