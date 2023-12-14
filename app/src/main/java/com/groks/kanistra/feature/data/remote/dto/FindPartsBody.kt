package com.groks.kanistra.feature.data.remote.dto

data class FindPartsBody(
    val name: String?,
    val id: String?,
    val sort: Sort
)

sealed class Sort(val sortType: SortType) {
    class Title(sortType: SortType): Sort(sortType)
    class Date(sortType: SortType): Sort(sortType)
    class Color(sortType: SortType): Sort(sortType)

    fun copy(sortType: SortType): Sort {
        return when (this){
            is Title -> Title(sortType)
            is Date -> Date(sortType)
            is Color -> Color(sortType)
        }
    }
}

sealed class SortType {
    object Ascending: SortType()
    object Descending: SortType()
}