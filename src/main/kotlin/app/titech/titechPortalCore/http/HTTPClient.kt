package app.titech.titechPortalCore.http

import java.net.HttpCookie

interface HTTPClient {
    suspend fun send(request: HTTPRequest): String
    suspend fun statusCode(request: HTTPRequest, cookies: Set<HttpCookie>): Int
    val cookies: Set<HttpCookie>
}