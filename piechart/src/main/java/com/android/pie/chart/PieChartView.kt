package com.android.pie.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.jabama.android.chartview.R
import kotlin.math.abs

class PieChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attributeSet, defStyleAttr) {

    private val filledStyle = 1
    private val outlinedStyle = 2
    private var chartStyle = 0
    private var chartPadding = 5f
    private var chartItemsPadding = 0f
    private var chartStrokeWidth = 10f
    private val pieItems = ArrayList<PieItem>()
    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.PieChartView,
            defStyleAttr,
            0
        )
        try {
            chartPadding =
                typedArray.getDimension(R.styleable.PieChartView_chartPadding, 5f)
            chartItemsPadding =
                typedArray.getDimension(R.styleable.PieChartView_chartItemsPadding, 1f) / 2
            chartStrokeWidth =
                typedArray.getDimension(R.styleable.PieChartView_chartStrokeWidth, 20f)
            chartStyle = typedArray.getInt(R.styleable.PieChartView_chartStyle, 2)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //set style for draw pie items
        paint.style = Paint.Style.FILL
        paint.strokeWidth = chartStrokeWidth

        //calculate the total value of all pies
        val totalValues = pieItems.map { abs(it.value) }.sum()

        //set start angle for first pie
        var startAngle = 0f

        if (pieItems.size == 1) {
            paint.color = pieItems[0].color
            canvas?.drawArc(
                //draw oval margin
                getRectF(),
                //start draw oval
                startAngle,
                //end draw oval
                (pieItems[0].value / totalValues) * 360,
                true,
                paint
            )
        } else {
            //iterate over pie item and draw each one
            for (pie in pieItems) {
                if (pie.value == 0.0f) continue
                val chartItemPadding = if (pie.value == totalValues) 0.0f else chartItemsPadding

                val sweepAngle = (pie.value / totalValues) * 360

                //set color of the pie
                paint.color = pie.color

                //draw
                canvas?.drawArc(
                    //draw oval margin
                    getRectF(),
                    //start draw oval
                    startAngle + chartItemPadding,
                    //end draw oval
                    sweepAngle - chartItemPadding,
                    true,
                    paint
                )

                startAngle += sweepAngle
            }
        }

        //hide the central lines
        if (chartStyle == outlinedStyle) {
            paint.color = Color.WHITE
            canvas?.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                (width.toFloat() / 2) - getDp(chartStrokeWidth) - getDp(chartPadding),
                paint
            )
        }
    }

    fun setPieItems(items: List<PieItem>) {
        this.pieItems.clear()
        this.pieItems.addAll(items)
        invalidate()
    }

    fun addPieItem(item: PieItem) {
        this.pieItems.add(item)
        invalidate()
    }

    private fun getDp(value: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        context.resources.displayMetrics
    )

    private fun getPx(value: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        value,
        context.resources.displayMetrics
    )

    private fun getRectF(): RectF {
        val padding = getDp(chartPadding)
        return RectF(
            padding, padding, width - padding, height - padding
        )
    }

    //to make width and height of chart equal
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
