package app.titech.titechPortalCore.`object`

data class HTMLSelect(
    val name: String,
    val index: Int,
    val values: List<String>
) {
    fun selectedValue(): String {
        return if (index < values.count()) {
            values[index]
        } else {
            ""
        }
    }

    fun select(value: String): HTMLSelect {
        val index = values.indexOf(value)
        return if (index != -1) {
            HTMLSelect(name, index, values)
        } else {
            this
        }
    }
}