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
    val str: String
    var difference = date.time - this.time
    val flag = difference < 0
    difference = abs(difference)
    val seconds: Long = difference / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    if (flag) {
        str = when {
            seconds <= 1 -> "только что"
            seconds <= 45 -> "через несколько секунд"
            seconds <= 75 -> "через минуту"
            minutes <= 45 -> "через ${TimeUnits.MINUTE.plural(minutes.toInt())}"
            minutes <= 75 -> "через час"
            hours < 22 -> "через ${TimeUnits.HOUR.plural(hours.toInt())}"
            hours < 26 -> "через день"
            days < 360 -> "через ${TimeUnits.DAY.plural(days.toInt())}"
            else -> "более чем через год"
        }
    } else {
        str = when {
            seconds <= 1 -> "только что"
            seconds <= 45 -> "несколько секунд назад"
            seconds <= 75 -> "минуту назад"
            minutes <= 45 -> "${TimeUnits.MINUTE.plural(minutes.toInt())} назад"
            minutes <= 75 -> "час назад"
            hours < 22 -> "${TimeUnits.HOUR.plural(hours.toInt())} назад"
            hours < 26 -> "день назад"
            days < 360 -> "${TimeUnits.DAY.plural(days.toInt())} назад"
            else -> "более года назад"
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