package com.example.assignment3

import Country
import CountryList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.ui.theme.Assignment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                CountryListScreen()
            }
        }
    }
}

@Composable
fun CountryListScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "countryList") {
        composable("countryList") { CountryListScreen(navController) }
        composable("countryDetail/{countryName}/{map}/{fact}") { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
            val mapRes = backStackEntry.arguments?.getString("map")?.toInt() ?: 0
            val fact = backStackEntry.arguments?.getString("fact") ?: ""
            CountryDetailScreen(navController, countryName, mapRes, fact)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Countries List") })
    }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(CountryList) { country ->
                CountryItem(country, navController)
            }
        }
    }
}

@Composable
fun CountryItem(country: Country, navController: NavController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("countryDetail/${country.name}/${country.map}/${country.fact}")
        }
        .padding(8.dp)) {
        Row {
            Image(painterResource(country.flag), null, modifier = Modifier.size(50.dp))
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = "Country: ${country.name}")
                Text(text = "Currency: ${country.currency}", fontSize = 11.sp)
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(navController: NavController, countryName: String, mapRes: Int, fact: String) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(countryName) }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Sharp.ArrowBack, contentDescription = null)
            }
        })
    }) { paddingValues ->
        Column (modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()){
            Image(painterResource(mapRes), null, modifier = Modifier
                .size(200.dp)
                .padding(horizontal = 10.dp))
            Column {
                Text(text = fact, fontSize = 16.sp, modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 10.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Assignment3Preview() {
    Assignment3Theme {
        CountryListScreen()
    }
}
