package com.fruit.frenzy.fruitfrenzy

object Prefs {
    var level: Int
        get() = sharedPrefs.getInt("level", 1)
        set(value) = sharedPrefs.edit().putInt("level", value).apply()

    var selectedBg: Int
        get() = sharedPrefs.getInt("selectedBg", R.drawable.background)
        set(value) = sharedPrefs.edit().putInt("selectedBg", value).apply()

    private lateinit var sharedPrefs: android.content.SharedPreferences

    fun init(context: android.content.Context) {
        sharedPrefs = context.getSharedPreferences(context.packageName, android.content.Context.MODE_PRIVATE)
    }

    var musicVolume: Float
        get() = sharedPrefs.getFloat("musicVolume", 0.5f)
        set(value) = sharedPrefs.edit().putFloat("musicVolume", value).apply()
    var soundVolume: Float
        get() = sharedPrefs.getFloat("soundVolume", 0.5f)
        set(value) = sharedPrefs.edit().putFloat("soundVolume", value).apply()
    var lastBonusTimestamp: Long
        get() = sharedPrefs.getLong("lastBonusTimestamp", 0L)
        set(value) = sharedPrefs.edit().putLong("lastBonusTimestamp", value).apply()

    fun isBonusAvailable(): Boolean {
        val currentTime = System.currentTimeMillis()
        val fiveHoursInMillis = 5 * 60 * 60 * 1000L
        return currentTime - lastBonusTimestamp >= fiveHoursInMillis
    }

    // Claim bonus and update the timestamp
    fun claimBonus() {
        lastBonusTimestamp = System.currentTimeMillis()
    }
}