package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song
import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.FilterViewModel

/**
 * FilterText est un composant permettant de chosir les options de filtrage des chansons
 * @param filterViewModel : FilterViewModel, le viewModel qui gère la logique de filtrage
 * @param modifier : Modifier, le modifier du composant
 * @param songList : MutableState<List<Song>>, la liste des chansons filtrées
 */

@Composable
fun FilterText(
    filterViewModel: FilterViewModel,
    modifier: Modifier,
    songList: MutableState<List<fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.models.Song>>
) {
    // On utilise le remember pour sauvegarder l'état des variables entre les recompositions
    var text by remember { mutableStateOf("") }
    var isCheckedDownloaded by remember { mutableStateOf(false) }
    var isCheckedUnlocked by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .weight(0.4f)
                .padding(horizontal = 8.dp)
        )  {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                value = text,
                onValueChange = {
                    text = it
                    songList.value = filterViewModel.setFilteredSongs(text, isCheckedUnlocked, isCheckedDownloaded)


                },
                label = { Text("Artiste ou titre") }
            )
        }
        Box(
            modifier = Modifier
                .weight(0.2f)
                .padding(horizontal = 8.dp)
        ){
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    text = ""
                    isCheckedDownloaded = false
                    isCheckedUnlocked = false
                    songList.value = filterViewModel.setFilteredSongs(text, isCheckedUnlocked, isCheckedDownloaded)
                }
            ){
                Text("Rénitialiser")
            }
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 8.dp)
        ) {
            Switch(
                checked = isCheckedDownloaded,
                onCheckedChange = {
                    isCheckedDownloaded = it
                    songList.value = filterViewModel.setFilteredSongs(text, isCheckedUnlocked, isCheckedDownloaded)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.DownloadDone,
                contentDescription = "Filter",
                modifier = Modifier
                    .size(24.dp)

            )
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 8.dp)
        ) {

            Switch(
                checked = isCheckedUnlocked,
                onCheckedChange = {
                    isCheckedUnlocked = it
                    songList.value = filterViewModel.setFilteredSongs(text, isCheckedUnlocked, isCheckedDownloaded)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.LockOpen,
                contentDescription = "Filter",
                modifier = Modifier
                    .size(24.dp)

            )
        }


    }

}