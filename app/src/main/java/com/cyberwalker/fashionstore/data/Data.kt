package com.cyberwalker.fashionstore.data

import androidx.compose.ui.graphics.Color
import com.cyberwalker.fashionstore.ui.theme.cardColorBlue
import com.cyberwalker.fashionstore.ui.theme.cardColorGreen
import com.cyberwalker.fashionstore.ui.theme.cardColorPeach
import com.cyberwalker.fashionstore.ui.theme.cardColorYellow

data class SizeItem(
    val _item: String,
    val _size: String,
    val _price: String
)
data class ColorItem(
    val _item: String,
    val _color: Color,
    val _colorTitle: String,
    val _image: String
)
data class Item(
    val _item:String,
    val _title: String,
    val _sizes: List<SizeItem>,
    val _color:List<ColorItem>,
    val _image:String,
    val _rating: String,
    val _description: String,
    val _price:String,
)
val sizeS: SizeItem = SizeItem("Sbm T-Shirt", "S","$120")
val sizeM: SizeItem = SizeItem("Sbm T-Shirt", "M","$140")
val sizeL: SizeItem = SizeItem("Sbm T-Shirt", "L","$110")
val sizeXL: SizeItem = SizeItem("Sbm T-Shirt", "XL","$220")

val sizesItem = listOf(sizeS, sizeM, sizeL, sizeXL)

val itemGreen=ColorItem(_item = "Sbm T-Shirt", _color=cardColorGreen, _colorTitle = "Green", _image = "https://http.cat/100")
val itemBlue=ColorItem(_item = "Sbm T-Shirt", _color=cardColorBlue, _colorTitle = "Blue", _image = "https://http.cat/101")
val itemPeach=ColorItem(_item = "Sbm T-Shirt", _color= cardColorPeach, _colorTitle = "Modern Peach", _image = "")
val itemYellow=ColorItem(_item = "Sbm T-Shirt", _color= cardColorYellow, _colorTitle = "Yellow", _image = "https://http.cat/103")

val colorsItem = listOf(itemBlue, itemGreen, itemPeach, itemYellow)

val item1 = Item(
    _item ="Sbm T-Shirt",
    _title= "Sbm T-Shirt",
    _sizes =   sizesItem,
    _color= colorsItem,
    _image="",
    _rating = "5/5",
    _description = "some text",
    _price ="$90")
val item2 = Item(
    _item ="Sbm T-Shirt2",
    _title= "Sbm T-Shirt",
    _sizes =   sizesItem,
    _color= colorsItem,
    _image="",
    _rating = "5/5",
    _description = "some text2",
    _price ="$90")
val item3 = Item(
    _item ="Sbm T-Shirt3",
    _title= "Sbm T-Shirt",
    _sizes =   sizesItem,
    _color= colorsItem,
    _image="",
    _rating = "5/5",
    _description = "some text3",
    _price ="$90")