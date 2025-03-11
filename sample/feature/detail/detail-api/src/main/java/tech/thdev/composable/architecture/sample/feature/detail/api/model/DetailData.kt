package tech.thdev.composable.architecture.sample.feature.detail.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailData(
    val text: String,
) : Parcelable
