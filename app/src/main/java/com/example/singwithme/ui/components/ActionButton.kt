package com.example.singwithme.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ActionButton est un bouton personnnalisé utilisé à travers l'applications
 * @param icon : ImageVector, icone du bouton
 * @param contentDescription : String, description du bouton
 * @param onClick : () -> Unit, l'action effectué lors du click
 * @param modifier : Modifier, le modifier du bouton
 * @param size : Dp, la taille du bouton (48dp par défaut)
 *
 */
@Composable
fun ActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    IconButton(
        onClick = {onClick()},
        modifier = modifier
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(size)
        )
    }
}

