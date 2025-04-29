package sunkasandeep.booksharing.s3430207

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.rememberImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

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
            )
            .padding(WindowInsets.systemBars.asPaddingValues()),
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

    val lifecycleOwner = LocalLifecycleOwner.current

    var booksList by remember { mutableStateOf(listOf<BookData>()) }
    var booksLoading by remember { mutableStateOf(true) }

    fun loadBooks() {
        booksLoading = true
        getBooks() { orders ->
            booksList = orders
            booksLoading = false
        }
    }

    // Load initially
    LaunchedEffect(Unit) {
        loadBooks()
    }

    // Reload when coming back to screen (ON_RESUME)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                loadBooks()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
            if (filteredBooks.isNotEmpty()) {
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
                        if (book.available == "Available")
                            BookCard(book)
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No Books Shared till now!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun BookCard(book: BookData) {
    val context = LocalContext.current

    val painter = rememberImagePainter(
        data = book.bookImage,
        builder = {
            crossfade(true) // Enables a fade-in effect when the image loads
            placeholder(R.drawable.book_placeholder) // A placeholder image while loading
            error(R.drawable.book_placeholder) // An error image if loading fails
        }
    )

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
                painter = painter,
                contentDescription = "Book Image",
                modifier = Modifier
                    .size(80.dp) // Adjust the size as needed
                    .padding(end = 16.dp)
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

    try {
        val databaseReference = FirebaseDatabase.getInstance().getReference("SharedBooks")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val booksList = mutableListOf<BookData>()

                if (!snapshot.exists() || snapshot.childrenCount.toInt() == 0) {
                    // Handle the case where there are no books under the branch
                    Log.e("Test", "No Books Found")
                    callback(emptyList()) // Return empty list or show appropriate message
                    return
                }

                Log.e("Test", "Books Found")

                for (donorSnapshot in snapshot.children) { // Iterate through email keys
                    for (bookSnapshot in donorSnapshot.children) { // Iterate through book entries
                        val book = bookSnapshot.getValue(BookData::class.java)
                        book?.let { booksList.add(it) }
                    }
                }

                // Fetch the images from Firebase Storage after fetching the book details
                fetchBookImages(booksList) { updatedBooks ->
                    callback(updatedBooks)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Test", "Error: ${error.message}")
                callback(emptyList()) // Return empty list in case of error
            }
        })
    } catch (e: Exception) {
        Log.e("Test", "No Books Found: ${e.message}")
        callback(emptyList()) // Return empty list if any exception occurs
    }
}


fun fetchBookImages(booksList: List<BookData>, callback: (List<BookData>) -> Unit) {
    val updatedBooksList = mutableListOf<BookData>()

    booksList.forEachIndexed { _, book ->
        // Check if the book has an image URL
        if (book.bookImage.isNotEmpty()) {
            // Fetch the image from Firebase Storage using the download URL
            val storageRef = FirebaseStorage.getInstance().reference
                .child("BookImages/${book.bookImage.substringAfterLast("/")}")

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val updatedBook =
                    book.copy(bookImage = uri.toString()) // Update book with image URL
                updatedBooksList.add(updatedBook)

                // Continue when all books are updated
                if (updatedBooksList.size == booksList.size) {
                    callback(updatedBooksList)
                }
            }.addOnFailureListener {
                // In case fetching the image fails, still continue
                updatedBooksList.add(book)
                if (updatedBooksList.size == booksList.size) {
                    callback(updatedBooksList)
                }
            }
        } else {
            // If no image, just add the book to the list
            updatedBooksList.add(book)
            if (updatedBooksList.size == booksList.size) {
                callback(updatedBooksList)
            }
        }
    }
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

@Composable
fun Base64Image(base64String: String, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(base64String) {
        bitmap = decodeBase64ToBitmap(base64String)
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    } else {
        // Placeholder while loading
        Spacer(
            modifier = modifier
                .background(Color.LightGray)
        )
    }
}
