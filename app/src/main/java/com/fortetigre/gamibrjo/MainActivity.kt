package com.fortetigre.gamibrjo

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fortetigre.gamibrjo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tvTest by lazy { binding.tvTest }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val paint = tvTest.paint
        val height = paint.measureText(tvTest.text.toString())
        val textShader: Shader = LinearGradient(50f, 0f, height, tvTest.textSize, intArrayOf(
            Color.parseColor("#F9AA2C"),
            Color.parseColor("#BC1F1C"),
        ), null, Shader.TileMode.REPEAT)

        tvTest.paint.setShader(textShader)
    }

}