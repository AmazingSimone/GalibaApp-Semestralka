package com.example.galibaapp_semestralka.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.ui.theme.primaryLight
import com.example.galibaapp_semestralka.ui.theme.surfaceLight


@Composable
fun FollowScreen() {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = surfaceLight
    ) {

        Column {

            Box {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                        Text(
                            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
                            text = "Sleduješ",
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        IconButton(
                            modifier = Modifier.padding(end = 20.dp),
                            onClick = { /*TODO*/ }) {

                            Icon(


                                imageVector = Icons.Default.Search,
                                contentDescription = "search artists",
                            )
                        }


                }
            }

            Box {

                Column (modifier = Modifier

                    .verticalScroll(rememberScrollState())){

                    CustomListItem(meno = "Back On Label", profilePic = R.drawable.backonlabelpfp)




                }
            }
        }

    }
    

}

@Composable
fun CustomListItem(
    meno: String,
    popis: String = "",
    @DrawableRes
    profilePic: Int
) {
    //TODO - spytaj sa jak mam spravit podmienku aby sa tam bud pridal alebo nepridal parameter
    Box (Modifier.clickable {  }) {

        ListItem(
            colors = ListItemDefaults.colors(surfaceLight),
            headlineContent = { Text(text =meno )  },
            supportingContent = { Text(text = popis)  },
            trailingContent = { val checkedState = remember { mutableStateOf(true) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                    colors = CheckboxDefaults.colors(primaryLight)

                ) },

            leadingContent = {
                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = profilePic),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        )
    }
    Divider()
}

@Composable
fun CustomListItemCard(
    meno: String,
    popis: String = "",
    @DrawableRes
    profilePic: Int
) {
    Card(

    ) {
        Text(
            text = meno,
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            textAlign = TextAlign.Center,
        )
    }
}


@Preview()
@Composable
fun FollowScreenPreview() {
    FollowScreen()
}