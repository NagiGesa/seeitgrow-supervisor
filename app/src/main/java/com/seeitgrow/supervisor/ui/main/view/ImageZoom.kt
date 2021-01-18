package com.seeitgrow.supervisor.ui.main.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

        binding.imageGlide.load(R.drawable.ronaldo)


//        Glide.with(this)
//            .load(R.drawable.ronaldo)
//            .into(object : CustomTarget<Drawable?>() {
//                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
//                    binding.imageGlide.setImageDrawable(resource)
//                }
//
//                override fun onLoadCleared(@Nullable placeholder: Drawable?) = Unit
//
//            })


    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }


}