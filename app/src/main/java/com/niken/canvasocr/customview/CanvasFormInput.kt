package com.niken.canvasocr.customview


import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.niken.canvasocr.CanvasDialog
import com.niken.canvasocr.MainActivity
import com.niken.canvasocr.R

class CanvasFormInput: FrameLayout {
    lateinit var titleView: TextView
    lateinit var canvasLayout: LinearLayout
    lateinit var imageView: ImageView
    lateinit var iconView: ImageView
    private lateinit var listener: (Bitmap) -> Unit

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet)
    }

    fun setListener(listener: (Bitmap) -> Unit) {
        this.listener = listener
    }


    private fun initView(context: Context, @Nullable attributeSet: AttributeSet?) {
        inflate(getContext(), R.layout.canvas_form_layout, this)
        titleView = findViewById(R.id.title_view)
        canvasLayout = findViewById(R.id.canvas_layout)
        imageView = findViewById(R.id.image_view)
        iconView = findViewById(R.id.icon)

        canvasLayout.setOnClickListener {
            val dialog = CanvasDialog()
            dialog.onImageSaveListener {
                Glide.with(context).load(it).into(imageView)
                listener(it)
                imageView.visibility = View.VISIBLE
                iconView.visibility = View.GONE
            }
            dialog.show((context as MainActivity).supportFragmentManager, "TAG")
        }

    }

    fun setLabel(label: String) {
        titleView.text = label
    }
}