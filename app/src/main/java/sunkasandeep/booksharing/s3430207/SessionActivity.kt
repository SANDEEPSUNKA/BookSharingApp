package sunkasandeep.booksharing.s3430207

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

class SessionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SessionActivityScreen()
        }
    }
}

@Composable
fun SessionActivityScreen() {
    var bookHolderEmail by remember { mutableStateOf("") }
    var bookHolderPassword by remember { mutableStateOf("") }


    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg))
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Spacer(modifier = Modifier.height(94.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Welcome,",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Glad to see you!",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Enter Email Address",
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(6.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            value = bookHolderEmail,
            onValueChange = { bookHolderEmail = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedContainerColor = colorResource(id = R.color.white),
                unfocusedIndicatorColor = Color.Black,
                focusedContainerColor = colorResource(id = R.color.white),
                focusedIndicatorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Enter Password",
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(6.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            value = bookHolderPassword,
            onValueChange = { bookHolderPassword = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedContainerColor = colorResource(id = R.color.white),
                unfocusedIndicatorColor = Color.Black,
                focusedContainerColor = colorResource(id = R.color.white),
                focusedIndicatorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {
                when {
                    bookHolderEmail.isEmpty() -> {
                            Toast.makeText(context, " Please Enter Mail", Toast.LENGTH_SHORT).show()
                    }

                    bookHolderPassword.isEmpty() -> {
                            Toast.makeText(context, " Please Enter Password", Toast.LENGTH_SHORT)
                                .show()
                    }

                    else -> {
                        val readerData = ReaderData(
                            "",
                            bookHolderEmail,
                            "",
                            bookHolderPassword
                        )

                        readerLogin(readerData,context)
                    }

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.bt_color),
                contentColor = colorResource(
                    id = R.color.black
                )
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            )
        }

        Spacer(modifier = Modifier.height(64.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Spacer(
                modifier = Modifier
                    .weight(1f) // Width of the line
                    .height(2.dp) // Adjust height as needed
                    .padding(horizontal = 6.dp)
                    .background(Color.Black) // Color of the line

            )

            Text(
                text = "or Register",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.clickable {
                    context.startActivity(Intent(context, JoinActivity::class.java))
                    context.finish()
                }
            )

            Spacer(
                modifier = Modifier
                    .weight(1f) // Width of the line
                    .height(2.dp) // Adjust height as needed
                    .padding(horizontal = 6.dp)
                    .background(Color.Black) // Color of the line

            )

        }


    }

}

fun readerLogin(readerData: ReaderData, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("BookSharedData").child(readerData.emailid.replace(".", ","))

    databaseReference.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val rData = task.result?.getValue(ReaderData::class.java)
            if (rData != null) {
                if (rData.password == readerData.password) {

                    BookSharingData.saveBookHolderStatus(context, true)
                    BookSharingData.saveBookHolderMail(context, rData.emailid)
                    BookSharingData.saveBookHolderName(context, rData.name)

                    Toast.makeText(context, "Login Sucessfully", Toast.LENGTH_SHORT).show()

                    context.startActivity(Intent(context, BookingSharingHome::class.java))

                } else {
                    Toast.makeText(context, "Seems Incorrect Credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Your account not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}
