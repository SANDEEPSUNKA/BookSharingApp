package com.example.bookingsharing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BookingSharingHome : ComponentActivity() {
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

        BookListScreen()

//        BookCardItem()

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

@Composable
fun BookListScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val books = listOf(
        Book("To Kill a Mockingbird", "Harper Lee", "English"),
        Book("1984", "George Orwell", "English"),
        Book("The Alchemist", "Paulo Coelho", "Portuguese"),
        Book("Pride and Prejudice", "Jane Austen", "English"),
        Book("Don Quixote", "Miguel de Cervantes", "Spanish"),
        Book("Les Misérables", "Victor Hugo", "French"),
        Book("Crime and Punishment", "Fyodor Dostoevsky", "Russian"),
        Book("One Hundred Years of Solitude", "Gabriel García Márquez", "Spanish"),
        Book("The Kite Runner", "Khaled Hosseini", "English"),
        Book("War and Peace", "Leo Tolstoy", "Russian"),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "English"),
        Book("The Little Prince", "Antoine de Saint-Exupéry", "French"),
        Book("A Tale of Two Cities", "Charles Dickens", "English"),
        Book("The Catcher in the Rye", "J.D. Salinger", "English"),
        Book("Siddhartha", "Hermann Hesse", "German")
    )

    val filteredBooks = books.filter {
        val query = searchQuery.text.trim().lowercase()
        it.title.lowercase().contains(query) || it.author.lowercase().contains(query)
    }

    Column(modifier = Modifier.padding(16.dp)) {
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
                BookCard(book)
            }
        }
    }
}

@Composable
fun BookCard(book: Book) {
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
                painter = painterResource(id = book.imageRes),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "Author: ${book.author}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Language: ${book.language}", fontSize = 13.sp, color = Color.Gray)
            }

            Text(
                modifier = Modifier
                    .clickable {
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
                text = "View",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = colorResource(id = R.color.white),
                )
            )
        }
    }
}


data class Book(
    val title: String,
    val author: String,
    val language: String,
    val imageRes: Int = R.drawable.book_image
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BookingSharingHomeActivity()
}
