package com.harsh.tpf.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

class TopRightArcCustomView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context) :
            this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet) :
            this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            this(context, attrs, defStyleAttr, 0)

    private var rect1 = RectF(0f, 0f, 0f, 0f)
    private val radius = 30

    private var pArc: Paint? = null
    private var pText: Paint? = null
    private var pText2: Paint? = null

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
        pText = object : Paint() {
            init {
                color = Color.rgb(255, 255, 255)
                textAlign = Paint.Align.LEFT
                textSize = 80f
            }
        }
        pText2 = object : Paint() {
            init {
                color = Color.rgb(255, 255, 255)
                textAlign = Paint.Align.LEFT
                textSize = 60f
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val w = width
        val h = height

        val rArc = RectF(
            (-w + w * 25 / 100).toFloat(),
            (-3 * h).toFloat(),
            (w * 95 / 100).toFloat(),
            h.toFloat()
        )
        canvas.drawArc(rArc, 0f, 300f, false, pArc!!)

        if (pText != null) {
            canvas.drawText("THINGS", 80f, 130f, pText!!)
        }
        if (pText2 != null) {
            canvas.drawText("The App", 80f, 220f, pText2!!)
        }
        super.dispatchDraw(canvas)
    }
}