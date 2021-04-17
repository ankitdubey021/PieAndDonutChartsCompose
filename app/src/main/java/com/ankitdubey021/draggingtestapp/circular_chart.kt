package com.ankitdubey021.draggingtestapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import com.ankitdubey021.draggingtestapp.ui.theme.Purple200
import com.ankitdubey021.draggingtestapp.ui.theme.secondaryProgressColor
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(dataSet: List<DataSet>){
    CircularChart(dataSet, false)
}

@Composable
fun DonutChart(dataSet: List<DataSet>, strokeGap : Int = 1){
    val gap = if(strokeGap < 1 || strokeGap > 10) 1 else strokeGap
    CircularChart(dataSet, donut = true, strokeGap = gap)
}


@Composable
private fun CircularChart(
    dataSet: List<DataSet>,
    donut : Boolean,
    strokeGap: Int = 1
) {
    Box(
        Modifier
            .rotate(270f)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        //fill with progress background color
        for (i in 0 until 360) {
            if(donut) {
                DonutTickMark(
                    angle = i,
                    on = false,
                    progressColor = secondaryProgressColor
                )
            }else{
                PieTickMark(angle = i, on = false,progressColor = secondaryProgressColor)
            }
        }
        var previousPercent = 0
        var startingIndex = 0
        //Now fill with progress
        for(ds in dataSet){
            previousPercent += ds.percent.toInt()
            val radianProgress = ((360 * previousPercent) / 100)

            for (i in startingIndex until radianProgress step strokeGap) {
                if(donut) {
                    DonutTickMark(
                        angle = i,
                        on = true,
                        progressColor = ds.color
                    )
                }else{
                    PieTickMark(angle = i, on = true,progressColor = ds.color)
                }
            }
            startingIndex = radianProgress
        }

    }
}

@Composable
private fun DonutTickMark(
    angle: Int,
    on: Boolean,
    progressColor: Color
) {
    Box(
        Modifier
            .fillMaxSize()
            .drawBehind {
                val theta = angle * PI.toFloat() / 180f
                val startRadius = size.width / 2 * startRadiusFraction
                val endRadius = size.width / 2 * endRadiusFraction

                /*val sweep = Brush.sweepGradient(progressColor)
                val offBrush = SolidColor(secondaryProgressColor)*/

                val startPos = Offset(
                    cos(theta) * startRadius,
                    sin(theta) * startRadius
                )

                val endPos = Offset(
                    cos(theta) * endRadius,
                    sin(theta) * endRadius
                )
                drawLine(
                    if (on) progressColor else secondaryProgressColor,
                    center + startPos,
                    center + endPos,
                    tickWidth,
                    StrokeCap.Round
                )
            }
    )
}

@Composable
private fun PieTickMark(
    angle: Int,
    on: Boolean,
    progressColor: Color
) {
    Box(
        Modifier
            .fillMaxSize()
            .drawBehind {
                val theta = angle * PI.toFloat() / 180f
                val endRadius = size.width / 2 * endRadiusFraction

                /*val sweep = Brush.sweepGradient(progressColor)
                val offBrush = SolidColor(secondaryProgressColor)*/

                val startPos = Offset(
                    cos(theta) ,
                    sin(theta)
                )

                val endPos = Offset(
                    cos(theta) * endRadius,
                    sin(theta) * endRadius
                )
                drawLine(
                    if (on) progressColor else secondaryProgressColor,
                    center + startPos,
                    center + endPos,
                    tickWidth,
                    StrokeCap.Round
                )
            }
    )
}