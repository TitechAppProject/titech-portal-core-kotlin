package app.titech.titechPortalCore.http

import app.titech.titechPortalCore.`object`.HTMLInput
import app.titech.titechPortalCore.`object`.HTMLSelect
import java.net.HttpCookie

class OtpSelectSubmitRequest(
    htmlInputs: List<HTMLInput>,
    htmlSelects: List<HTMLSelect>
) : HTTPRequest {
    override val baseURL: String = BaseURL.origin
    override val httpMethod: String = "POST"
    override val path: String = "/GetAccess/Login"
    override val queryParameters: Map<String, Any>? = null
    override val headerFields: Map<String, String>? = mapOf(
        "Referer" to BaseURL.origin + "/GetAccess/Login?Template=idg_key&AUTHMETHOD=IG&GASF=CERTIFICATE,IG.GRID,IG.TOKENRO,IG.OTP&LOCALE=ja_JP&GAREASONCODE=13&GAIDENTIFICATIONID=UserPassword&GARESOURCEID=resourcelistID2&GAURI=https://portal.nap.gsic.titech.ac.jp/GetAccess/ResourceList&Reason=13&APPID=resourcelistID2&URI=https://portal.nap.gsic.titech.ac.jp/GetAccess/ResourceList",
        "Host" to BaseURL.host,
        "Origin" to BaseURL.origin,
        "Content-Type" to "application/x-www-form-urlencoded",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language" to "ja-jp"
    )
    override val body: Map<String, String>?
        = htmlInputs.associate { Pair(it.name, it.value) } + htmlSelects.associate { Pair(it.name, it.selectedValue()) }
}