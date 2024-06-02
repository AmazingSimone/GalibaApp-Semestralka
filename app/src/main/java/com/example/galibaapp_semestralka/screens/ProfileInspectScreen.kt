package com.example.galibaapp_semestralka.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.screens.HomeScreen.CustomCard

@Composable
fun ProfileInspectScreen(
    firebaseViewModel: FirebaseViewModel,
    navController: NavController,
    id: String // hned na zaciatku si vypita id aby ho naisel v databaze a nasledne to vytvori screen z nacitanych udajov
) {

    Surface {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(all = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "iconClose")
                    }

                }

                ProfileInfo(
                    R.drawable.backonlabelpfp,
                    isArtist = true,
                    name = "Back On Label",
                    "celkom nás to všetko baví \uD83D\uDC97\n" +
                            ".\n" +
                            "24.5. - bastriguli! underground v @totojevyklad ✨\n" +
                            "28.6. - Project Banska 3 v @klub_77 ❌\n" +
                            "4.7. - Kvíz v @zahradacnk \uD83E\uDDE0"
                )


            }

            Column(
                //verticalArrangement = Arrangement.spacedBy(26.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.padding(all = 10.dp))

                CustomCard(
                    firebaseViewModel = firebaseViewModel,
                    navController,
                    image = R.drawable.backtooldschoolposter,
                    title = "Back To Oldschool",
                    location = "Klub 77",
                    city = "Banska Bystrica",
                    cityPrefix = "BB",
                    date = "6.5.2023",
                    time = "19:00",
                    text = "Throwback party pre mladých? Aj to je koncept. BackToOldschool sa vracia vo svojej siedmej edícií\uD83D\uDD7A\n" +
                            ".\n" +
                            "Hráme šialené mashupy trackov z rokov 60’ až 00’. Ako spolu znie Sara Perche Ti Amo a Du Hast? Alebo Rihanna a Meki Žbirka? ",
                    author = "Back On Label",
                    profilePic = R.drawable.backonlabelpfp
                )
            }
        }
    }
}

@Composable
fun ProfileInfo(
    @DrawableRes profilePic: Int = R.drawable.empty_profile,
    isArtist: Boolean,
    name: String,
    bio: String = ""

) {

    Image(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape),
        painter = painterResource(id = profilePic),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
    Spacer(modifier = Modifier.padding(all = 10.dp))

    Text(
        text = "Umelec/Kapela",
        fontSize = MaterialTheme.typography.bodyLarge.fontSize
    )

    Spacer(modifier = Modifier.padding(all = 10.dp))

    Text(
        text = name,
        fontSize = MaterialTheme.typography.displayMedium.fontSize,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.padding(all = 10.dp))

    Text(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        text = bio,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize
    )
    Spacer(modifier = Modifier.padding(all = 10.dp))

    HorizontalDivider()
}

//@Composable
//@Preview
//fun PreviewProfileInspectScreen() {
//    ProfileInspectScreen("id")
//}

