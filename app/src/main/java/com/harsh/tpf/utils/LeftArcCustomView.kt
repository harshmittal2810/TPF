package com.harsh.tpf.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.RelativeLayout

class LeftArcCustomView(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) :
    RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context) :
            this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet) :
            this(context, attrs, 0, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            this(context, attrs, defStyleAttr, 0) {
        init()

    }

    private fun init() {
        pArc = object : Paint() {
            init {
                color = Color.parseColor("#666666")
                style = Paint.Style.FILL
            }
        }
    }

    private var rect1 = RectF(0f, 0f, 0f, 0f)
    private val radius = 25

    private var pArc: Paint? = null


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect1 = RectF(0f, 0f, w.toFloat(), h.toFloat())
        pArc = object : Paint() {
            init {
                val l = LinearGradient(
                    0f,
                    0f,
                    w.toFloat(),
                    h.toFloat(),
                    Color.parseColor("#666666"),
                    Color.parseColor("#666666"),
                    Shader.TileMode.MIRROR
                )
                shader = l
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val w = width
        val h = height

        val rArc = RectF(
            (-w + w * 20 / 100).toFloat(),
            0f,
            (w + w * 20 / 100).toFloat(),
            (3 * h).toFloat()
        )
        canvas.drawArc(rArc, 0f, 360f, false, pArc!!)

        super.dispatchDraw(canvas)

    }
}