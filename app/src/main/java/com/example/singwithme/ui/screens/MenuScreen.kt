
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.singwithme.back.cache.Song
import com.example.singwithme.ui.components.MusicGridCard
import com.example.singwithme.viewmodel.MusicViewModel

@Composable
fun MenuScreen(navController: NavController ,playlist : List<Song>, downloadFunction: (String) -> Unit,isPlayingTrue: (Boolean) -> Unit,) {

    MusicGridCard(
        playlist,
        modifier = Modifier
        .fillMaxWidth(),
        downloadFunction,
        isPlayingTrue,
        navController = navController
    )
}
