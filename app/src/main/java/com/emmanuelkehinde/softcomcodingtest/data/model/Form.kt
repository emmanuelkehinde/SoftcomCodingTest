package com.emmanuelkehinde.softcomcodingtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Form(
    var id: String,
    var name: String,
    var pages: ArrayList<Page>
) : Parcelable

@Parcelize
data class Page(
    var label: String,
    var sections: List<Section>
) : Parcelable

@Parcelize
data class Section(
    var label: String,
    var elements: List<Element>
) : Parcelable

@Parcelize
data class Element(
    @SerializedName("unique_id")
    var uniqueId: String,

    var type: ElementType?,
    var mode: Mode?,
    var label: String?,
    var keyboard: KeyboardType?,
    var formattedNumeric: String?,
    var file: String?,
    var isMandatory: Boolean?,
    var rules: List<Rule>
) : Parcelable

@Parcelize
data class Rule(
    var condition: Condition?,
    var value: Value?,
    var action: Action?,
    var otherwise: String,
    var targets: List<String>
) : Parcelable

@Parcelize
enum class ElementType : Parcelable {
    @SerializedName("embedded-photo")
    EMBEDDED_PHOTO,

    @SerializedName("text")
    TEXT,

    @SerializedName("formattednumeric")
    FORMATTED_NUMERIC,

    @SerializedName("datetime")
    DATE_TIME,

    @SerializedName("yesno")
    YES_NO
}

@Parcelize
enum class KeyboardType : Parcelable {
    @SerializedName("numeric")
    NUMERIC
}

@Parcelize
enum class Mode : Parcelable {
    @SerializedName("date")
    DATE
}

@Parcelize
enum class Condition : Parcelable {
    @SerializedName("equals")
    EQUALS
}

@Parcelize
enum class Action : Parcelable {
    @SerializedName("show")
    SHOW
}

@Parcelize
enum class Value : Parcelable {
    @SerializedName("Yes")
    YES,

    @SerializedName("No")
    NO
}