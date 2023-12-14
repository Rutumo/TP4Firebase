package com.eric.tp4firebase.notification

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.eric.tp4firebase.viewmodeles.AuthViewModele

@Composable
fun NotificationMessage(authvm: AuthViewModele) {
    val notifState = authvm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if (notifMessage !=null) {
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
    }
}