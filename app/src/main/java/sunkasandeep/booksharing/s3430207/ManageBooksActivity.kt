package sunkasandeep.booksharing.s3430207

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
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
    val bookHolderEmail = BookSharingData.getBookHolderMail(context)
    var booksList by remember { mutableStateOf(listOf<BookData>()) }
    var booksLoading by remember { mutableStateOf(true) }

    fun loadBooks() {
        booksLoading = true
        ManageGetBooks(bookHolderEmail) { orders ->
            booksList = orders
            booksLoading = false
        }
    }

    LaunchedEffect(bookHolderEmail) {
        loadBooks()
    }



    val filteredBooks = booksList.filter {
        val query = searchQuery.text.trim().lowercase()
        it.bookName.lowercase().contains(query) || it.author.lowercase().contains(query)
    }

    Column(modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.bt_color)
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Icon(
                modifier = Modifier
                    .clickable {
                        context.finish()
                    },
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



            if (booksLoading) {
                LinearProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                if (filteredBooks.isNotEmpty()) {
                    LazyColumn {
                        items(filteredBooks) { book ->
                            MangeBookCard(book){
                                loadBooks()
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "You didn't share any books till now!",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MangeBookCard(book: BookData,callback: () -> Unit) {

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
                    .width(70.dp)
                    .height(90.dp)
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

                Row(modifier = Modifier.align(alignment = Alignment.End)) {

                    Text(
                        modifier = Modifier
                            .clickable {
                                deleteBook(book.bookId, context,callback)
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
                        text = "Delete",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = colorResource(id = R.color.black),
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))


                    Text(
                        modifier = Modifier

                            .clickable {
                                UpdateSelectionBook.updationBook = book
                                context.startActivity(
                                    Intent(
                                        context,
                                        UpdateBookActivity::class.java
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
                        text = "Update",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = colorResource(id = R.color.black),
                        )
                    )
                }
            }

        }
    }
}

//deleteBook(book.bookId, context)


fun ManageGetBooks(email: String, callback: (List<BookData>) -> Unit) {
    try {
        val emailKey = email.replace(".", ",")
        val databaseReference = FirebaseDatabase.getInstance().getReference("SharedBooks/$emailKey")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookList = mutableListOf<BookData>()

                if (!snapshot.exists() || snapshot.childrenCount.toInt() == 0) {
                    // Handle the case where there are no books under the branch
                    Log.e("Test", "No Books Found")
                    callback(emptyList()) // Return empty list or show appropriate message
                    return
                }

                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(BookData::class.java)
                    book?.let { bookList.add(it) }
                }

//                callback(bookList)

                fetchBookImages(bookList) { updatedBooks ->
                    callback(updatedBooks)
                }
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

fun deleteBook(bookId: String, activityContext: Context,callback: () -> Unit) {
    // Get the email of the book holder (use for branching)
    val bookHolderEmail = BookSharingData.getBookHolderMail(activityContext)

    // Reference to the specific book in Firebase (SharedBooks -> bookHolderEmail -> bookId)
    val bookReference = FirebaseDatabase.getInstance()
        .getReference("SharedBooks")
        .child(bookHolderEmail.replace(".", ","))  // Replace period with comma for Firebase path
        .child(bookId)

    // Perform the delete operation
    bookReference.removeValue()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Successfully deleted the book
                callback()
                Toast.makeText(activityContext, "Book Deleted Successfully", Toast.LENGTH_SHORT)
                    .show()

            } else {
                // Failed to delete the book
                Toast.makeText(
                    activityContext,
                    "Failed to Delete: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            // Handle any errors that occur during the operation
            Toast.makeText(
                activityContext,
                "Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}



