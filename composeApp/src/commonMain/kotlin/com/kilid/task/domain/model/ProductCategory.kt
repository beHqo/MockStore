package com.kilid.task.domain.model

enum class ProductCategory(val rawName: String) {
    ELECTRONICS("electronics"),
    JEWELERY("jewelery"),
    MEN_CLOTHING("men's clothing"),
    WOMEN_CLOTHING("women's clothing"),
    OTHER("other"); // Fallback for any un-mapped string

    companion object {
        /**
         * Convert a raw JSON string (e.g. "men's clothing") into its Category enum.
         * If no match is found, returns OTHER.
         */
        fun fromRaw(raw: String): ProductCategory =
            entries.firstOrNull { it.rawName.equals(raw, ignoreCase = true) } ?: OTHER
    }
}