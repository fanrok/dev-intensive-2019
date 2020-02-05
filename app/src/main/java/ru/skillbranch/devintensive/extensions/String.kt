package ru.skillbranch.devintensive.extensions

fun String.truncate(limit: Int = 16): String{
    if (this.trimEnd().length > limit){
        return this.substring(0, limit).trimEnd()+"..."
    } else {
        return this.trimEnd()
    }

}

fun String.stripHtml(): String{
    var regex = "[\\s]{2,}".toRegex()
    var s = regex.replace(this, " ")
    regex = "<[^>]*>".toRegex()
    s = regex.replace(s, "")
    return s
}