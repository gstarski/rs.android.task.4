package com.example.storagetask.utils

import com.example.storagetask.data.Review
import java.util.*

fun generateRandomReviews(incrementIdentifiers: Boolean = false) = sequence {
    val titles = listOf(
        "Just a title",
        "Sample title",
        "A little exaggerated, don't you think?",
        "Once again"
    )

    var n = 0L

    while (true) {
        val review = Review(
            if (incrementIdentifiers) n++ else 0L,
            titles[Random().nextInt(titles.size)],
            (0..5).random().toFloat(),
            "Just some summary.\nAnother line.\nAnother planet.",
            Date())

        yield(review)
    }
}