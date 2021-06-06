package mvvm.data

class CountryMap {

    companion object {
        val instance = mapOf(
                Pair("Ukraine", listOf("Kyiv", "Slavutych")),
                Pair("USA", listOf("New York", "Houston"))
        )
    }
}