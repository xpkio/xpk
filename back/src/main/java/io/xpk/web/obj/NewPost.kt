package io.xpk.web.obj


data class NewPost (val body: String, val authorId: Long, val spots: Set<Long>)