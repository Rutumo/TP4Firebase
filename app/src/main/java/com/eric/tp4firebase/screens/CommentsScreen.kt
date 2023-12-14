package com.eric.tp4firebase.screens

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eric.tp4firebase.modele.Commentaires
import com.eric.tp4firebase.viewmodeles.AuthViewModele
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CommentsScreen(navController: NavController, vm: AuthViewModele) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()

    ) {
        registerScreen(LocalContext.current, navController)
    }
}

@Composable
fun registerScreen(context: Context, navController: NavController) {

    val nomEtudiant = remember {
        mutableStateOf("")
    }

    val nomCours = remember {
        mutableStateOf("")
    }

    val avis = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = nomEtudiant.value,
            onValueChange = { nomEtudiant.value = it },
            placeholder = { Text(text = "Entrer ton nom") },

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = nomCours.value,

            onValueChange = { nomCours.value = it },

            placeholder = { Text(text = "Entrer le nom du cours") },

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = avis.value,
            onValueChange = { avis.value = it },
            placeholder = { Text(text = "Entrer ton avis") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (TextUtils.isEmpty(nomEtudiant.value.toString())) {
                    Toast.makeText(context, "SVP, entrer ton nom", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(nomCours.value.toString())) {
                    Toast.makeText(context, "SVP, entrer le nom du cours", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(avis.value.toString())) {
                    Toast.makeText(context, "SVP, entrer ton avis", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    ajouterDesAvis(
                        nomEtudiant.value, nomCours.value, avis.value, context
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Envoyer", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate(DestinationScreen.Details.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Voir les commentaires", modifier = Modifier.padding(8.dp))
        }
    }
}

fun ajouterDesAvis(
    nomEtudiant: String, nomCours: String, avis: String, context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbComments: CollectionReference = db.collection("commentaires")
    val comments = Commentaires(nomEtudiant, nomCours, avis)

    dbComments.add(comments).addOnSuccessListener {
        Toast.makeText(
            context, "Coommentaires enregistres", Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        Toast.makeText(context, "Impossible d'ajouter les commentaires \n$e", Toast.LENGTH_SHORT).show()
    }

}