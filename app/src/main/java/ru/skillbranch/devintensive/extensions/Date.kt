package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var str: String = ""
    var difference = date.time - this.time
    val flag = difference < 0
    difference = abs(difference)
    val seconds: Long = difference / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    if (flag) {
        if (seconds < 60) {
            str = "через ${TimeUnits.SECOND.plural(seconds.toInt())}"
        } else if (minutes < 60) {
            str = "через ${TimeUnits.MINUTE.plural(minutes.toInt())}"
        } else if (hours < 24) {
            str = "через ${TimeUnits.HOUR.plural(hours.toInt())}"
        } else if (days < 366) {
            str = "через ${TimeUnits.DAY.plural(days.toInt())}"
        } else {
            str = "более чем через год"
        }
    } else {
        if (seconds < 60) {
            str = "${TimeUnits.SECOND.plural(seconds.toInt())} назад"
        } else if (minutes < 60) {
            str = "${TimeUnits.MINUTE.plural(minutes.toInt())} назад"
        } else if (hours < 24) {
            str = "${TimeUnits.HOUR.plural(hours.toInt())} назад"
        } else if (days < 366) {
            str = "${TimeUnits.DAY.plural(days.toInt())} назад"
        } else {
            str = "более года назад"
        }
    }
    return str
}

enum class TimeUnits {
    SECOND {
        override fun plural(i: Int): String {
            val str: String = when (i % 10) {
                0, 5, 6, 7, 8, 9 -> "секунд"
                1 -> "секунду"
                2, 3, 4 -> "секунды"
                else -> "секунд"
            }
            return "$i $str"
        }
    },
    MINUTE {
        override fun plural(i: Int): String {
            val str: String = when (i % 10) {
                0, 5, 6, 7, 8, 9 -> "минут"
                1 -> "минуту"
                2, 3, 4 -> "минуты"
                else -> "минут"
            }
            return "$i $str"
        }
    },
    HOUR {
        override fun plural(i: Int): String {
            val str: String = when (i % 10) {
                0, 5, 6, 7, 8, 9 -> "часов"
                1 -> "час"
                2, 3, 4 -> "часа"
                else -> "час"
            }
            return "$i $str"
        }
    },
    DAY {
        override fun plural(i: Int): String {
            val str: String = when (i % 10) {
                0, 5, 6, 7, 8, 9 -> "дней"
                1 -> "день"
                2, 3, 4 -> "дня"
                else -> "день"
            }
            return "$i $str"
        }
    };

    abstract fun plural(i: Int): String
}