package com.example.galibaapp_semestralka.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.CreateEventViewModel
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateEvent(navController: NavHostController, createEventViewModel: CreateEventViewModel = viewModel(), firebaseViewModel: FirebaseViewModel) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            var nazovAkcie by rememberSaveable { mutableStateOf("") }
            //var datumAkcie by rememberSaveable { mutableStateOf("") }
            var datumAkcie by rememberSaveable {
                mutableStateOf(LocalDate.MIN)
            }
            var mesto by rememberSaveable { mutableStateOf("") }

            val najdiMesto by createEventViewModel.searchText.collectAsState()

            val mesta by createEventViewModel.mesta.collectAsState()

            val isSearching by createEventViewModel.isSearching.collectAsState()

            var miestoAkcie by rememberSaveable { mutableStateOf("") }

            var popisAkcie by rememberSaveable { mutableStateOf("") }




            var showDialogCancelCreate by remember { mutableStateOf(false) }

            val calendarState = rememberUseCaseState()


            if (showDialogCancelCreate) {
                AlertDialog(
                    onDismissRequest = { showDialogCancelCreate = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialogCancelCreate = false
//                                navController.navigate(Screens.AUTHROOT.name) {
//                                    popUpTo(Screens.HOME.name) { inclusive = true }
//                                }
                                navController.popBackStack()
                            }
                        ) {
                            Text("Potvrdit")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showDialogCancelCreate = false },

                            ) {
                            Text("Zrušiť")
                        }
                    },
                    title = { Text("Vsetky vykonane zmeny budu stratene") },
                    text = { Text("Si si isty ?") }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vytvor Galibu",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
                IconButton(
                    onClick = {
                        if (nazovAkcie.isNotEmpty() || miestoAkcie.isNotEmpty() || datumAkcie != LocalDate.MIN) {
                            showDialogCancelCreate = true
                        } else {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "closeIcon")
                }
            }


            val (eventNameFocusRequester, eventDateFocusRequester,eventCityFocusRequester, eventPlaceFocusRequester, eventBioFocusRequester) = remember { FocusRequester.createRefs() }

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

            CalendarDialog(

                state = calendarState,
                selection = CalendarSelection.Date {
                    Log.d("Date", "$it")
                    datumAkcie = it
                },
                config = CalendarConfig(
                    locale = Locale("sk")
                )

            )

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventDateFocusRequester)
                    .fillMaxWidth(),
                value = if (datumAkcie != LocalDate.MIN) {
                    datumAkcie.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                } else {
                    ""
                },
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    IconButton(onClick = { calendarState.show() }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "callendar icon"
                        )
                    }
                },

                placeholder = { Text("Datum akcie") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { eventCityFocusRequester.requestFocus() }
                )
            )
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(eventCityFocusRequester)
                        .fillMaxWidth(),
                    value = najdiMesto,
                    onValueChange = createEventViewModel::onSearchTextChange,
                    label = { Text("Najdi mesto") },
                    placeholder = { Text("Najdi mesto") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { eventPlaceFocusRequester.requestFocus() }
                    )
                )
                LazyColumn() {
                    items(mesta) { mesto ->
                        Text(text = mesto.nazov)
                    }
                }
            }


            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventPlaceFocusRequester)
                    .fillMaxWidth(),
                value = miestoAkcie,
                onValueChange = { miestoAkcie = it },
                label = { Text("Miesto akcie") },
                placeholder = { Text("Nazov klubu alebo adresa") },
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
            val localFocusManager = LocalFocusManager.current


            val maxCharPopis = 600

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventBioFocusRequester)
                    .fillMaxWidth(),
                value = popisAkcie,
                onValueChange = {
                    if (it.length <= maxCharPopis) {
                        popisAkcie = it
                    }
                },
                label = { Text("Popis akcie") },
                minLines = 5,
                maxLines = 20,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.clearFocus() }
                )
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            var showDialogConfirmCreate by remember { mutableStateOf(false) }
            val context = LocalContext.current

            if (showDialogConfirmCreate) {
                AlertDialog(
                    onDismissRequest = { showDialogConfirmCreate = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialogConfirmCreate = false
//                                navController.navigate(Screens.AUTHROOT.name) {
//                                    popUpTo(Screens.HOME.name) { inclusive = true }
//                                }

                                val onSuccess = {
                                    navController.popBackStack()
                                    Toast.makeText(context, "Galiba bola vytvorena !", Toast.LENGTH_LONG).show()
                                }

                                val onFailure: () -> Unit = {
                                    Toast.makeText(context, "Nastala chyba", Toast.LENGTH_LONG).show()
                                }
                                firebaseViewModel.createEvent(onSuccess, onFailure, nazovAkcie, miestoAkcie,datumAkcie,popisAkcie)

                            }
                        ) {
                            Text("Potvrdit")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showDialogConfirmCreate = false },

                            ) {
                            Text("Zrušiť")
                        }
                    },
                    title = { Text("Chystas sa vytvorit Galibu") },
                    text = { Text("Su vsetky udaje spravne ?") }
                )
            }

            Button(
                onClick = {
                    showDialogConfirmCreate = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nazovAkcie.isNotEmpty() && datumAkcie != LocalDate.MIN && miestoAkcie.isNotEmpty()
            ) {
                Text(text = "Vytvorit galibu")
            }
        }
    }
}

//@Composable
//@Preview
//fun CreateEventPreview() {
//    CreateEvent(navController)
//}