package com.example.bookingsharing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManageBooksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageBookListScreen()
        }
    }
}

@Composable
fun ManageBookListScreen() {
    val searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current as Activity
    val userEmail = BookSharingData.readMail(context)
    var booksList by remember { mutableStateOf(listOf<BookData>()) }
    var booksLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        ManageGetBooks(userEmail) { orders ->
            booksList = orders
            booksLoading = false
        }
    }

    val filteredBooks = booksList.filter {
        val query = searchQuery.text.trim().lowercase()
        it.bookName.lowercase().contains(query) || it.author.lowercase().contains(query)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white)
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Select Contact",
                tint = Color.Black
            )

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Manage Books",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                label = { Text("Search by Title or Author") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp)
//            )

            LazyColumn {
                items(filteredBooks) { book ->
                    MangeBookCard(book)
                }
            }
        }
    }
}

@Composable
fun MangeBookCard(book: BookData) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                bitmap = decodeBase64ToBitmap(book.bookImage)!!.asImageBitmap(),
                contentDescription = "Book Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .padding(end = 8.dp)
                    .clip(RectangleShape)
                    .border(2.dp, Color.Black, RectangleShape),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.bookName,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "Author: ${book.author}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Language: ${book.language}", fontSize = 13.sp, color = Color.Gray)
            }

            Text(
                modifier = Modifier
                    .clickable {
                        UpdateSelectionBook.updationBook = book
                        context.startActivity(Intent(context, UpdateBookActivity::class.java))
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
                    .padding(vertical = 4.dp, horizontal = 4.dp),
                text = "Update",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = colorResource(id = R.color.white),
                )
            )
        }
    }
}


fun ManageGetBooks(email: String, callback: (List<BookData>) -> Unit) {
    try {
        val emailKey = email.replace(".", ",")
        val databaseReference = FirebaseDatabase.getInstance().getReference("SharedBooks/$emailKey")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookList = mutableListOf<BookData>()

                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(BookData::class.java)
                    book?.let { bookList.add(it) }
                }

                callback(bookList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
                callback(emptyList())
            }
        })
    } catch (e: Exception) {
        Log.e("ManageGetBooks", "Exception: ${e.message}")
    }
}


