package com.reihanalavi.jetpackdogs.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Dog(
    @SerializedName("id")
    @ColumnInfo(name = "breed_id") val breedId: String?,

    @SerializedName("name")
    @ColumnInfo(name = "dog_name") val dogBreed: String?,

    @SerializedName("life_span")
    @ColumnInfo(name = "life_span") val lifeSpan: String?,

    @SerializedName("breed_group")
    @ColumnInfo(name = "breed_group") val breedGroup: String?,

    @SerializedName("bred_for")
    @ColumnInfo(name = "bred_for") val bredFor: String?,

    @SerializedName("temperament")
    @ColumnInfo(name = "dog_temperament") val temperament: String?,

    @SerializedName("url")
    @ColumnInfo(name = "image_url") val imageUrl: String?
) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

}