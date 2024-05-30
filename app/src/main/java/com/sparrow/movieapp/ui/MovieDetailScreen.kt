package com.sparrow.movieapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sparrow.movieapp.R
import com.sparrow.movieapp.model.MovieDetail
import com.sparrow.movieapp.model.ProductionCompany
import com.sparrow.movieapp.ui.theme.black
import com.sparrow.movieapp.ui.theme.blackLight
import com.sparrow.movieapp.utilis.Util
import com.sparrow.movieapp.viewmodel.MovieViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailScreen(id: Int) {
    var viewModel = viewModel<MovieViewModel>()
    viewModel.id = id
    viewModel.loading = true
    viewModel.getDetails()
    var state = viewModel.state
    var detail = state.detail

    var scroll = rememberScrollState()

    if (state.error!!.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = R.drawable.baseline_error_24),
                contentDescription = "error"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = state.error!!, color = Color.White)
        }
    } else if (!state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(color = Color.White)
        }
    } else {
        Column(modifier = Modifier.verticalScroll(state = scroll)) {
            Box(

                contentAlignment = Alignment.TopCenter
            ) {
                BackGroundImage(img = Util.ImagePath + detail.poster_path)
                ForeGroundImage(
                    img = Util.ImagePath + detail.poster_path,
                    title = detail.original_title
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 10.dp, start = 10.dp)
                ) {
                    Text(
                        text = detail.original_title,
                        maxLines = 1,
                        fontSize = 22.sp,
//                textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier

                            .basicMarquee()

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.actor),
                            contentDescription = "ic",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = detail.genres.joinToString { it.name },
                            color = Color.White,
                            fontSize = 12.sp

                        )
                    }

                }


            }
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier

                    .padding(bottom = 50.dp, start = 20.dp, end = 20.dp, top = 20.dp)
            ) {
                Ratings(data = detail)
//            TextContent(txt = "Writer", data = detail.writer, ic = R.drawable.baseline_draw_24)

                TextContent(txt = "Summary", data = detail.overview, ic = R.drawable.summary)
//            TextContent(txt = "Actors", data = detail.actors, ic = R.drawable.actor)
//            TextContent(txt = "Awards", data = detail.awards, ic = R.drawable.awards)
                ImagesTile(txt = "Production Company", companys = detail.production_companies)
            }
        }
    }


}

@Composable
fun ImagesTile(companys: List<ProductionCompany>, txt: String) {
    if (companys.isNotEmpty()) {
        Column {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.awards),
                    contentDescription = "ic",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = txt, color = Color.White, fontWeight = FontWeight.Bold)
            }
            LazyRow(
                contentPadding = PaddingValues(10.dp)
            ) {

                items(companys) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .background(Color.White)
                            .padding(5.dp)
                    ) {
                        AsyncImage(
                            model = Util.ImagePath + it.logo_path,
                            contentDescription = "img",
                            modifier = Modifier
                                .padding(6.dp)
                                .height(90.dp)
                                .clip(
                                    RoundedCornerShape(15.dp)

                                ),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = it.name)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_outlined_flag_24),
                                contentDescription = "img",


                                )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = it.origin_country)
                        }

                    }
                }
            }
        }

    }

}

@Composable
fun TextContent(txt: String, data: String, ic: Int) {
    Row {
        Icon(painter = painterResource(id = ic), contentDescription = "ic", tint = Color.White)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = txt, color = Color.White, fontWeight = FontWeight.Bold)
    }
    Text(text = data, color = Color.White, fontSize = 14.sp)
}


@Composable
fun Ratings(data: MovieDetail) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
    ) {
        Fields(ic = Icons.Default.Star, txt = data.vote_average.toString())
        Fields(img = R.drawable.time, txt = data.runtime.toString(), ic = null)
        Fields(ic = Icons.Default.DateRange, txt = data.release_date)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
    ) {
        Fields(ic = Icons.Default.ThumbUp, txt = data.vote_count.toString())
        Fields(img = R.drawable.baseline_attach_money_24, txt = data.budget.toString(), ic = null)
        Fields(
            ic = null,
            img = R.drawable.baseline_outlined_flag_24,
            txt = data.origin_country.joinToString { it })


    }
}

@Composable
fun Fields(ic: ImageVector?, txt: String, img: Int? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (img == null) {
            Icon(
                imageVector = ic!!,
                contentDescription = "start",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )


        } else {

            Icon(
                painter = painterResource(id = img),
                contentDescription = "img",
                tint = Color.White

            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = txt, color = Color.White)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForeGroundImage(img: String, title: String) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
            .padding(top = 50.dp),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = img, contentDescription = "img", modifier = Modifier
                .width(250.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                ),
            contentScale = ContentScale.Crop
        )
        Box(

            modifier = Modifier
                .matchParentSize()
                .width(200.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent, Color.Transparent, Color(0xB91A1B1B)
                        )

                    ),
                    shape = RoundedCornerShape(20.dp)
                ),

            )


    }
}

@Composable
fun BackGroundImage(img: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(black)
    ) {
        AsyncImage(
            model = img,
            contentDescription = "img",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop

        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, blackLight)
                    )
                )
        )
    }
}
