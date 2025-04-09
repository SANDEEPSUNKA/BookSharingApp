package com.example.bookingsharing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BookingSharingHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingSharingHomeActivity()
        }
    }
}

@Composable
fun BookingSharingHomeActivity()
{
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.bg)
            ),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {

            Image(
                modifier = Modifier
                    .size(38.dp),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile"
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    ,
                text = "Home",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(38.dp),
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout"
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white)
                )
                .padding(vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, AddBookActivity::class.java))
                    }
                    . weight(1f),
                text = "Add New Book",
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.
                        weight(1f),
                text = "Manage Book",
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        BookCardItem()

    }

}

@Composable
fun BookCardItem()
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
            .background(
                color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(6.dp)
            )
            .clickable {
            }
            .padding(horizontal = 8.dp, vertical = 12.dp)


    ) {

        Row()
        {
            Image(
                modifier = Modifier
                    .size(96.dp),
                painter = painterResource(id = R.drawable.book_image),
                contentDescription = "Book Des"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
            )

            {

                Text(
                    modifier = Modifier,
                    text = "The Da Vinci Code",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                )

                Text(
                    modifier = Modifier,
                    text = "Dan Brown",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier
                        .clickable {
                        }
                        .padding(horizontal = 12.dp)
                        .background(
                            color = colorResource(id = R.color.bt_color),
                            shape = RoundedCornerShape(
                                10.dp
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = colorResource(id = R.color.bt_color),
                            shape = RoundedCornerShape(
                                10.dp
                            )
                        )
                        .padding(vertical = 8.dp, horizontal = 18.dp)
                        .align(Alignment.End),
                    text = "View",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colorResource(id = R.color.white),
                    )
                )


            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BookingSharingHomeActivity()
}
