package com.ankitdubey021.draggingtestapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.ankitdubey021.draggingtestapp.ui.theme.secondaryProgressColor
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(percentProgress : Int, progressColor : Color = Color.Red){
    CircularChart(percentProgress = percentProgress, donut = false, progressColor = progressColor)
}

@Composable
fun DonutChart(percentProgress : Int, strokeGap : Int = 1, progressColor : Color = Color.Red){
    val gap = if(strokeGap < 1 || strokeGap > 10) 1 else strokeGap
    CircularChart(percentProgress = percentProgress, donut = true, strokeGap = gap, progressColor)
}


@Composable
private fun CircularChart(
    percentProgress : Int,
    donut : Boolean,
    strokeGap: Int = 1,
    progressColor: Color
) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val radianProgress = (360 * percentProgress) / 100
        val positivePoints  = 270 + radianProgress
        val extraPoint = positivePoints - 360
        for (i in 0 until 360) {
            if(donut) {
                DonutTickMark(
                    angle = i,
                    on = false,
                    progressColor = progressColor
                )
            }else{
                PieTickMark(angle = i, on = false,progressColor = progressColor)
            }
        }

        for (i in 270 until (if(positivePoints <= 360) positivePoints else 360) step strokeGap) {
            if(donut) {
                DonutTickMark(
                    angle = i,
                    on = true,
                    progressColor = progressColor
                )
            }else{
                PieTickMark(angle = i, on = true, progressColor = progressColor)
            }
        }
        if(extraPoint > 0){
            for (i in 0 until extraPoint step strokeGap) {
                if(donut) {
                    DonutTickMark(
                        angle = i,
                        on = true,
                        progressColor = progressColor
                    )
                }else{
                    PieTickMark(angle = i, on = true, progressColor = progressColor)
                }
            }
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
                    StrokeCap.Square
                )
            }
    )
}
