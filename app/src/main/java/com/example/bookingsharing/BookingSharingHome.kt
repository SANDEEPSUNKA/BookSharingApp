package com.example.bookingsharing

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookingSharingHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingSharingHomeActivity()
        }
    }
}

@Composable
fun BookingSharingHomeActivity() {
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
                    color = colorResource(id = R.color.bt_color)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(38.dp)
                    .clickable {
                        context.startActivity(
                            Intent(
                                context,
                                BookHolderDataActivity::class.java
                            )
                        )
                    },
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile"
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier,
                text = "Home",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .clickable()
                    {

                        // Navigate to LoginActivity when clicked
                        BookSharingData.saveBookHolderStatus(context, false)

                        val intent = Intent(context, SessionActivity::class.java)
                        context.startActivity(intent)

                        context.finish()
                    }
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
                    .weight(1f),
                text = "Add New Book",
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        context.startActivity(Intent(context, ManageBooksActivity::class.java))

                    },
                text = "Manage Book",
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        BookListScreen()

    }

}


@Composable
fun BookListScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current as Activity
    val bookHolderEmail = BookSharingData.getBookHolderMail(context)
    var booksList by remember { mutableStateOf(listOf<BookData>()) }
    var booksLoading by remember { mutableStateOf(true) }

    LaunchedEffect(bookHolderEmail) {
        getBooks() { orders ->
            booksList = orders
            booksLoading = false
        }
    }

    val filteredBooks = booksList.filter {
        val query = searchQuery.text.trim().lowercase()
        it.bookName.lowercase().contains(query) || it.author.lowercase().contains(query)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (booksLoading) {
            LinearProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by Title or Author") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            LazyColumn {
                items(filteredBooks) { book ->
                    if (book.isAvailable == "Available")
                        BookCard(book)
                }
            }
        }
    }
}

@Composable
fun BookCard(book: BookData) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)

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


                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .clickable {
                            BookSelected.bookData = book

                            context.startActivity(
                                Intent(
                                    context,
                                    ViewBookActivity::class.java
                                )
                            )
                        }
                        .padding(horizontal = 4.dp)
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
                        .padding(vertical = 6.dp, horizontal = 10.dp),
                    text = "View",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = colorResource(id = R.color.black),
                    )
                )
            }

        }
    }
}


fun getBooks(callback: (List<BookData>) -> Unit) {
    val databaseReference = FirebaseDatabase.getInstance().getReference("SharedBooks")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val donationsList = mutableListOf<BookData>()

            for (donorSnapshot in snapshot.children) { // Iterate through email keys
                for (donationSnapshot in donorSnapshot.children) { // Iterate through donation entries
                    val donation = donationSnapshot.getValue(BookData::class.java)
                    donation?.let { donationsList.add(it) }
                }
            }

            callback(donationsList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BookingSharingHomeActivity()
}


fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    val decodedString = Base64.decode(base64String, Base64.DEFAULT)
    val originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return originalBitmap
}
