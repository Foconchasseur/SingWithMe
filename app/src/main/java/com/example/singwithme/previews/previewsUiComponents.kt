package com.example.singwithme.previews

import MockKaraokeViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.singwithme.data.models.ID
import com.example.singwithme.data.models.Song
import com.example.singwithme.previews.mockviewmodel.MockFilterViewModel
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.Cursor
import com.example.singwithme.ui.components.ErrorDisplay
import com.example.singwithme.ui.components.FilterText
import com.example.singwithme.viewmodel.MockErrorViewModel
import com.example.singwithme.ui.components.KaraokeSimpleText
import com.example.singwithme.ui.components.KaraokeSlider
import com.example.singwithme.ui.components.SongCard
import com.example.singwithme.ui.components.SongGridCard
import com.example.singwithme.ui.components.ThemeSelector

private val mockSongs = listOf(
    Song(ID("Wonderwall - Remastered", "Oasis"), true, "/", false),
    Song(ID("Don't Look Back in Anger - Remastered", "Oasis"), false, "DontLookBack/DontLookBack.md", false),
    Song(ID("Stand by Me", "Oasis"), true, "/",false),
    Song(ID("Bohemian Rhapsody", "Queen"), false, "Bohemian/Bohemian.md",true),
    Song(ID("Love Me Like There's No Tomorrow - Special Edition", "Freddie Mercury"), true, "/",false)
)
private val mockMutableSongs: MutableState<List<Song>> = mutableStateOf(mockSongs)
private val errorViewModel = MockErrorViewModel()
private val filterViewModel = MockFilterViewModel()
private val karaokeViewModel = MockKaraokeViewModel()

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    ActionButton(
        icon = Icons.Default.Home,
        contentDescription = "Pause",
        onClick = { /* Action preview */ }
    )
}

@Preview(showBackground = true)
@Composable
fun CursorPreview() {

    Cursor(
        modifier = Modifier,
        textPosition = Offset(0f,0f),
        textWidth= 0.dp,
        progress= 0f,
        fontsize= 25.dp
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorDisplayError() {
    ErrorDisplay(errorViewModel)
}

@Preview(showBackground = true, widthDp = 1500, heightDp = 100)
@Composable
fun FilterTextPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FilterText(
            filterViewModel = filterViewModel,
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            songList = mockMutableSongs
        )
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 400)
@Composable
fun KaraokeSimpleTextPreview(){//Soucis de fonctionnement du preview avec le systeme de coordonnées utilisé lors de recompositions
    KaraokeSimpleText(
        mainText = "texte principal",
        nextText = "",
        lastText = "",
        progress = 0.6f,
        modifier =  Modifier
    )
}

@Preview(showBackground = true, widthDp = 500, heightDp = 50)
@Composable
fun KaraokeSliderPreview(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        KaraokeSlider(Modifier.align(Alignment.TopCenter), karaokeViewModel, 20f)
    }
}

@Preview(showBackground = false, widthDp = 2000, heightDp = 150)
@Composable
fun SongCardLockedPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SongCard(
            song = mockSongs[0],
            downloadFilesSong = { _, _, _ -> },
            setPlayingTrue= { _ -> },
            deleteFiles = { _, _, _, _ -> },
            navController = rememberNavController(),
            errorViewModel = errorViewModel,
            songList = mockMutableSongs
        )
    }
}

@Preview(showBackground = false, widthDp = 2000, heightDp = 150)
@Composable
fun SongCardUnLockedPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SongCard(
            song = mockSongs[1],
            downloadFilesSong = { _, _, _ -> },
            setPlayingTrue= { _ -> },
            deleteFiles = { _, _, _, _ -> },
            navController = rememberNavController(),
            errorViewModel = errorViewModel,
            songList = mockMutableSongs
        )
    }
}
@Preview(showBackground = false, widthDp = 2000, heightDp = 150)
@Composable
fun SongCardDownloadedPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SongCard(
            song = mockSongs[3],
            downloadFilesSong = { _, _, _ -> },
            setPlayingTrue= { _ -> },
            deleteFiles = { _, _, _, _ -> },
            navController = rememberNavController(),
            errorViewModel = errorViewModel,
            songList = mockMutableSongs
        )
    }
}

@Preview(showBackground = true, widthDp = 2000, heightDp = 500)
@Composable
fun SongGridCardPreview(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SongGridCard(
            modifier = Modifier,
            downloadFilesSong = { _, _, _ -> },
            setPlayingTrue= { _ -> },
            deleteFiles = { _, _, _, _ -> },
            navController = rememberNavController(),
            errorViewModel = errorViewModel,
            songList = mockMutableSongs
        )
    }
}

@Preview(showBackground = true, widthDp = 1000, heightDp = 150)
@Composable
fun ThemeSelectorPreview(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ThemeSelector(
            currentThemeIndex=0,
            onThemeChanged = { _ -> }
        )
    }
}
