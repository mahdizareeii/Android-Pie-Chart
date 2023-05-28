package com.example.piechart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.pie.chart.PieChartView
import com.android.pie.chart.PieItem

class RvAdapter : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    class RvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(view: View) {
            view.findViewById<PieChartView>(R.id.chart_view).apply {
                setPieItems(
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        return RvViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_chart, parent, false)
        )
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(holder.itemView)
    }
}