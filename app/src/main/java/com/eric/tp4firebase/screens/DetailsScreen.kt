package com.eric.tp4firebase.screens

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eric.tp4firebase.modele.Commentaires
import com.eric.tp4firebase.viewmodeles.AuthViewModele
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("UnrememberedMutableState")
@Composable
fun ShowDetailsScreen(navController: NavController, authvm: AuthViewModele) {
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.Green)
    ) {

        var commentsList = mutableStateListOf<Commentaires?>()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("commentaires").get()
            .addOnSuccessListener {
                queryDocumentSnapshots ->
                if (queryDocumentSnapshots.isEmpty) {
                    val listC = queryDocumentSnapshots.documents

                    for (l in listC) {
                        val comment: Commentaires? = l.toObject(Commentaires::class.java)
                        commentsList.add(comment)
                    }
                }
                else {
                    //Toast.makeText(, "Pas des commentaires enregistres", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                //Toast.makeText(context, "Impossible de lire les donnees", Toast.LENGTH_SHORT).show()
            }
        AvisScreen(LocalContext.current, commentsList)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvisScreen(context: Context, commentsList: SnapshotStateList<Commentaires?>) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            itemsIndexed(commentsList) { index, item ->

                Card(
                    onClick = {
                        Toast.makeText(
                            context,
                            commentsList[index]?.nomEtudiant + " est selectionne..",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.padding(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                    //elevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        commentsList[index]?.nomEtudiant?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(4.dp),
                                color = Color.Green,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        commentsList[index]?.nomCours?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(4.dp),

                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))

                        commentsList[index]?.avis?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(4.dp),

                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                    }
                }
            }

        }
    }
}
