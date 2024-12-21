
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.singwithme.data.models.Song
import com.example.singwithme.ui.components.SongGridCard

@Composable
fun MenuScreen(
    navController: NavController,
    playlist : List<Song>,
    downloadFunction: (String) -> Unit,
    setPlayingTrue: (Boolean) -> Unit,
    deleteFiles: (Context, String, String) -> Unit
) {

    SongGridCard(
        playlist = playlist,
        modifier = Modifier
            .fillMaxWidth(),
        downloadFunction = downloadFunction,
        setPlayingTrue = setPlayingTrue,
        deleteFiles = deleteFiles,
        navController = navController
    )
}
