package com.example.galibaapp_semestralka

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.galibaapp_semestralka.navigation.Start
import com.example.galibaapp_semestralka.ui.theme.GalibaAppSemestralkaTheme

class  MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalibaAppSemestralkaTheme {

//                val viewModel = viewModel<CreateEventViewModel>()
//                val searchText by viewModel.searchText.collectAsState()
//                val mesta by viewModel.mesta.collectAsState()
//                val isSearching by viewModel.isSearching.collectAsState()
//
//                val filteredMesta = mesta.filter { viewModel.normalize(it.nazov).contains(searchText, ignoreCase = true) }.take(5)
//
//                Column(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    TextField(
//                        value = searchText,
//                        singleLine = true,
//                        onValueChange = viewModel::onSearchTextChange,
//                        modifier = Modifier.fillMaxWidth(),
//                        placeholder = {
//                            Text(text = "Search")
//                        },
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    if (isSearching && searchText.isNotEmpty()) {
//                        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
//                            items(filteredMesta) { mesto ->
//                                Text(
//                                    mesto.nazov,
//                                    modifier = Modifier.padding(10.dp) .clickable {
//                                        viewModel.onSearchTextChange(mesto.nazov)
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }






                Start()

            }
        }
    }
}