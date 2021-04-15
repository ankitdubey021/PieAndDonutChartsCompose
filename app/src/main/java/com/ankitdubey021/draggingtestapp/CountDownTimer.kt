package com.ankitdubey021.draggingtestapp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import com.ankitdubey021.draggingtestapp.ui.theme.bgDark
import com.ankitdubey021.draggingtestapp.ui.theme.bgLight
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/*
data class WheelPosition(
    val start : Offset, val current : Offset
){
    val delta : Offset get()  = current-start
    val theta = delta.theta
}
operator fun WheelPosition?.plus(rhs : Offset) : WheelPosition = this?.copy( current = current + rhs) ?: WheelPosition(current = rhs, start = rhs)
*/

val Offset.theta: Float get() = (atan2(y.toDouble(), x.toDouble()) * 180.0 / PI).toFloat()

@Composable
fun CountDownTimer() {

    var position by remember { mutableStateOf<Offset?>(null) }
    var origin by remember { mutableStateOf<Offset>(Offset.Zero) }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(listOf( bgLight,  bgDark))
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, amount ->
                            position = position?.plus(amount) ?: amount
                            change.consumeAllChanges()
                        },
                        onDragStart = { offset ->
                            position = offset - origin
                        },
                        onDragEnd = {
                            position = null
                        },
                        onDragCancel = {
                            position = null
                        },

                        )
                }
                .onSizeChanged { origin = it.center.toOffset() }
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
            /*.background(Color.DarkGray,shape = CircleShape)
            .drawBehind {
                drawCircle(Color.Gray, center = size.center, radius = 20f)
            }*/
        ) {

            Text(
                "01:50",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 48.sp,
                )
            )
            val theta = position?.theta ?: 0f
            for (i in 0 until 40) {
                val angle = i*9
                TickMark(
                    angle = angle,
                    on = angle < theta
                )
            }
        }
    }
}

const val startRadiusFraction = 0.5f
const val endRadiusFraction = 0.75f
const val tickWidth = 5f

@Composable
fun TickMark(
    angle: Int,
    on: Boolean
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
                    if (on) Color.Red else Color.White.copy(alpha =  0.1f),
                    center + startPos,
                    center + endPos,
                    tickWidth,
                    StrokeCap.Round
                )
            }
    )
}