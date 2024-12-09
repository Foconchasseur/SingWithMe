package com.example.singwithme

import MenuScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.singwithme.data.models.Music
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.example.singwithme.ui.components.MusicCard
import com.example.singwithme.ui.components.MusicGridCard
import com.example.singwithme.ui.screens.PlaybackScreen
import com.example.singwithme.ui.theme.SingWithMeTheme

@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
fun PlaybackScreenPreview() {
    PlaybackScreen(
        lyricsPath = "Here are the lyrics of the song scrolling across the screen...",
        onPauseClick = {},
        onRestartClick = {},
        onMenuClick = {},

    )
}

@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        onSongSelected = {}
    )
}
/*
@Preview(showBackground = true)
@Composable
fun NavigationRail(){
    NavigationRail()
}
*/
@Preview(showBackground = true)
@Composable
fun MusicGridCardPreview() {
    MusicGridCard()
}
@Preview(showBackground = true)
@Composable
fun MusicCardPreview() {
    SingWithMeTheme {
        MusicCard(Music("Booba","B2O","/",true))
    }
}

@Preview(showBackground = true)
@Composable
fun KaraokeSimpleTextPreview() {
    KaraokeSimpleText("test",0.17F, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    ActionButton(
        icon = Icons.Default.Home,
        tint = Color.Black,
        contentDescription = "Pause",
        onClick = { /* Action preview */ }
    )
}