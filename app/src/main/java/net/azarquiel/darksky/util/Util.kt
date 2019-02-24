package net.azarquiel.darksky.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

object Util {
    fun timeSpanToDate(ts: Long): String {
        val df = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return df.format(ts * 1000)
    }

    fun farToCel(far: Double): Double {
        var cel = (far - 32) * 0.5556
        val df = DecimalFormat("#.#")
        cel = java.lang.Double.valueOf(df.format(cel))
        return cel
    }
}
