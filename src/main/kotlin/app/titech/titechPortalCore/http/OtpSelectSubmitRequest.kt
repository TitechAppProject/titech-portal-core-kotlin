package app.titech.titechPortalCore.http

import app.titech.titechPortalCore.`object`.HTMLInput
import app.titech.titechPortalCore.`object`.HTMLSelect
import java.net.URL

class OtpSelectSubmitRequest(
    htmlInputs: List<HTMLInput>,
    htmlSelects: List<HTMLSelect>,
    referer: URL?
) : HTTPRequest {
    override val baseURL: String = BaseURL.origin
    override val httpMethod: String = "POST"
    override val path: String = "/GetAccess/Login"
    override val queryParameters: Map<String, Any>? = null
    override val headerFields: Map<String, String>? = mapOf(
        "Origin" to BaseURL.origin,
        "Content-Type" to "application/x-www-form-urlencoded",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language" to "ja-jp"
    ) + if (referer != null) mapOf("Referer" to referer.toString()) else emptyMap()
    override val body: Map<String, String>?
        = htmlInputs.associate { Pair(it.name, it.value) } + htmlSelects.associate { Pair(it.name, it.selectedValue()) }
}