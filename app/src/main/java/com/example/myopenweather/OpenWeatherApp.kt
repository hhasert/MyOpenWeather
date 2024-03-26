package com.example.myopenweather

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myopenweather.ui.navigation.OpenWeatherNavHost
import com.example.myopenweather.ui.viewmodel.OpenWeatherViewModel

//private const val TAG = "MyOpenWeather"
/**
 * enum values that represent the screens in the app
 */
enum class MyOpenWeatherScreen(@StringRes val title: Int) {
    RequestPermissions(title = R.string.requestpermissions),
    Location(title = R.string.app_name),
    Weather(title = R.string.weather),
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenWeatherTopAppBar(
    currentScreen: MyOpenWeatherScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateMenu: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier)
{
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = navigateMenu) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OpenWeatherApp( navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MyOpenWeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: MyOpenWeatherScreen.Location.name
    )
    val openWeatherViewModel: OpenWeatherViewModel =
        viewModel(factory = OpenWeatherViewModel.Factory)
    Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { OpenWeatherTopAppBar(
            currentScreen = currentScreen,
     //       canNavigateBack = navController.previousBackStackEntry != null,
            canNavigateBack = false,
            navigateUp = { navController.navigateUp() },
            navigateMenu = {navController.navigate(MyOpenWeatherScreen.Location.name)},
            scrollBehavior = scrollBehavior) }
    ) {
        OpenWeatherNavHost(navController, openWeatherViewModel, Modifier.padding(it))
    }
}
