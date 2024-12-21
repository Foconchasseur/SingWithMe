package com.example.singwithme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.singwithme.data.models.Song
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.KaraokeSimpleText

private val musicCardData = listOf(
    Song("Wonderwall - Remastered", "Oasis", true, "/", false),
    Song("Don't Look Back in Anger - Remastered", "Oasis", false, "DontLookBack/DontLookBack.md", false),
    Song("Stand by Me", "Oasis", true, "/",false),
    Song("Bohemian Rhapsody", "Queen", false, "Bohemian/Bohemian.md",true),
    Song("Love Me Like There's No Tomorrow - Special Edition", "Freddie Mercury", true, "/",false)
)
/*
@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
fun PlaybackScreenPreview() {
    PlaybackScreen(
        lyrics = listOf(),
        exoPlayer = null,
        onPauseClick = {},
        onRestartClick = {},
        onMenuClick = {},

    )
}
*/
/*
@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        navController =
        //onSongSelected = {}
    )
}
*/

/*
@Preview(showBackground = true)
@Composable
fun NavigationRail(){
    NavigationRail()
}
*/
/*
@Preview(showBackground = true)
@Composable
fun MusicGridCardPreview() {
    MusicGridCard()
}
*/
/*
@Preview(showBackground = true)
@Composable
fun MusicCardPreview() {
    SingWithMeTheme {
        SongCard(Song("Booba","B2O",true,"/",false))
    }
}
*/
@Preview(showBackground = true)
@Composable
fun KaraokeSimpleTextPreview() {
    KaraokeSimpleText("test", "test","test", 0.17F, modifier = Modifier.fillMaxWidth())
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