package com.vj.base.utils

fun String.mask(isMask: Boolean = false): String {
    var indexDash = ArrayList<Int>()
    if (isMask) {
        var maskedFormattedString = ""
        this.forEachIndexed { index, char ->
            if (char.equals('-', false)) {
                indexDash.add(index)
            }
        }

        val formatText = this.replace("-", "", false)

        val size = formatText.length - 4
        formatText.forEachIndexed { index, char ->
            if (index < size) {
                maskedFormattedString = maskedFormattedString.plus("X")
            } else {
                maskedFormattedString = maskedFormattedString.plus(char)
            }
        }

        indexDash.forEach { index ->
            val xx = StringBuilder().append(maskedFormattedString)
            maskedFormattedString = xx.insert(index, "-").toString()
        }

        return maskedFormattedString
    }
    return this
}