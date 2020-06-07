package ttd.editor.api

import java.util.*

data class Bookmark(
    val bookmarkId: Int,
    val href: String,
    val description: String,
    val extended: String,
    val hash: String,
    val meta: String,
    val time: Date,
    val shared: Boolean,
    val toread: Boolean,
    val tags: Array<String>,
    val edited: Date?
)