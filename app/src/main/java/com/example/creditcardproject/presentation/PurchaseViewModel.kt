package com.example.creditcardproject.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.creditcardproject.data.Purchase
import com.example.creditcardproject.data.DataRepository

class PurchaseViewModel : ViewModel() {
    private val _purchaseList = MutableStateFlow<List<Purchase>>(emptyList())
    val purchaseList: StateFlow<List<Purchase>> = _purchaseList

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp

    private val _isOtpGenerated = MutableStateFlow(false)
    val isOtpGenerated: StateFlow<Boolean> = _isOtpGenerated

    private val _isPayEnabled = MutableStateFlow(false)
    val isPayEnabled: StateFlow<Boolean> = _isPayEnabled

    fun generateRandomPurchases() {
        val purchases = List(10) { DataRepository.getRandomPurchase() }
        _purchaseList.value = purchases
    }

    fun generateOTP() {
        _otp.value = DataRepository.generateOTP()
        _isOtpGenerated.value = true
        _isPayEnabled.value = true
    }

    fun pay() {
        if (_isOtpGenerated.value) {
            val totalAmount = _purchaseList.value.sumByDouble { it.amount }
            println("You have successfully paid $$totalAmount. Thank you!")
            _isPayEnabled.value = false
        }
    }
}
