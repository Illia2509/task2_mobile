package com.example.creditcardproject.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import com.example.creditcardproject.data.Purchase

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: PurchaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)

        setContent {
            var otp by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                viewModel.generateRandomPurchases()
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text("Purchase List")
                LazyColumn {
                    items(viewModel.purchaseList.collectAsState().value) { purchase ->
                        PurchaseItem(purchase)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.generateOTP() },
                    enabled = !viewModel.isOtpGenerated.collectAsState().value
                ) {
                    Text("Get OTP")
                }

                if (viewModel.isOtpGenerated.collectAsState().value) {
                    otp = viewModel.otp.collectAsState().value
                    Text("Your OTP is: $otp")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.pay()
                        Toast.makeText(this@MainActivity, "Payment successful!", Toast.LENGTH_SHORT).show()
                    },
                    enabled = viewModel.isPayEnabled.collectAsState().value
                ) {
                    Text("Pay")
                }
            }
        }
    }
}

@Composable
fun PurchaseItem(purchase: Purchase) {
    Text("${purchase.name} - ${purchase.category} - ${purchase.color} - $${purchase.amount}")
}
