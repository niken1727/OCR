package com.niken.canvasocr

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.niken.canvasocr.customview.DoodleCanvas


class CanvasDialog: DialogFragment() {
    lateinit var listener: (Bitmap) -> Unit
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    fun onImageSaveListener(listener: (Bitmap) -> Unit) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.canvas_drawing_layout, container, false)
        val btnReset = view.findViewById<TextView>(R.id.reset)
        val btnSave = view.findViewById<TextView>(R.id.save)
        val doodleCanvas= view.findViewById<DoodleCanvas>(R.id.doodle_canvas)

        btnSave.setOnClickListener {
            val bitmapDrawable = doodleCanvas.getBitmap()
//            val uri = ImageUtils.saveBitmapAndGetUri(requireContext(),
//                bitmap = bitmapDrawable, "signature")
            listener(bitmapDrawable)
            dismiss()
        }
        btnReset.setOnClickListener {

            doodleCanvas.reset()
        }

        return view
    }
}