package com.sparrow.movieapp.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sparrow.movieapp.R
import com.sparrow.movieapp.model.Results
import com.sparrow.movieapp.ui.theme.black
import com.sparrow.movieapp.ui.theme.blackLight
import com.sparrow.movieapp.utilis.Util
import com.sparrow.movieapp.viewmodel.MovieViewModel
import java.math.RoundingMode

@Composable
fun HomeScreen(navigationCntr: NavHostController) {
    var viewModel = viewModel<MovieViewModel>()
    var state = viewModel.state

    Scaffold(
        topBar = {
            TopBar()
        },

        ) { it ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(black),
            contentPadding = PaddingValues(5.dp),
            columns = GridCells.Fixed(2),
        ) {
            itemsIndexed(state.movies) { index, it ->
                if (index >= state.movies.size - 1 && state.isEnd) {
                    viewModel.loadPage()
                }
                ItemUi(item = it, navigationCntr)
            }

            item(span = {
                GridItemSpan(2)
            }) {
                if (state.isLoading && !state.isEnd) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                if (!state.error.isNullOrEmpty()) {
                    Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemUi(item: Results, navigationCntr: NavHostController) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .shadow(15.dp)
            .clickable {

                navigationCntr.navigate("movie_detail/${item.id}")
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(5.dp), contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = Util.ImagePath + item.poster_path,

                placeholder = painterResource(id = R.drawable.baseline_video_library_24),
                contentDescription = "img",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            )
            Box(

                modifier = Modifier
                    .matchParentSize()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color(0xB91A1B1B)
                            )

                        ),

                        ),

                )
            Column(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(8.dp)
            ) {
                Text(
                    text = item.title,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.basicMarquee()
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "ic",
                        tint = Color.Yellow,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = item.vote_average.toBigDecimal().setScale(1, RoundingMode.HALF_DOWN)
                            .toString(), color = Color.White, fontSize = 12.sp
                    )
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "MovieDB") },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.clapperboard),
                contentDescription = "ic",
//                tint = Color.Transparent,
                modifier = Modifier.size(50.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
//            navigationIconContentColor = Color.Transparent
        )
    )
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomePreview() {
    HomeScreen(navigationCntr = rememberNavController())
}
