package sunkasandeep.booksharing.s3430207

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnBoardingScreen(::checkStudentStatus)
        }
    }

    private fun checkStudentStatus(studentStatus: Int) {
        if (studentStatus == 2) {
            startActivity(Intent(this, BookingSharingHome::class.java))
            finish()
        }
        if (studentStatus == 1) {
            startActivity(Intent(this, SessionActivity::class.java))
            finish()
        }

    }
}

@Composable
fun OnBoardingScreen(onLoginClick: (studentStatus: Int) -> Unit) {
    val context = LocalContext.current as Activity

    SideEffect {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)

            val currentStatus = BookSharingData.getBookHolderStatus(context)

            if(currentStatus)
            {
                onLoginClick.invoke(2)

            }else{
                onLoginClick.invoke(1)

            }
        }
    }

    SplashScrOnBoardingScreenD()
}

@Composable
fun SplashScrOnBoardingScreenD() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bt_color))
            .padding(WindowInsets.systemBars.asPaddingValues()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(94.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Sandeep Sunka",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )


            Card(
                modifier = Modifier
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            )
            {

                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .background(color = Color.Transparent),
                )
                {
                    Spacer(modifier = Modifier.height(6.dp))

                    Image(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.book_sharing),
                        contentDescription = "Book Sharing App",
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Share",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black), // Green color similar to the design
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Top Books",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black), // Green color similar to the design
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Interesting Books",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black), // Green color similar to the design
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Informative Books",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black), // Green color similar to the design
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }


        }
    }

}