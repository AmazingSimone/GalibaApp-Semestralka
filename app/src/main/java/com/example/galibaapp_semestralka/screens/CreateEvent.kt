package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.data.Search.SearchCityViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockPopup
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
fun CreateEvent(
    navController: NavHostController,
    searchCityViewModel: SearchCityViewModel = viewModel(),
    firebaseViewModel: FirebaseViewModel
) {
    var isUploading by rememberSaveable { mutableStateOf(false) }

    Box {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
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

                var casAkcie: LocalTime? by rememberSaveable { mutableStateOf(null) }


                var datumACasAkcie: LocalDateTime? by rememberSaveable { mutableStateOf(null) }

                val selectedMesto by searchCityViewModel.selectedMesto.collectAsState()

                val searchText by searchCityViewModel.searchText.collectAsState()

                val mesta by searchCityViewModel.mestaForEditCreateEvent.collectAsState()

                var miestoAkcie by rememberSaveable { mutableStateOf("") }

                var popisAkcie by rememberSaveable { mutableStateOf("") }


                var showDialogCancelCreate by rememberSaveable { mutableStateOf(false) }

                val calendarState = rememberUseCaseState()

                val clockState = rememberUseCaseState()


                if (showDialogCancelCreate) {
                    AlertDialog(
                        onDismissRequest = { showDialogCancelCreate = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDialogCancelCreate = false
//                                navController.navigate(Screens.AUTHROOT.name) {
//                                    popUpTo(Screens.HOME.name) { inclusive = true }
//                                }
                                    navController.popBackStack()
                                }
                            ) {
                                Text(stringResource(R.string.button_confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialogCancelCreate = false },

                                ) {
                                Text(stringResource(R.string.button_cancel))
                            }
                        },
                        title = { Text(stringResource(R.string.alert_all_changes_will_be_lost)) },
                        text = { Text(stringResource(R.string.alert_are_you_sure)) }
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
                        text = stringResource(R.string.title_create_event),
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize
                    )
                    IconButton(
                        onClick = {
                            if (nazovAkcie.isNotEmpty() || (datumAkcie != LocalDate.MIN) || selectedMesto != null || miestoAkcie.isNotEmpty()) {
                                showDialogCancelCreate = true
                            } else {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "closeIcon")
                    }
                }

                BackHandler {
                    if (nazovAkcie.isNotEmpty() || (datumAkcie != LocalDate.MIN) || selectedMesto != null || miestoAkcie.isNotEmpty()) {
                        showDialogCancelCreate = true
                    } else {
                        navController.popBackStack()
                    }
                }

                val (eventNameFocusRequester,
                    eventDateFocusRequester,
                    eventCityFocusRequester,
                    eventPlaceFocusRequester,
                    eventBioFocusRequester) = remember { FocusRequester.createRefs() }

                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(eventNameFocusRequester)
                        .fillMaxWidth(),
                    value = nazovAkcie,
                    onValueChange = { nazovAkcie = it },
                    label = { Text(stringResource(R.string.text_event_name)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { eventCityFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.padding(all = 10.dp))

                CalendarDialog(

                    state = calendarState,
                    selection = CalendarSelection.Date {
                        datumAkcie = it
                    },
                    config = CalendarConfig(
                        locale = Locale(Locale.getDefault().toLanguageTag())
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
                    placeholder = { Text(stringResource(R.string.text_event_date)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { eventCityFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.padding(all = 10.dp))


                ClockPopup(
                    state = clockState,
                    selection = ClockSelection.HoursMinutes { hours, minutes ->

                        //datumACasAkcie = LocalDateTime.of(datumAkcie,casAkcie)
                        //Log.d("CasAkcie" , "stary cas $casAkcie")
                        //Log.d("CasAkcie" , "stary datum a cas $datumACasAkcie")
                        casAkcie = LocalTime.of(hours, minutes)


                        datumACasAkcie = LocalDateTime.of(datumAkcie, casAkcie)
                        //datumACasAkcie?.format(DateTimeFormatter.ofPattern("DD/MM/uuuu HH:mm",Locale("sk")))

                    })


                OutlinedTextField(
                    modifier = Modifier
                        //    .focusRequester(eventDateFocusRequester)
                        .fillMaxWidth(),
                    value = if (casAkcie != null) casAkcie.toString() else "",
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
                    placeholder = { Text(stringResource(R.string.text_event_time)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { eventCityFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.padding(all = 10.dp))


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
                                Text(text = stringResource(R.string.text_search_event_city))
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
                    value = miestoAkcie,
                    onValueChange = { miestoAkcie = it },
                    label = { Text(stringResource(R.string.text_event_address)) },
                    placeholder = { Text(stringResource(R.string.text_name_or_address_of_event)) },
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
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )


                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color(0x80000000))
                        )

                        Icon(
                            imageVector = Icons.Default.Upload,
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
                    value = popisAkcie,
                    onValueChange = {
                        if (it.length <= maxCharPopis) {
                            popisAkcie = it
                        }
                    },
                    label = { Text(stringResource(R.string.text_event_bio)) },
                    minLines = 5,
                    maxLines = 20,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.clearFocus() }
                    ),
                    isError = popisAkcie.length == maxCharPopis
                )
                Spacer(modifier = Modifier.padding(all = 10.dp))

                var showDialogConfirmCreate by rememberSaveable { mutableStateOf(false) }
                val context = LocalContext.current


                if (showDialogConfirmCreate) {
                    AlertDialog(
                        onDismissRequest = { showDialogConfirmCreate = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDialogConfirmCreate = false
                                    isUploading = true

                                    val onSuccess = {
                                        isUploading = false
                                        navController.popBackStack()
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.toast_event_was_created),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    val onFailure: () -> Unit = {
                                        isUploading = false
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.toast_there_was_error),
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                    firebaseViewModel.createEvent(
                                        onSuccess,
                                        onFailure,
                                        nazovAkcie,
                                        selectedImageUri,
                                        miestoAkcie,
                                        datumACasAkcie,
                                        selectedMesto,
                                        popisAkcie
                                    )

                                }
                            ) {
                                Text(stringResource(R.string.button_confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialogConfirmCreate = false },

                                ) {
                                Text(stringResource(R.string.button_cancel))
                            }
                        },
                        title = { Text(stringResource(R.string.alert_you_are_about_to_create_event)) },
                        text = { Text(stringResource(R.string.alert_is_everything_correct)) }
                    )
                }

                Button(
                    onClick = {
                        if (casAkcie == null) {
//                        casAkcie = LocalTime.of(19, 0)
                            datumACasAkcie = LocalDateTime.of(datumAkcie, LocalTime.of(19, 0))
                        }
                        showDialogConfirmCreate = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = nazovAkcie.isNotEmpty() && (datumAkcie != LocalDate.MIN && (datumAkcie.isAfter(
                        LocalDate.now()
                    ) ||
                            datumAkcie.isEqual(LocalDate.now()))) && selectedMesto != null && miestoAkcie.isNotEmpty()
                ) {
                    Text(text = stringResource(R.string.confirmButton_create_event))
                }

            }
        }
        if ( isUploading ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

//@Composable
//@Preview
//fun CreateEventPreview() {
//    CreateEvent(navController)
//}