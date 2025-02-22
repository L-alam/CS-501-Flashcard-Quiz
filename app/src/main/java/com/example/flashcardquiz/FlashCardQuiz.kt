package com.example.flashcardquiz

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcardquiz.ui.theme.FlashcardQuizTheme
import com.example.staggeredphotogallery.loadFlashcardsFromXml
import kotlinx.coroutines.delay

@Composable
fun FlashcardQuizApp(context: Context) {
    var flashcards by remember { mutableStateOf(loadFlashcardsFromXml(context)) }
    var shuffledFlashcards by remember { mutableStateOf(flashcards) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(15000L) // Shuffle every 15 seconds
            shuffledFlashcards = flashcards.shuffled()
        }
    }

    LazyRow(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        items(shuffledFlashcards) { flashcard ->
            FlashcardItem(flashcard)
        }
    }
}

@Composable
fun FlashcardItem(flashcard: Flashcard) {
    var flipped by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(150.dp)
            .padding(8.dp)
            .clickable { flipped = !flipped },
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(if (flipped) flashcard.answer else flashcard.question)
            }
        }
    )
}

@Preview
@Composable
fun PreviewFlashcardQuiz() {
    FlashcardQuizApp(context = LocalContext.current)
}