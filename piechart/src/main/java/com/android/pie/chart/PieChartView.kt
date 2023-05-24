package com.android.pie.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class PieChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var chartPadding = 20f
    private var chartItemsPadding = 2f
    private var chartStrokeWidth = 8f
    private val pieItems = ArrayList<PieItem>()
    private val paint = Paint()

    init {
        //TODO get from attribute
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //set style for draw pie items
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = getDp(chartStrokeWidth)

        //calculate the total value of all pies
        var totalValues = 0f
        for (pie in pieItems) {
            totalValues += pie.value.takeIf { it >= 0 } ?: (pie.value * -1)
        }

        //set start angle for first pie
        var startAngle = 0f

        //iterate over pie item and draw each one
        for (pie in pieItems) {
            val sweepAngle = (pie.value / totalValues) * 360

            //set color of the pie
            paint.color = pie.color

            //draw
            canvas?.drawArc(
                //oval
                getRecF(),
                //startAngle
                startAngle + getDp(chartItemsPadding),
                //sweepAngle
                sweepAngle - getDp(chartItemsPadding),
                //useCenter
                true,
                //paint
                paint
            )

            startAngle += sweepAngle
        }


        //set style for hide the central lines
        paint.style = Paint.Style.FILL

        //hide the central lines
        paint.color = Color.WHITE
        canvas?.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width.toFloat() / 2) - getPx(chartStrokeWidth) - getPx(getDp(chartPadding)),
            paint
        )
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

    private fun getRecF(): RectF {
        val padding = getDp(chartPadding)
        return RectF(
            padding, padding, width - padding, height - padding
        )
    }

    //to make width and size of chart equal
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}