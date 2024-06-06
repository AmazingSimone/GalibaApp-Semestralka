package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.galibaapp_semestralka.data.CreateEvent.CreateEventViewModel
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.data.Search.SearchCityViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditEvent(navController: NavHostController, createEventViewModel: CreateEventViewModel = viewModel(), firebaseViewModel: FirebaseViewModel, searchCityViewModel: SearchCityViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        searchCityViewModel._searchText.value = firebaseViewModel.chosenEvent.value?.city?.nazov ?: ""
    }

    Surface (
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val chosenEvent by firebaseViewModel.chosenEvent.observeAsState()

            var nazovAkcie by rememberSaveable { mutableStateOf("") }
            //var datumAkcie by rememberSaveable { mutableStateOf("") }
//            var eventDateChanged by rememberSaveable {
//                mutableStateOf(LocalDate.MIN)
//            }

            var eventDateChanged by remember { mutableStateOf(chosenEvent?.dateAndTime?.toLocalDate()) }

            var eventTimeChanged by remember { mutableStateOf(chosenEvent?.dateAndTime?.toLocalTime()) }


            //var eventTimeChanged: LocalTime? by rememberSaveable { mutableStateOf(null) }


            var datumACasAkcieChanged: LocalDateTime? by rememberSaveable { mutableStateOf(null) }

            val selectedMesto by searchCityViewModel.selectedMesto.collectAsState()

            val searchText by searchCityViewModel.searchText.collectAsState()

            val mesta by searchCityViewModel.mestaForEditCreateEvent.collectAsState()

            var eventLocation by rememberSaveable { mutableStateOf("") }

            //var eventDetailsChanged by rememberSaveable { mutableStateOf("") }


            var showDialogCancelCreate by remember { mutableStateOf(false) }

            val calendarState = rememberUseCaseState()

            val clockState = rememberUseCaseState()


            var eventNameChanged by remember { mutableStateOf(chosenEvent?.eventName) }

            var eventLocationChanged by remember { mutableStateOf(chosenEvent?.location) }
            var eventDetailsChanged by remember { mutableStateOf(chosenEvent?.eventDetails.toString()) }

            var eventCityChanged by remember { mutableStateOf(chosenEvent?.city) }



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
                    text = "Uprav Galibu \uD83E\uDE9B",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
                IconButton(
                    onClick = {
                        if ((eventNameChanged?.isNotEmpty() ?: false && eventNameChanged != chosenEvent?.eventName) ||
                            (eventDateChanged != chosenEvent?.dateAndTime?.toLocalDate() && eventDateChanged?.isBefore(LocalDate.now()) == false) ||
                            (searchCityViewModel.selectedMesto.value != null && searchCityViewModel.selectedMesto.value != chosenEvent?.city) ||
                            (eventLocationChanged != chosenEvent?.location && eventLocationChanged?.isNotEmpty() ?: false ||
                                    eventDetailsChanged != chosenEvent?.eventDetails
                                    )) {
                            showDialogCancelCreate = true
                        } else {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "closeIcon")
                }
            }


            val (eventNameFocusRequester, eventDateFocusRequester, eventCityFocusRequester, eventPlaceFocusRequester, eventBioFocusRequester) = remember { FocusRequester.createRefs() }



            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventNameFocusRequester)
                    .fillMaxWidth(),
                value = eventNameChanged.toString(),
                onValueChange = { eventNameChanged = it },
                label = { Text("Nazov akcie") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { eventCityFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))


            //val eventDateChanged = remember { mutableStateOf(chosenEvent?.dateAndTime?.toLocalDate()) }

            CalendarDialog(

                state = calendarState,
                selection = CalendarSelection.Date {
                    Log.d("Date", "$it")
                    eventDateChanged = it
                },
                config = CalendarConfig(
                    locale = Locale("sk")
                )

            )

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventDateFocusRequester)
                    .fillMaxWidth(),
                value = (if (eventDateChanged != LocalDate.MIN) {
                    eventDateChanged?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                } else {
                    ""
                }).toString(),
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

            Spacer(modifier = Modifier.padding(all = 10.dp))


            ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes{
                    hours, minutes ->

                //datumACasAkcie = LocalDateTime.of(datumAkcie,casAkcie)
                //Log.d("CasAkcie" , "stary cas $casAkcie")
                //Log.d("CasAkcie" , "stary datum a cas $datumACasAkcie")
                eventTimeChanged = LocalTime.of(hours, minutes)


                datumACasAkcieChanged = LocalDateTime.of(eventDateChanged,eventTimeChanged)
                //datumACasAkcie?.format(DateTimeFormatter.ofPattern("DD/MM/uuuu HH:mm",Locale("sk")))
                Log.d("CasAkcie" , "novy cas $eventTimeChanged")
                Log.d("CasAkcie" , "datum a cas $datumACasAkcieChanged")
            })


            OutlinedTextField(
                modifier = Modifier
                    //    .focusRequester(eventDateFocusRequester)
                    .fillMaxWidth(),
                value = if (eventTimeChanged != null) eventTimeChanged.toString() else "",
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    IconButton(onClick = { clockState.show() }) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "time icon"
                        )
                    }
                },
                placeholder = { Text("Zaciatok akcie") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { eventCityFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            //val eventCityChanged by remember { mutableStateOf(chosenEvent?.city?.nazov) }
            //val eventCityChanged = chosenEvent?.city?.nazov
            //val defaultCity : String = eventCityChanged.toString()
            //Log.d("firebaseviewmodel" ,"city: ${defaultCity}")
            //searchText = eventCityChanged.toString()
            //searchCityViewModel.defaultText = eventCityChanged.toString()
            //searchCityViewModel.onSearchTextChange(eventCityChanged.toString())
            //searchCityViewModel.onSearchTextChange(eventCityChanged.toString())
            //searchCityViewModel._searchText.value = chosenEvent?.city?.nazov.toString()
            //searchCityViewModel.setDefaultText(firebaseViewModel)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(searchCityViewModel.dynamicSize.value)
                    .focusRequester(eventCityFocusRequester)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                    //.weight(1f)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = searchText,
                        singleLine = true,
                        onValueChange = searchCityViewModel::onSearchTextChange,
                        label = {
                            Text(text = "Vyhladaj mesto")
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { eventPlaceFocusRequester.requestFocus() }
                        )
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(mesta) { mesto ->
                            Text(
                                mesto.nazov,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .clickable {
                                        searchCityViewModel.chooseMesto(mesto)
                                        eventCityChanged = searchCityViewModel.selectedMesto.value
                                        Log.d(
                                            "mestoAkcie",
                                            "${searchCityViewModel.selectedMesto.value}"
                                        )
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventPlaceFocusRequester)
                    .fillMaxWidth(),
                value = eventLocationChanged.toString(),
                onValueChange = { eventLocationChanged = it },
                label = { Text("Adresa") },
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

            var selectedImageUri by remember {
                mutableStateOf<Uri?>(null)
            }

            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    selectedImageUri = it
                }
            )

            Surface(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(350.dp)
                ) {
                    AsyncImage(
                        model =selectedImageUri,
                        contentDescription =null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color(0x80000000))
                    )

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))


            val localFocusManager = LocalFocusManager.current


            val maxCharPopis = 600

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(eventBioFocusRequester)
                    .fillMaxWidth(),
                value = eventDetailsChanged,
                onValueChange = {
                    if (it.length <= maxCharPopis) {
                        eventDetailsChanged = it
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
                ),
                isError = eventDetailsChanged.length == maxCharPopis
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            val context = LocalContext.current


            var showDialogConfirmDelete by remember { mutableStateOf(false) }
            if (showDialogConfirmDelete) {
                AlertDialog(
                    onDismissRequest = { showDialogConfirmDelete = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialogConfirmDelete = false

                                val onSuccess = {
                                    navController.popBackStack()
                                    Toast.makeText(
                                        context,
                                        "Galiba bola vymazana !",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                val onFailure: () -> Unit = {
                                    Toast.makeText(context, "Nastala chyba", Toast.LENGTH_LONG)
                                        .show()
                                }
                                firebaseViewModel.deleteEvent(onSuccess,onFailure,chosenEvent?.eventId.toString())

                            },
                            //colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                        ) {
                            Text("Potvrdit",
                                color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialogConfirmDelete = false },

                            ) {
                            Text("Zrušiť")
                        }
                    },
                    title = { Text("Chystas sa vymazat Galibu") },
                    text = { Text("Si si naozaj isty ?") }
                )
            }

            OutlinedButton(
                onClick = { showDialogConfirmDelete = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Vymazat galibu",
                    color = MaterialTheme.colorScheme.error
                )
            }

            var showDialogConfirmCreate by remember { mutableStateOf(false) }
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
                                //val updatedEvent = Event(searchCityViewModel.selectedMesto.value, datumACasAkcieChanged, eventDetailsChanged,eventNameChanged,eventLocationChanged)
                                val onSuccess = {
                                    navController.popBackStack()
                                    Toast.makeText(
                                        context,
                                        "Galiba bola upravena !",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                val onFailure: () -> Unit = {
                                    Toast.makeText(context, "Nastala chyba", Toast.LENGTH_LONG)
                                        .show()
                                }
                                firebaseViewModel.updateEventInfo(
                                    onSuccess,
                                    onFailure,
                                    eventId = chosenEvent?.eventId.toString(),
                                    city = eventCityChanged,
                                    dateAndTime = datumACasAkcieChanged,
                                    eventDetails = eventDetailsChanged,
                                    eventName = eventNameChanged,
                                    location = eventLocationChanged
                                )

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
                    title = { Text("Chystas sa upravit Galibu") },
                    text = { Text("Su vsetky udaje spravne ?") }
                )
            }

            Button(
                onClick = {
                    if (eventTimeChanged == chosenEvent?.dateAndTime?.toLocalTime()) {
                        datumACasAkcieChanged = LocalDateTime.of(eventDateChanged, chosenEvent?.dateAndTime?.toLocalTime())
                    }
                    showDialogConfirmCreate = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = (eventNameChanged?.isNotEmpty() ?: false && eventNameChanged != chosenEvent?.eventName) ||
                          (eventDateChanged != chosenEvent?.dateAndTime?.toLocalDate() && eventDateChanged?.isBefore(LocalDate.now()) == false) ||
                           (searchCityViewModel.selectedMesto.value != null && searchCityViewModel.selectedMesto.value != chosenEvent?.city) ||
                            (eventLocationChanged != chosenEvent?.location && eventLocationChanged?.isNotEmpty() ?: false ||
                            eventDetailsChanged != chosenEvent?.eventDetails)


//                enabled = eventNameChanged?.isNotEmpty() ?: false && (eventDateChanged != LocalDate.MIN && (eventDateChanged?.isAfter(
//                    LocalDate.now()) == false || eventDateChanged?.isEqual(LocalDate.now()) == false)) && selectedMesto != null && eventLocation.isNotEmpty()
            ) {
                Text(text = "Upravit galibu")
            }

        }
    }
}

//@Composable
//@Preview
//fun PreviewEditEvent() {
//    EditEvent(navController)
//}