package app.titech.titechPortalCore.http

import app.titech.titechPortalCore.`object`.HTMLInput
import java.net.HttpCookie

class PasswordSubmitRequest(
    htmlInputs: List<HTMLInput>
) : HTTPRequest {
    override val baseURL: String = BaseURL.origin
    override val httpMethod: String = "POST"
    override val path: String = "/GetAccess/Login"
    override val queryParameters: Map<String, Any>? = null
    override val headerFields: Map<String, String>? = mapOf(
        "Referer" to BaseURL.origin + "/GetAccess/Login?Template=userpass_key&AUTHMETHOD=UserPassword",
        "Host" to BaseURL.host,
        "Origin" to BaseURL.origin,
        "Content-Type" to "application/x-www-form-urlencoded",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language" to "ja-jp"
    )
    override val body: Map<String, String>? = htmlInputs.associate { Pair(it.name, it.value) }
}