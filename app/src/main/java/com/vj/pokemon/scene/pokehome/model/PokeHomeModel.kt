package com.vj.pokemon.scene.pokehome.model

import android.os.Parcel
import android.os.Parcelable

class PokeHomeModel(var id: Int? = null ,
                      var name: String? = null,
                      var url: String? = null)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let { parcel.writeInt(it) }
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokeHomeModel> {
        override fun createFromParcel(parcel: Parcel): PokeHomeModel {
            return PokeHomeModel(parcel)
        }

        override fun newArray(size: Int): Array<PokeHomeModel?> {
            return arrayOfNulls(size)
        }
    }
}