package com.example.galibaapp_semestralka.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
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
import com.example.galibaapp_semestralka.navigation.Screens

@Composable
fun UserScreen(
    navController: NavController
) {
    Surface {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "iconClose")
                }
                IconButton(onClick = { navController.navigate(Screens.EDIT_USER_PROFILE.name)  }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "editProfileIcon")
                }



            }




            Spacer(modifier = Modifier.padding(all = 20.dp))

            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.empty_profile),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            Text(
                text = "Posluchac",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            Text(
                text = "Simone Bartanus",
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontWeight = FontWeight.Bold
            )

        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),

            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
                Button(onClick = { navController.navigate(Screens.LOGIN.name) }) {
                    Text(text = "Odhlasit sa")
                }
                Spacer(modifier = Modifier.padding(all = 10.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.padding(all = 5.dp))


                    Text(
                        text = "Šimon Bartánus 2024",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                    )


        }
    }
}

//@Composable
//@Preview
//fun PreviewUserSceen() {
//    UserScreen()
//}