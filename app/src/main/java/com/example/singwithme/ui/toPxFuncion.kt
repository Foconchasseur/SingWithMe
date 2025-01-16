package com.example.singwithme.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
