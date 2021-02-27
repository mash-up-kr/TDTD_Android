package com.tdtd.presentation.entity

data class Dummy(
    var id: Long?,
    var content: String,
    var date: String
)
fun getData(): List<Dummy> {
    return listOf(
        Dummy(
            0,
            "ㅇㅣ것은 ㅌㅔ스트 입니다",
            "2020/21/32"
        ),
        Dummy(
            1,
            "ㅇㅣ것은 ㅌㅔ스트 입니다",
            "2020/21/32"
        ),
        Dummy(
            2,
            "ㅇㅣ것은 ㅌㅔ스트 입니다",
            "2020/21/32"
        ),
        Dummy(
            3,
            "ㅇㅣ것은 ㅌㅔ스트 입니다",
            "2020/21/32"
        ),
    )
}
