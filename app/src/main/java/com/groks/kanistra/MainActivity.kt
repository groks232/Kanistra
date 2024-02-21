package com.groks.kanistra

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.Coil
import coil.ImageLoader
import com.groks.kanistra.feature.domain.util.initUntrustImageLoader
import com.groks.kanistra.feature.presentation.main.NavHostEntry
import com.groks.kanistra.ui.theme.KanistraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanistraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHostEntry()

                    //Ёбаный костыль чтобы скачивать картинки
                    //с домена с самоподписаннами сертификатами.
                    //Хуй его знает куда это деть поэтому оставлю пока тут.
                    /*TODO("Move somewhere")*/
                    val context = LocalContext.current
                    val untrustImageLoader: ImageLoader = initUntrustImageLoader(context)
                    Coil.setImageLoader(untrustImageLoader)
                }
            }
        }
    }
}