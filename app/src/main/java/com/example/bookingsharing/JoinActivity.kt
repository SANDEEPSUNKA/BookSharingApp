package com.example.bookingsharing

import android.app.Activity
import android.content.Intent
import android.graphics.Paint.Join
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class JoinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoinActivityScreen()
        }
    }
}

@Composable
fun JoinActivityScreen() {
    var studentEmail by remember { mutableStateOf("") }
    var studentFullName by remember { mutableStateOf("") }
    var studentArea by remember { mutableStateOf("") }
    var studentPassword by remember { mutableStateOf("") }
    var sConfirmPassword by remember { mutableStateOf("") }


    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.evergreen))
    ) {
        Spacer(modifier = Modifier.height(46.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Create Account,",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "to get started now!",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Enter Email Address",
            color = Color.White
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
            value = studentEmail,
            onValueChange = { studentEmail = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = colorResource(id = R.color.evergreen),
                unfocusedIndicatorColor = Color.White,
                focusedContainerColor = colorResource(id = R.color.evergreen),
                focusedIndicatorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Enter FullName",
            color = Color.White
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
            value = studentFullName,
            onValueChange = { studentFullName = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = colorResource(id = R.color.evergreen),
                unfocusedIndicatorColor = Color.White,
                focusedContainerColor = colorResource(id = R.color.evergreen),
                focusedIndicatorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Enter Your Area",
            color = Color.White
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
            value = studentArea,
            onValueChange = { studentArea = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = colorResource(id = R.color.evergreen),
                unfocusedIndicatorColor = Color.White,
                focusedContainerColor = colorResource(id = R.color.evergreen),
                focusedIndicatorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Enter Password",
            color = Color.White
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
            value = studentPassword,
            onValueChange = { studentPassword = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = colorResource(id = R.color.evergreen),
                unfocusedIndicatorColor = Color.White,
                focusedContainerColor = colorResource(id = R.color.evergreen),
                focusedIndicatorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(12.dp))


        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Confirm Password",
            color = Color.White
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
            value = sConfirmPassword,
            onValueChange = { sConfirmPassword = it },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = colorResource(id = R.color.evergreen),
                unfocusedIndicatorColor = Color.White,
                focusedContainerColor = colorResource(id = R.color.evergreen),
                focusedIndicatorColor = Color.White
            )
        )


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Handle login */ },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.misty_mint),
                contentColor = colorResource(
                    id = R.color.evergreen
                )
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            )
        }

        Spacer(modifier = Modifier.height(36.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Spacer(
                modifier = Modifier
                    .weight(1f) // Width of the line
                    .height(2.dp) // Adjust height as needed
                    .padding(horizontal = 6.dp)
                    .background(Color.White) // Color of the line

            )

            Text(
                text = "or Login",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.clickable {
                    context.startActivity(Intent(context, SessionActivity::class.java))
                    context.finish()
                }
            )

            Spacer(
                modifier = Modifier
                    .weight(1f) // Width of the line
                    .height(2.dp) // Adjust height as needed
                    .padding(horizontal = 6.dp)
                    .background(Color.White) // Color of the line

            )

        }


    }

}

