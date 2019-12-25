package com.younghong.learngles

import android.content.Context
import android.opengl.GLES30
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class GLUtil {
    companion object {

        /**
         * 加载指定类型的shader
         *
         * type: shader类型, 分为GL_VERTEX_SHADER、GL_FRAGMENT_SHADER
         * resId: shader源文件id, 该工程中, 放置在 res/raw文件夹下面
         *
         * reture: 加载成功返回对应shader的id, 否则抛出异常
         */
        fun loadGLShader(context: Context, type: Int, resId: Int): Int {
            val code: String = readRawTextFile(context, resId)!!
            var shader = GLES30.glCreateShader(type)
            GLES30.glShaderSource(shader, code)
            GLES30.glCompileShader(shader)
            //检查shader是否错误
            val compileStatus = IntArray(1)
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compileStatus, 0)
            if (compileStatus[0] == 0) {
                Log.e("ShaderUtil", "Error compiling shader: " + GLES30.glGetShaderInfoLog(shader))
                GLES30.glDeleteShader(shader)
                shader = 0
            }
            if (shader == 0) {
                throw RuntimeException("Error creating shader.")
            }
            return shader
        }

        /**
         * 将FloatArray数据转为FloatBuffer
         */
        fun getFloatBuffer(array: FloatArray): FloatBuffer {
            var byteBuffer = ByteBuffer.allocateDirect(4 * array.size)
            byteBuffer.order(ByteOrder.nativeOrder())
            var floatBuffer = byteBuffer.asFloatBuffer()
            floatBuffer!!.put(array)
            floatBuffer!!.position(0)
            return floatBuffer
        }

        /**
         * 获取资源文件中存储的字符串
         *
         * @param resId 存储Shader的资源文件所对应的resource id
         * @return 返回文件中存储的字符串, 异常时返回null
         */
        private fun readRawTextFile(context: Context, resId: Int): String? {
            val inputStream = context.resources.openRawResource(resId)
            try {
                val reader = BufferedReader(InputStreamReader(inputStream))
                val sb = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line).append("\n")
                }
                reader.close()
                return sb.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }


}