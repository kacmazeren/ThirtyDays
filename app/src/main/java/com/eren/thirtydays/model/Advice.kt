package com.eren.thirtydays.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Advice(
    @StringRes val adviceString: Int,
    @DrawableRes val imageRes: Int
)
