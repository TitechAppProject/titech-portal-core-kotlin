package app.titech.titechPortalCore.`object`

data class TitechPortalAccount(
    val username: String,
    val password: String,
    val matrixcode: Map<TitechPortalMatrix, String>
)