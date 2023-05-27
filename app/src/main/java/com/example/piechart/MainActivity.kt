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
                    8500000f,
                    Color.YELLOW
                ),
                PieItem(
                    954000f,
                    Color.BLUE
                ),
                PieItem(
                    1000000f,
                    Color.BLACK
                ),
                PieItem(
                    540000f,
                    Color.RED
                )
            )
        )
    }
}