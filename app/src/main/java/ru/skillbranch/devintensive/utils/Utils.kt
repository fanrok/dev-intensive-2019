package ru.skillbranch.devintensive.utils

object Utils {
    val mapTranslit = mapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya",
        "А" to "A",
        "Б" to "B",
        "В" to "V",
        "Г" to "G",
        "Д" to "D",
        "Е" to "E",
        "Ё" to "E",
        "Ж" to "Zh",
        "З" to "Z",
        "И" to "I",
        "Й" to "I",
        "К" to "K",
        "Л" to "L",
        "М" to "M",
        "Н" to "N",
        "О" to "O",
        "П" to "P",
        "Р" to "R",
        "С" to "S",
        "Т" to "T",
        "У" to "U",
        "Ф" to "F",
        "Х" to "H",
        "Ц" to "C",
        "Ч" to "Ch",
        "Ш" to "Sh",
        "Щ" to "Sh'",
        "Ъ" to "",
        "Ы" to "I",
        "Ь" to "",
        "Э" to "E",
        "Ю" to "Yu",
        "Я" to "Ya"
    )

    fun parseFullName(fullName: String?, devider: String = " "): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        if (firstName?.length === 0) {
            firstName = null
        }
        if (lastName?.length === 0) {
            lastName = null
        }
        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {

        val (first, last) = parseFullName(payload, divider)
        val firstTranslit: String = translitWord(first)
        val lastTranslit: String = translitWord(last)
        return "$firstTranslit$divider$lastTranslit"
    }

    fun translitWord(s: String?): String {
        var out: String = ""
        if (s !== null) {
            for (char in s) {
                if (mapTranslit.containsKey("$char")) {
                    out += mapTranslit.getValue("$char")
                } else {
                    out += char
                }
            }
        }
        return out
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = getFirstUpperSymbol(firstName)
        val last = getFirstUpperSymbol(lastName)
        if (first.isEmpty() && last.isEmpty()) {
            return null
        } else {
            return "$first$last"
        }
    }

    private fun getFirstUpperSymbol(s: String?): String {
        val symbol: Char? = s?.getOrNull(0)?.toUpperCase()
        if (symbol === null || symbol === ' ') {
            return ""
        } else {
            return "$symbol"
        }
    }


}