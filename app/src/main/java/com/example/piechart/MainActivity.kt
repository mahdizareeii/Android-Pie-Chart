package com.example.piechart

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.pie.chart.PieChartView
import com.android.pie.chart.PieItem

class MainActivity : AppCompatActivity() {

    private var chart: PieChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chart = findViewById(R.id.pie_chart_view)
        chart?.setPieItems(
            listOf(
                PieItem(
                    50f,
                    Color.YELLOW
                ),
                PieItem(
                    30f,
                    Color.BLUE
                ),
                PieItem(
                    20f,
                    Color.BLACK
                ),
                PieItem(
                    90f,
                    Color.RED
                )
            )
        )
    }
}