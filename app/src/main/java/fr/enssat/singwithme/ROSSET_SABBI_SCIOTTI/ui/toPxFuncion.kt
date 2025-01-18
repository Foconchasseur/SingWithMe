package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

//Convertie des float et int qui représentent des pixels en Dp
@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
