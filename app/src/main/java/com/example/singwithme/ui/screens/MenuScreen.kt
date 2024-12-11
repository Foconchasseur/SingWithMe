
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.singwithme.ui.components.MusicCard
import com.example.singwithme.ui.components.MusicGridCard

@Composable
fun MenuScreen(
    onSongSelected: (String) -> Unit,
){
    MusicGridCard(modifier = Modifier
        .fillMaxWidth())
}