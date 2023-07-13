package app.titech.titechPortalCore.`object`

enum class HTMLInputType(val rawValue: String) {
    Text("text"),
    Password("password"),
    Checkbox("checkbox"),
    Radio("radio"),
    File("file"),
    Hidden("hidden"),
    Submit("submit"),
    Reset("reset"),
    Button("button"),
    Image("image");

    companion object {
        fun fromString(str: String): HTMLInputType {
            return values().firstOrNull { it.rawValue == str } ?: Text
        }
    }
}
