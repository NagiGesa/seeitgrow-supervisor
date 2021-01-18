package com.seeitgrow.supervisor.ui.main.view

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.databinding.ImageZoomBinding
import kotlin.math.max
import kotlin.math.min

class ImageZoom : AppCompatActivity() {
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private lateinit var imageView: ImageView
    lateinit var binding: ImageZoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ImageZoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.load(R.drawable.ronaldo)

        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            binding.imageView.scaleX = scaleFactor
            binding.imageView.scaleY = scaleFactor
            return true
        }
    }
}