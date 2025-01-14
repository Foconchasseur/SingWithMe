
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.singwithme.data.models.ID
import com.example.singwithme.data.models.Song
import com.example.singwithme.objects.Playlist
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.FilterText
import com.example.singwithme.ui.components.SongGridCard
import com.example.singwithme.ui.components.LaunchScreen
import com.example.singwithme.viewmodel.ErrorViewModel
import com.example.singwithme.viewmodel.FilterViewModel

@Composable
fun MenuScreen(
    navController: NavController,
    playlist : List<Song>,
    downloadFunction: (ID, ErrorViewModel, MutableState<List<Song>>) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, ID, MutableState<List<Song>>) -> Unit,
    quitApplication : () -> Unit,
    downloadPlaylist : (MutableState<List<Song>>) -> Unit,
    errorViewModel: ErrorViewModel,
    filterViewModel: FilterViewModel
) {
    val songList = remember { mutableStateOf(Playlist.songs.toList()) }
    Box(modifier = Modifier.fillMaxSize()) {
        if (Playlist.songs.isEmpty()) {
           LaunchScreen()
        } else {
            FilterText(filterViewModel, modifier = Modifier.align(Alignment.Center), songList = songList)
            SongGridCard(
                modifier = Modifier
                    .fillMaxWidth(),
                downloadFunction = downloadFunction,
                setPlayingTrue = setPlayingTrue,
                deleteFiles = deleteFiles,
                navController = navController,
                errorViewModel = errorViewModel,
                songList = songList
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .padding(top = 5.dp)
            ,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                icon = Icons.Default.Build,
                contentDescription = "getPlaylist",
                onClick = {downloadPlaylist(songList)}
            )
            ActionButton(
                icon = Icons.Default.Clear,
                contentDescription = "Pause",
                onClick = { quitApplication()}
            )
        }
    }
}
