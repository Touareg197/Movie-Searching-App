package ru.movie.searching.utils

import android.graphics.*
import com.squareup.picasso.Transformation

class ImageTransformation : Transformation {
    companion object {
        private val KEY = "rectangleImageTransformation"
    }

    override fun transform(source: Bitmap): Bitmap {

        // Init shader
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // Init paint
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.shader = shader

        // Create and draw circle bitmap
        val output = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(output)

        canvas.drawRoundRect(0F, 0F, source.width.toFloat() ,source.height.toFloat(), 60f, 60f, paint)

        source.recycle()

        return output
    }

    override fun key(): String = KEY
}