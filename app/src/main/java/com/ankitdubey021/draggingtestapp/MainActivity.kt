package com.ankitdubey021.draggingtestapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankitdubey021.draggingtestapp.ui.theme.DraggingTestAppTheme
import com.ankitdubey021.draggingtestapp.ui.theme.Purple200
import com.ankitdubey021.draggingtestapp.ui.theme.Purple700
import com.ankitdubey021.draggingtestapp.ui.theme.bgDark

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DraggingTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = bgDark, modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                                .background(bgDark),
                            contentAlignment = Alignment.Center
                        ){
                            val progress = 10
                            PieChart(70, progressColor  = Color.Green)
                        }
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                                .background(bgDark),
                            contentAlignment = Alignment.Center
                        ){
                            val progress = 50
                            DonutChart(progress)
                            Text(
                                text = "$progress%",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                                .background(bgDark),
                            contentAlignment = Alignment.Center
                        ){
                            val progress = 100
                            DonutChart(progress, strokeGap = 8, progressColor = Color.Yellow)
                            Text(
                                text = "$progress%",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DraggingTestAppTheme {
        CountDownTimer()
    }
}