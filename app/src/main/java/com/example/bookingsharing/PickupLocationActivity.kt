package com.example.bookingsharing

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

class PickupLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}


//@Composable
//fun LocateRestaurantScreen() {
//
//
//
//    val cameraPositionState = rememberCameraPositionState {
//        position =
//            CameraPosition.fromLatLngZoom(LatLng(33.8751469,74.9031424), 14f) // Focused on Cornell Quarter
//    }
//
//    val appContext = LocalContext.current as Activity
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = colorResource(id = R.color))
//                .padding(vertical = 6.dp, horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back",
//                tint = Color.Black
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = "Select Pickup Location",
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.headlineSmall,
//                color = Color.White
//            )
//        }
//
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState
//        ) {
//
//
//            Marker(
//                state = rememberMarkerState(position = LatLng(33.8751469,74.9031424)),
//                title = "PickUp Location"
//
//            )
//
//        }
//    }
//}