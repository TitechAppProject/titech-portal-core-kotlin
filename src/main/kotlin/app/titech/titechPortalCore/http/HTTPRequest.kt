package app.titech.titechPortalCore.http

interface HTTPRequest {
    val baseURL: String
    val httpMethod: String
    val path: String
    val queryParameters: Map<String, Any>?
    val headerFields: Map<String, String>?
    val body: Map<String, String>?
}