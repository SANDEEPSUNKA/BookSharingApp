package com.example.bookingsharing

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class UpdateBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateBook()
        }

    }
}

object UpdateSelectionBook{
 var updationBook = BookData ()
}

@Composable
fun UpdateBook() {
    var bookTitle by remember { mutableStateOf(UpdateSelectionBook.updationBook.bookName) }
    var bookAuthors by remember { mutableStateOf(UpdateSelectionBook.updationBook.author) }
    var bookCategory by remember { mutableStateOf(UpdateSelectionBook.updationBook.genre) }
    var bookLanguage by remember { mutableStateOf(UpdateSelectionBook.updationBook.language) }
    var bookPublisher by remember { mutableStateOf(UpdateSelectionBook.updationBook.publisher) }
    var location by remember { mutableStateOf(UpdateSelectionBook.updationBook.lat) }

    var ownerName by remember { mutableStateOf(UpdateSelectionBook.updationBook.ownerName) }
    var ownerContact by remember { mutableStateOf(UpdateSelectionBook.updationBook.ownerContact) }

    var selectedOption by remember { mutableStateOf<String?>(UpdateSelectionBook.updationBook.condition) }

    var isAvailable by remember { mutableStateOf<String?>(UpdateSelectionBook.updationBook.isAvailable) }

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.bg)
            )
            .verticalScroll(rememberScrollState()),
    ) {

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
                text = "Update Book",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = bookTitle,
            onValueChange = { bookTitle = it },
            label = { Text("Title of Book") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = bookAuthors,
            onValueChange = { bookAuthors = it },
            label = { Text("Authors") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = bookCategory,
            onValueChange = { bookCategory = it },
            label = { Text("Genre/Category") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = bookLanguage,
            onValueChange = { bookLanguage = it },
            label = { Text("Language(e.g.,English)") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = bookPublisher,
            onValueChange = { bookPublisher = it },
            label = { Text("Publisher") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )
        Spacer(modifier = Modifier.height(6.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Owner Name") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.white),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            value = ownerContact,
            onValueChange = { ownerContact = it },
            label = { Text("Contact Number") },
            textStyle = TextStyle(color = colorResource(id = R.color.black)),

            )

        Spacer(modifier = Modifier.height(6.dp))



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Condition Selector?",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption == "New",
                        onClick = { selectedOption = "New" }
                    )
                    Text("New", modifier = Modifier.clickable { selectedOption = "New" })
                }


                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption == "Good",
                        onClick = { selectedOption = "Good" }
                    )
                    Text("Good", modifier = Modifier.clickable { selectedOption = "Good" })
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption == "Torn",
                        onClick = { selectedOption = "Torn" }
                    )
                    Text("Worn", modifier = Modifier.clickable { selectedOption = "Worn" })
                }
            }
        }

//        AddBook1()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Availability? ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isAvailable == "Available",
                        onClick = { isAvailable = "Available" }
                    )
                    Text("Available", modifier = Modifier.clickable { isAvailable = "Available" })
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isAvailable == "Not Available",
                        onClick = { isAvailable = "Not Available" }
                    )
                    Text(
                        "Not Available",
                        modifier = Modifier.clickable { selectedOption = "Not Available" })
                }
            }
        }

//        AddBook2()

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier
                .clickable {
                    if (bookTitle.isEmpty()) {
                        Toast
                            .makeText(context, "Enter all fields", Toast.LENGTH_SHORT)
                            .show()
                    } else {


                        val updatedBook = mapOf(
                            "bookName" to bookTitle,
                            "author" to bookAuthors,
                            "genre" to bookCategory,
                            "language" to bookLanguage,
                            "publisher" to bookPublisher,
                            "lat" to "908.78",
                            "lng" to "673.38",
                            "condition" to selectedOption!!,
                            "isAvailable" to isAvailable!!,
                            "ownerName" to ownerName,
                            "ownerContact" to ownerContact,
                        )
                        updateBook(UpdateSelectionBook.updationBook.bookId, updatedBook, context)


                    }
                }
                .width(200.dp)
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
                .padding(vertical = 12.dp, horizontal = 12.dp)
                .align(Alignment.CenterHorizontally),
            text = "Update",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                color = colorResource(id = R.color.white),
            )
        )


    }
}

fun updateBook(donorId: String, updatedData: Map<String, Any>, context: Context) {

    try {
        val emailKey = BookSharingData.readMail(context)
            .replace(".", ",")
        val path = "SharedBooks/$emailKey/$donorId"
        FirebaseDatabase.getInstance().getReference(path).updateChildren(updatedData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Details Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                (context as Activity).finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
    } catch (e: Exception) {
        Log.e("Test", "Error Message : ${e.message}")
    }
}
