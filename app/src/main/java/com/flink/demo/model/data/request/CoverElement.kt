package com.flink.demo.model.data.request

class CoverElement(
    var type: Type? = null,
    var data: Any? = null
){
    enum class Type(val value: Int){
        CARDS(0),
        ITEM_POST(1),
        LOADER(2)
    }
}