/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cyberwalker.fashionstore.detail


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.dump.vertical
import com.cyberwalker.fashionstore.ui.theme.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.cyberwalker.fashionstore.data.*
import com.cyberwalker.fashionstore.util.showMessage

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    // item: Item,
    onAction: (actions: DetailScreenActions) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        DetailScreenContent(
            modifier = Modifier.padding(innerPadding),
            onAction = onAction,
            viewModel = viewModel
        )
    }
}

@Composable
private fun DetailScreenContent(
    viewModel: DetailViewModel,
    modifier: Modifier,
    onAction: (actions: DetailScreenActions) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Detail Screen" }
    ) {
        val selectedItem = viewModel.item.observeAsState().value
        val selectedColorItem = viewModel.colorItem.observeAsState().value
        val selectedSizeItem = viewModel.sizeItem.observeAsState().value

        Spacer(modifier = Modifier.size(16.dp))
        ImageBox(
            onAction = onAction,
            selectedColor = selectedColorItem ?: itemPeach,
            viewModel = viewModel,
            isFavorite = selectedItem?._isFavorite ?: false
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 228.dp)
        ) {
            TabRow(
                viewModel = viewModel,
                selectedColor = selectedColorItem ?: itemPeach,
//                colorItem
            )// ColorItem.PEACH)
            Spacer(modifier = Modifier.size(16.dp))
            ProductInfo(selectedItem ?: item1, selectedColorItem ?: itemPeach)
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Size", style = MaterialTheme.typography.medium_18)
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SizeBox(viewModel = viewModel, sizeItem = sizeS, selectedSizeItem?._size ?: "L")
            SizeBox(viewModel = viewModel, sizeItem = sizeM, selectedSizeItem?._size ?: "L")
            SizeBox(viewModel = viewModel, sizeItem = sizeL, selectedSizeItem?._size ?: "L")
            SizeBox(viewModel = viewModel, sizeItem = sizeXL, selectedSizeItem?._size ?: "L")
        }
        Spacer(modifier = Modifier.weight(1F))
        Spacer(modifier = Modifier.size(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = "Price", style = MaterialTheme.typography.caption.copy(gray))
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = selectedSizeItem?._price ?: sizeL._price,
                    style = MaterialTheme.typography.medium_18
                )
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(backgroundColor = highlight),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .widthIn(170.dp)
                    .defaultMinSize(minHeight = 40.dp)
            ) {
                Text(
                    text = "Add To Cart",
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun ProductInfo(
    item: Item,
    colorItem: ColorItem,
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Text(text = "Sbm T-Shirt", style = MaterialTheme.typography.medium_18)
            Text(text = item._title, style = MaterialTheme.typography.medium_18)
            Row {
                Text(text = item._rating, style = MaterialTheme.typography.medium_18)
                Spacer(modifier = Modifier.size(8.dp))
                Image(painter = painterResource(id = R.drawable.icstar), contentDescription = null)
            }
        }
//        Text(text = "Modern Peach", style = MaterialTheme.typography.small_caption2)
        Text(text = colorItem._colorTitle, style = MaterialTheme.typography.small_caption2)
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "${item._description}  $textWithLink",
            style = MaterialTheme.typography.small_caption.copy(color = gray)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            buildAnnotatedString {
                productInfo.forEach {
                    withStyle(style = paragraphStyle) {
                        append(bullet)
                        append("\t\t")
                        append(it)
                    }
                }
            },
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.small_caption2
        )
    }
}

val textWithLink = buildAnnotatedString {
    append("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Tenetur, sit fuga? Cum optio fugit ")

    pushStringAnnotation(tag = "See more", annotation = "https://google.com/")
    withStyle(style = SpanStyle(color = Purple700)) {
        append("See more")
    }
    pop()
}

val bullet = "\u2022"
val productInfo = listOf(
    "Dolor sit amet",
    "Lorem ipsum",
)
val paragraphStyle = ParagraphStyle(textIndent = TextIndent())

@Composable
private fun ImageBox(
    viewModel: DetailViewModel,
    isFavorite: Boolean,
    selectedColor: ColorItem,
    onAction: (actions: DetailScreenActions) -> Unit
) {
    Box(
        modifier = Modifier
            .defaultMinSize(minHeight = 310.dp)
            .fillMaxWidth()
            .background(color = cardColorBlue, shape = RoundedCornerShape(16.dp))
    ) {
        val context = LocalContext.current
        var isFavorite = rememberSaveable { mutableStateOf(isFavorite) }
        Image(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp)
                .clickable { onAction(DetailScreenActions.Back) },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 16.dp)
                .clickable {
                    showMessage(context, "favorite")
                    isFavorite.value = !isFavorite.value
                    viewModel.setIsFavorite(!isFavorite.value)
                },
            painter = painterResource(id = if (isFavorite.value) R.drawable.ic_heart_red else R.drawable.ic_heart_filled),
            contentDescription = null
        )
//        Image(
//            modifier = Modifier
//                .defaultMinSize(minWidth = 287.dp, minHeight = 335.dp)
//                .align(Alignment.BottomCenter),
//            painter = painterResource(id = R.drawable.ic_girl),//how to make it dynamic
//            contentDescription = null
//        )

        AsyncImage(
            model = selectedColor?._image,
//            ImageRequest.Builder(LocalContext.current)
//                .data(student.image)
//                .crossfade(true)
//                .build(),//
            contentDescription = selectedColor._colorTitle,
            //contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_girl),
            modifier = Modifier
                .defaultMinSize(minWidth = 287.dp, minHeight = 335.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun TabRow(
    viewModel: DetailViewModel,
    selectedColor: ColorItem// Color
) {
    Row(
        modifier = Modifier
            .vertical()
            .rotate(-90F), verticalAlignment = Alignment.CenterVertically
    ) {
        ColorBox(viewModel = viewModel, colorItem = itemGreen, selected = selectedColor._color)
        Spacer(modifier = Modifier.size(16.dp))
        ColorBox(viewModel = viewModel, colorItem = itemBlue, selected = selectedColor._color)
        Spacer(modifier = Modifier.size(16.dp))
        ColorBox(viewModel = viewModel, colorItem = itemPeach, selected = selectedColor._color)
        Spacer(modifier = Modifier.size(16.dp))
        ColorBox(viewModel = viewModel, colorItem = itemYellow, selected = selectedColor._color)
    }
}

@Composable
private fun SizeBox(
    viewModel: DetailViewModel,
    sizeItem: SizeItem,
    selectedSize: String,
) {
//    val context = LocalContext.current
    Box(
        modifier = if (selectedSize == sizeItem._size)
            modifierSelected.clickable {
                viewModel.setSizeItem(sizeItem._size)
            }
        else
            modifierUnselected.clickable {
                viewModel.setSizeItem(sizeItem._size)
            },

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sizeItem._size,
            style = MaterialTheme.typography.medium_18_bold
                .copy(if (selectedSize == sizeItem._size) Color.White else dark)
        )
    }
}

val modifierSelected = Modifier
    .size(56.dp)
    .clip(RoundedCornerShape(12.dp))
    .background(color = highlight, shape = RoundedCornerShape(12.dp))

val modifierUnselected = Modifier
    .size(45.dp)
    .clip(RoundedCornerShape(12.dp))
    .background(color = sizeGreen, shape = RoundedCornerShape(12.dp))

@Composable
fun ColorBox(
    viewModel: DetailViewModel,
    colorItem: ColorItem,// Color,
    selected: Color,// ColorItem,
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clickable {
//                viewModel.setSelecteColor(colorItem)
                viewModel.setColorItem(colorItem._color)
            }
            .background(color = colorItem._color, shape = CircleShape)
    ) {
        if (selected == colorItem._color) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(90F),
                painter = painterResource(id = R.drawable.ic_tick),
                contentDescription = null
            )
        }
        // Spacer(modifier = Modifier.size(16.dp))
    }
}

sealed class DetailScreenActions {
    object Back : DetailScreenActions()
}