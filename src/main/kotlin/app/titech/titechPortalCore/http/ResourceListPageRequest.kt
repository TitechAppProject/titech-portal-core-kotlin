package app.titech.titechPortalCore.http

import java.net.HttpCookie

class ResourceListPageRequest: HTTPRequest {
    override val baseURL: String = BaseURL.origin
    override val httpMethod: String = "GET"
    override val path: String = "/GetAccess/ResourceList"
    override val queryParameters: Map<String, Any>? = null
    override val headerFields: Map<String, String>? = mapOf(
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language" to "ja-jp"
    )
    override val body: Map<String, String>? = null
}