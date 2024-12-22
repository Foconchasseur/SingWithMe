
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.singwithme.data.models.Song
import com.example.singwithme.ui.components.ActionButton
import com.example.singwithme.ui.components.SongGridCard

@Composable
fun MenuScreen(
    navController: NavController,
    playlist : List<Song>,
    downloadFunction: (String) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, String) -> Unit,
    quitApplication : () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        SongGridCard(
            playlist = playlist,
            modifier = Modifier
                .fillMaxWidth(),
            downloadFunction = downloadFunction,
            setPlayingTrue = setPlayingTrue,
            deleteFiles = deleteFiles,
            navController = navController
        )
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
                onClick = {

                }
            )
            ActionButton(
                icon = Icons.Default.Clear,
                contentDescription = "Pause",
                onClick = { quitApplication()}
            )
        }

    }
}
