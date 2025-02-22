package com.example.staggeredphotogallery

import android.content.Context
import com.example.flashcardquiz.Flashcard
import com.example.flashcardquiz.R
import org.xmlpull.v1.XmlPullParser

fun loadFlashcardsFromXml(context: Context): List<Flashcard> {
    val flashcards = mutableListOf<Flashcard>()
    val parser = context.resources.getXml(R.xml.flashcards)

    var question: String? = null
    var answer: String? = null

    while (parser.eventType != XmlPullParser.END_DOCUMENT) {
        when (parser.eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name) {
                    "question" -> question = parser.nextText()
                    "answer" -> answer = parser.nextText()
                }
            }
            XmlPullParser.END_TAG -> {
                if (parser.name == "card" && question != null && answer != null) {
                    flashcards.add(Flashcard(question, answer))
                    question = null
                    answer = null
                }
            }
        }
        parser.next()
    }
    return flashcards
}