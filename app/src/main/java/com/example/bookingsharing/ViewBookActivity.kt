package com.example.bookingsharing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

class ViewBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewBookScreen(BookSelected.bookData)
        }
    }
}

object BookSelected {
    lateinit var bookData: BookData
}

@Composable
fun ViewBookScreen(book: BookData) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.bt_color))
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    (context as Activity).finish()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "View Book Details",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    bitmap = decodeBase64ToBitmap(book.bookImage)!!.asImageBitmap(),
                    contentDescription = "Book Image",
                    modifier = Modifier
                        .width(70.dp)
                        .height(90.dp)
                        .padding(end = 8.dp)
                        .clip(RectangleShape)
                        .border(1.dp, Color.Black, RectangleShape),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.bookName,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Text(
                            text = "Author :",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = book.author,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row {
                        Text(
                            text = "Language :",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = book.language,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row {
                        Text(
                            text = "Category :",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = book.genre,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }


                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                3.dp
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(3.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    val address = getAddressFromLatLng(
                        context,
                        LatLng(book.lat.toDouble(), book.lng.toDouble())
                    )
                    Text(modifier = Modifier.weight(1f), text = address, maxLines = 2)

                    Text(
                        text = "Show",
                        color = Color.White,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        ShowBookPickupLocationActivity::class.java
                                    )
                                )

                            }
                            .background(
                                color = colorResource(id = R.color.black),
                                shape = RoundedCornerShape(
                                    6.dp
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.black),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 4.dp
                            )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Publisher", modifier = Modifier.weight(1f))
                    Text(text = ":  ")
                    Text(text = book.publisher, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Book Condition", modifier = Modifier.weight(1f))
                    Text(text = ":")
                    Text(text = book.condition, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Book Availability", modifier = Modifier.weight(1f))
                    Text(text = ":")
                    Text(text = book.isAvailable, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Owner Name", modifier = Modifier.weight(1f))
                    Text(text = ":")
                    Text(text = book.ownerName, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Owner Contact", modifier = Modifier.weight(1f))
                    Text(text = ":")
                    Text(text = book.ownerContact, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}