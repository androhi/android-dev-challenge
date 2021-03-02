package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "puppyList") {
        composable("puppyList") {
            PuppyList(navController = navController, puppyList = createInitialData())
        }
        composable(
            "puppyDetail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) {
            PuppyDetail(navController, it.arguments?.getInt("index"))
        }
    }
}

@Composable
fun PuppyList(navController: NavController, puppyList: List<Puppy>, modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Foster parent recruitment") }
            )
        }
    ) {
        Surface(color = MaterialTheme.colors.background) {
            LazyColumn(state = scrollState, modifier = modifier) {
                itemsIndexed(puppyList) { index, puppy ->
                    PuppyCard(puppy) {
                        navController.navigate("puppyDetail/$index")
                    }
                }
            }
        }
    }
}

@Composable
fun PuppyCard(puppy: Puppy, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(16.dp)) {
        Image(
            painter = painterResource(id = puppy.imageResId),
            contentDescription = "puppy",
            modifier = Modifier
                .size(78.dp)
                .clip(RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            Text(puppy.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(puppy.title.orEmpty(), style = MaterialTheme.typography.body2)
                Text(puppy.message.orEmpty(), style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun PuppyDetail(navController: NavController, index: Int?) {
    val puppy = getPuppyDetail(index ?: 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Foster parent recruitment") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "back")
                }}
            )
        }
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = puppy.imageResId),
                    contentDescription = "puppy",
                    modifier = Modifier.padding(bottom = 16.dp).clip(RoundedCornerShape(8.dp))
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = puppy.name, style = MaterialTheme.typography.body1)
                    Text(text = puppy.message.orEmpty(), style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

private fun createInitialData(): List<Puppy> {
    return MutableList(4) { index ->
        var name: String? = null
        var imageResId: Int? = null
        var title: String? = null
        var message: String? = null

        when (index) {
            0 -> {
                name = "Taro"
                imageResId = R.drawable.puppy1
                title = "Japan"
                message = "Age 1"
            }
            1 -> {
                name = "Max"
                imageResId = R.drawable.puppy2
                title = "America"
                message = "Age 1"
            }
            2 -> {
                name = "Rocky"
                imageResId = R.drawable.puppy3
                title = "Germany"
                message = "Age 2"
            }
            3 -> {
                name = "Sam"
                imageResId = R.drawable.puppy4
                title = "Australia"
                message = "Age 1"
            }
            else -> {
                // nothing to do
            }
        }
        Puppy(
            name.orEmpty(),
            imageResId ?: 0,
            title,
            message
        )
    }
}

// FIXME: create the detail data
private fun getPuppyDetail(index: Int): Puppy {
    var name: String? = null
    var imageResId: Int? = null
    var title: String? = null
    var message: String? = null

    when (index) {
        0 -> {
            name = "Taro"
            imageResId = R.drawable.puppy1
            title = "Japan"
            message = "A really very cute puppy. Please let me know if you find it."
        }
        1 -> {
            name = "Max"
            imageResId = R.drawable.puppy2
            title = "America"
            message = "A self-paced puppy. Maybe I'm sleeping on the side of the road."
        }
        2 -> {
            name = "Rocky"
            imageResId = R.drawable.puppy3
            title = "Germany"
            message = "A very smart puppy, but often lost."
        }
        3 -> {
            name = "Sam"
            imageResId = R.drawable.puppy4
            title = "Australia"
            message = "Being a serious puppy, he may be looking for a home."
        }
        else -> {
            // nothing to do
        }
    }

    return Puppy(
        name.orEmpty(),
        imageResId ?: 0,
        title,
        message
    )
}

@Preview
@Composable
fun PuppyCardPreview() {
    MyTheme {
//        PuppyCard() {}
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
