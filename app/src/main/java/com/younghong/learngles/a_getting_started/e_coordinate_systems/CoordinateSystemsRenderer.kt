package com.younghong.learngles.a_getting_started.e_coordinate_systems

import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.younghong.learngles.GLUtil
import com.younghong.learngles.MyApplication
import com.younghong.learngles.R
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class CoordinateSystemsRenderer : GLSurfaceView.Renderer {

    //用于计算屏幕比例，然后对图像进行投影变换
    private var aspectRatio = 1.0f
    private var projectionMatrix = FloatArray(16);

    private var programID = -1
    private var brickTexture = -1
    private var smileTexture = -1

    private val vertexPosition = 0
    private val texCoordPosition = 1
    private val modelMtxPosition = 2

    private val brickTexPosition = 0
    private val smileTexPosition = 1

    //立方体6个面顶点信息：xyz坐标+纹理xy坐标
    private val vertices = floatArrayOf(
        -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
        0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,

        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
        -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

        -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
        -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
    )
    private var verticesBuffer: FloatBuffer
    private var vbo = -1


    val modelMatrix = FloatArray(16)
    var angle = 0.0f
    //定义了10个cube的坐标
    var cubePositions = floatArrayOf(
        0.0f, 0.0f, -2.0f,
        2.0f, 5.0f, -15.0f,
        -1.5f, -2.2f, -2.5f,
        -3.8f, -2.0f, -12.3f,
        2.4f, -0.4f, -3.5f,
        -1.7f, 3.0f, -7.5f,
        1.3f, -2.0f, -2.5f,
        1.5f, 2.0f, -2.5f,
        1.5f, 0.2f, -1.5f,
        -1.3f, 1.0f, -1.5f
    )

    init {
        verticesBuffer = GLUtil.getFloatBuffer(vertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_VERTEX_SHADER,
            R.raw.coordinate_systems_vertex_shader
        )
        val fragmentShader = GLUtil.loadGLShader(
            MyApplication.appContext!!,
            GLES30.GL_FRAGMENT_SHADER,
            R.raw.coordinate_systems_fragment_shader
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

        //根据屏幕比例，设置投影矩阵。注意，把投影矩阵的near和far设置的间隔远一些，否则超出范围的内容不会显示
        Matrix.setIdentityM(projectionMatrix, 0)
        if (width > height) {
            aspectRatio = width.toFloat() / height
            Matrix.frustumM(
                projectionMatrix, 0, aspectRatio * -1.0f, aspectRatio * 1.0f, -1.0f, 1.0f, 0.8f, 20f
            )
        } else {
            aspectRatio = height.toFloat() / width
            Matrix.frustumM(
                projectionMatrix, 0, -1.0f, 1.0f, aspectRatio * -1.0f, aspectRatio * 1.0f, 0.8f, 20f
            )
        }


    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        //因为要渲染3D立方体，所以需要开启深度测试
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
        //开启深度测试后，每次绘制前，需要清空深度信息，也就是GL_DEPTH_BUFFER_BIT
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
        GLES30.glVertexAttribPointer(vertexPosition, 3, GLES30.GL_FLOAT, false, 4 * 5, 0)
        GLES30.glVertexAttribPointer(texCoordPosition, 2, GLES30.GL_FLOAT, false, 4 * 5, 4 * 3)
        GLUtil.checkGLError()

        //根据时间，进行旋转变换
        angle = if (angle > 360) 0f else angle + 1.0f
        for (i in 0..(cubePositions.size / 3 - 1)) {
            Matrix.setIdentityM(modelMatrix, 0)
            Matrix.translateM(
                modelMatrix,
                0,
                cubePositions[3 * i],
                cubePositions[3 * i + 1],
                cubePositions[3 * i + 2]
            )
            Matrix.rotateM(modelMatrix, 0, angle, 45.0f, 45.0f, 45.0f)
            //对modelMatrix做一下投影
            Matrix.multiplyMM(modelMatrix, 0, projectionMatrix, 0, modelMatrix, 0)

            GLES30.glUniformMatrix4fv(modelMtxPosition, 1, false, modelMatrix, 0)
            GLUtil.checkGLError()
            //激活纹理单元、绑定纹理到该单元、指定采样器对应的纹理单元
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, brickTexture)
            GLES30.glUniform1i(brickTexPosition, 0)

            GLES30.glActiveTexture(GLES30.GL_TEXTURE1)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, smileTexture)
            GLES30.glUniform1i(smileTexPosition, 1)

            //绘制
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 36)
            GLUtil.checkGLError()
        }
    }
}