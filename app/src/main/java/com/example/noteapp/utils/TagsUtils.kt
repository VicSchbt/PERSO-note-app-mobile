package com.example.noteapp.utils

import com.example.noteapp.data.model.Tag

fun parseTags(input: String): List<Tag> {
    return input.split(",")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinctBy { it.lowercase() } // avoid duplicates, case insensitive
        .map { Tag(id = 0, name = it) } // id = 0, repository should resolve id on insert
}