package app.titech.titechPortalCore.http

import java.net.HttpCookie
import java.net.URL

data class HTTPResponse(
    val body: String,
    val url: URL?
)

interface HTTPClient {
    suspend fun send(request: HTTPRequest): HTTPResponse
    suspend fun statusCode(request: HTTPRequest, cookies: Set<HttpCookie>): Int
    val cookies: Set<HttpCookie>
}