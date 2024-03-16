package com.example.android_korobkov.l.android_korobkov_5transport.transportBaseAdapter

data class TransportModel(
    val name: String,
    val type: String,
    val capacity: String,
    val axleCount: Int,
    val isShowRemoveIcon: Boolean = false
) {
    override fun toString(): String {
        return name
    }
}
