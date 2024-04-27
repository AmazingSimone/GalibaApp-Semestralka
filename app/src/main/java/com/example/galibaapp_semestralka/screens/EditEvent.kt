package com.example.galibaapp_semestralka.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.galibaapp_semestralka.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditEvent() {
    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Uprav Galibu",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
                Icon(imageVector = Icons.Default.Close, contentDescription = "closeIcon")
            }

            var nazovAkcie by rememberSaveable { mutableStateOf("") }

            val (eventNameFocusRequester, eventDateFocusRequester, eventPlaceFocusRequester, eventBioFocusRequester) = remember { FocusRequester.createRefs() }

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventNameFocusRequester)
                    .fillMaxWidth(),
                value = nazovAkcie,
                onValueChange = { nazovAkcie = it },
                label = { Text("Nazov akcie") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { eventDateFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            var datumAkcie by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventDateFocusRequester)
                    .fillMaxWidth(),
                value = datumAkcie,
                onValueChange = { datumAkcie = it },
                label = { Text("Datum akcie") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { eventPlaceFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            var miestoAkcie by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventPlaceFocusRequester)
                    .fillMaxWidth(),
                value = miestoAkcie,
                onValueChange = { miestoAkcie = it },
                label = { Text("Miesto akcie") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { eventBioFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            Surface(

                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
                //.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.backonlabelpfp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))

            var popisAkcie by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventBioFocusRequester)
                    .fillMaxWidth(),
                value = popisAkcie,
                onValueChange = { popisAkcie = it },
                label = { Text("Popis akcie") },
                minLines = 10,
                maxLines = 10,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {  }
                )
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ulozit zmeny")
            }
        }
    }
}

@Composable
@Preview
fun PreviewEditEvent() {
    EditEvent()
}