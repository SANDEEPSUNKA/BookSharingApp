package sunkasandeep.booksharing.s3430207

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BookHolderDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookHolderData()
        }
    }
}

@Composable
fun BookHolderData() {
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())
    ) {
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
                contentDescription = "back",
                tint = Color.Black
            )

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Your Profile",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))


            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.bookholder_profile),
                contentDescription = "Profile"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier,
                text = "Name : ${BookSharingData.getBookHolderName(context)}",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                modifier = Modifier,
                text = "Email : ${BookSharingData.getBookHolderMail(context)}",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )

            ContactAboutScreen()

        }
    }
}

@Composable
fun ContactAboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text("Contact Us", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sandeep Sunka", fontSize = 18.sp)
        Text("Email: sunkasandeep35@gmail.com", fontSize = 18.sp)
        Text("Student ID: S3430207", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text("About Us", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome to the Book Sharing App – where stories are shared, not shelved.\n\n" +
                    "Developed by Sandeep Sunka, this mobile app is designed to connect readers, book lovers, and knowledge seekers through the simple act of sharing books.\n\n" +
                    "Thank you for joining our community of readers and sharers!",
            fontSize = 16.sp
        )
    }
}
