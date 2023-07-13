package app.titech.titechPortalCore

import app.titech.titechPortalCore.http.*
import app.titech.titechPortalCore.`object`.*
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.HttpCookie

class TitechPortalLoginPasswordPageValidationError : Exception()
class TitechPortalLoginAlreadyLoggedinError : Exception()
class TitechPortalLoginNoMatrixcodeOptionError : Exception()
class TitechPortalLoginMatrixcodePageValidationError : Exception()
class TitechPortalLoginResourceListPageValidationError(val currentMatrixs: List<TitechPortalMatrix>) : Exception()

class TitechPortal(
    val httpClient: HTTPClient = HTTPClientImpl()
) {
    /// TitechPortalにログイン
    /// - Parameter account: ログイン情報
    suspend fun login(account: TitechPortalAccount): List<HttpCookie> {
        /// パスワードページの取得
        val passwordPageHtml = fetchPasswordPage()
        /// パスワードページのバリデーション
        val validatePasswordPageResult = validatePasswordPage(passwordPageHtml)
        if (!validatePasswordPageResult) {
            throw TitechPortalLoginPasswordPageValidationError()
        }
        /// パスワードページのInputsのパース
        val passwordPageInputs = parseHTMLInput(passwordPageHtml)
        /// パスワードFormの送信
        val passwordPageSubmitHtml = submitPassword(passwordPageInputs, account.username, account.password)
        /// すでにログインセッションがある場合はパスワード入力後にすぐにResourceListページに飛ぶ
        if (validateResourceListPage(passwordPageSubmitHtml)) {
            throw TitechPortalLoginAlreadyLoggedinError()
        }
        val matrixcodePageHtml: String = if (validateOtpPage(passwordPageSubmitHtml)) {
            /// OTP選択ページのInputsのパース
            val otpSelectPageInputs = parseHTMLInput(passwordPageSubmitHtml)
            /// OTP選択ページのSelectのパース
            val otpSelectPageSelects = parseHTMLSelect(passwordPageSubmitHtml)
            /// OTP選択ページのSelectにGridAuthOptionが含まれているか確認
            /// これがない場合マトリクス認証が許可されていない
            val hasMatrixcodeOption = otpSelectPageSelects.any {
                it.values.contains("GridAuthOption")
            }
            if (!hasMatrixcodeOption) {
                throw TitechPortalLoginNoMatrixcodeOptionError()
            }
            /// OTP選択Formの送信
            submitOtpSelect(otpSelectPageInputs, otpSelectPageSelects)
        } else {
            passwordPageSubmitHtml
        }
        /// マトリクスコードページのバリデーション
        if (!validateMatrixcodePage(matrixcodePageHtml)) {
            throw TitechPortalLoginMatrixcodePageValidationError()
        }
        /// マトリクスコード入力ページのInputsのパース
        val matrixcodePageInputs = parseHTMLInput(matrixcodePageHtml)
        /// マトリクスコード入力ページのCurrentMatrixのパース
        val matrixcodePageCurrentMatrix = parseCurrentMatrixes(matrixcodePageHtml)
        ///マトリクスコード入力ページのSelectのパース
        val matrixcodePageSelects = parseHTMLSelect(matrixcodePageHtml)
        /// マトリクスコードFormの送信
        val matrixcodePageSubmitHtml = submitMatrixcode(matrixcodePageInputs, matrixcodePageSelects, matrixcodePageCurrentMatrix, account.matrixcode)
        /// リソースリストページのバリデーション
        if (!validateResourceListPage(matrixcodePageSubmitHtml)) {
            throw TitechPortalLoginResourceListPageValidationError(matrixcodePageCurrentMatrix)
        }

        return httpClient.cookies.map {
            HttpCookie(it.name, it.value)
        }
    }

    /// UsernameとPasswordのみが正しいかチェック
    /// - Parameter account: チェックするアカウント情報
    /// - Returns: 正しくログインできればtrue, エラーであればfalseを返す
    suspend fun checkUsernamePassword(username: String, password: String): Boolean {
        /// パスワードページの取得
        val passwordPageHtml = fetchPasswordPage()
        /// パスワードページのバリデーション
        val validatePasswordPageResult = validatePasswordPage(passwordPageHtml)
        if (!validatePasswordPageResult) {
            throw TitechPortalLoginPasswordPageValidationError()
        }
        /// パスワードページのInputsのパース
        val passwordPageInputs = parseHTMLInput(passwordPageHtml)
        /// パスワードFormの送信
        val passwordPageSubmitHtml = submitPassword(passwordPageInputs, username, password)

        return validateOtpPage(passwordPageSubmitHtml) || validateMatrixcodePage(passwordPageSubmitHtml)
    }

    /// ログイン済みかを判定
    /// - Returns: ログイン済みのセッションであればtrue、ログイン済みでなければfalse
    suspend fun isLoggedIn(cookies: List<HttpCookie>): Boolean {
        val statusCode = httpClient.statusCode(ResourceListPageRequest(), cookies.toSet())

        return statusCode == 200
    }

    /// 現在のマトリクスを取得
    /// - Parameter account: ログイン情報
    /// - Returns: 現在のマトリクス
    suspend fun fetchCurrentMatrix(username: String, password: String): List<TitechPortalMatrix> {
        /// パスワードページの取得
        val passwordPageHtml = fetchPasswordPage()
        /// パスワードページのバリデーション
        val validatePasswordPageResult = validatePasswordPage(passwordPageHtml)
        if (!validatePasswordPageResult) {
            throw TitechPortalLoginPasswordPageValidationError()
        }
        /// パスワードページのInputsのパース
        val passwordPageInputs = parseHTMLInput(passwordPageHtml)
        /// パスワードFormの送信
        val passwordPageSubmitHtml = submitPassword(passwordPageInputs, username, password)
        /// すでにログインセッションがある場合はパスワード入力後にすぐにResourceListページに飛ぶ
        if (validateResourceListPage(passwordPageSubmitHtml)) {
            throw TitechPortalLoginAlreadyLoggedinError()
        }
        val matrixcodePageHtml: String = if (validateOtpPage(passwordPageSubmitHtml)) {
            /// OTP選択ページのInputsのパース
            val otpSelectPageInputs = parseHTMLInput(passwordPageSubmitHtml)
            /// OTP選択ページのSelectのパース
            val otpSelectPageSelects = parseHTMLSelect(passwordPageSubmitHtml)
            /// OTP選択ページのSelectにGridAuthOptionが含まれているか確認
            /// これがない場合マトリクス認証が許可されていない
            val hasMatrixcodeOption = otpSelectPageSelects.any {
                it.values.contains("GridAuthOption")
            }
            if (!hasMatrixcodeOption) {
                throw TitechPortalLoginNoMatrixcodeOptionError()
            }
            /// OTP選択Formの送信
            submitOtpSelect(otpSelectPageInputs, otpSelectPageSelects)
        } else {
            passwordPageSubmitHtml
        }
        /// マトリクスコードページのバリデーション
        if (!validateMatrixcodePage(matrixcodePageHtml)) {
            throw TitechPortalLoginMatrixcodePageValidationError()
        }
        /// マトリクスコード入力ページのCurrentMatrixのパース
        return parseCurrentMatrixes(matrixcodePageHtml)
    }

    private suspend fun fetchPasswordPage(): String  = httpClient.send(PasswordPageRequest())

    internal fun validatePasswordPage(html: String): Boolean {
        val bodyHTML = Jsoup.parse(html).body().html()

        return bodyHTML.contains("Please input your account &amp; password.")
    }

    private suspend fun submitPassword(htmlInputs: List<HTMLInput>, username: String, password: String): String {
        val injectedHtmlInputs = inject(htmlInputs, username, password)

        val request = PasswordSubmitRequest(injectedHtmlInputs)

        return httpClient.send(request)
    }

    internal fun validateOtpPage(html: String): Boolean {
        val bodyHTML = Jsoup.parse(html).body().html()

        return bodyHTML.contains("Select Label for OTP") || bodyHTML.contains("Enter Token Dynamic Password")
    }

    private suspend fun submitOtpSelect(htmlInputs: List<HTMLInput>, htmlSelects: List<HTMLSelect>): String {
        val selectedHtmlSelects = htmlSelects.map {
            it.select("GridAuthOption")
        }

        val request = OtpSelectSubmitRequest(htmlInputs, selectedHtmlSelects)

        return httpClient.send(request)
    }

    private suspend fun submitMatrixcode(htmlInputs: List<HTMLInput>, htmlSelects: List<HTMLSelect>, parsedMatrix: List<TitechPortalMatrix>, matrixcodes: Map<TitechPortalMatrix, String>): String {
        val injectedHtmlInputs = inject(htmlInputs, parsedMatrix, matrixcodes)

        val injectedSelects = htmlSelects.map {
            if (it.values.contains("NoOtherIGAuthOption")) {
                it.select("NoOtherIGAuthOption")
            } else {
                it
            }
        }

        val request = MatrixcodeSubmitRequest(injectedHtmlInputs, injectedSelects)

        return httpClient.send(request)
    }

    internal fun validateMatrixcodePage(html: String): Boolean {
        val bodyHTML = Jsoup.parse(html).body().html()

        return bodyHTML.contains("Matrix Authentication")
    }

    private fun parseCurrentMatrixes(html: String): List<TitechPortalMatrix> {
        val matrixArr = "\\[([A-J]{1}),([1-7]{1})]".toRegex().findAll(html)

        return matrixArr.mapNotNull {
            val alphabet = it.groupValues.get(1)
            val numberString = it.groupValues.get(2)

            try {
                TitechPortalMatrix.valueOf(alphabet + numberString)
            } catch(e: Exception) {
                null
            }
        }.toList()
    }

    internal fun validateResourceListPage(html: String): Boolean {
        val title = Jsoup.parse(html).title()

        return title.contains("リソース メニュー")
    }

    internal fun parseHTMLInput(html: String): List<HTMLInput> {
        val doc = Jsoup.parse(html)
        return doc.select("input")
            .map {
                HTMLInput(
                    it.attr("name"),
                    HTMLInputType.fromString(it.attr("type")),
                    it.attr("value")
                )
            }
    }

    private fun parseHTMLSelect(html: String): List<HTMLSelect> {
        val doc = Jsoup.parse(html)
        return doc.select("select")
            .map {
                HTMLSelect(
                    it.attr("name"),
                    0,
                    it.select("option").map { it.attr("value") }
                )
            }
            .filter { it.values.isNotEmpty() }
    }

    private fun inject(inputs: List<HTMLInput>, username: String, password: String): List<HTMLInput> {
        val firstTextInput = inputs.firstOrNull { it.type == HTMLInputType.Text } ?: return inputs
        val firstPasswordInput = inputs.firstOrNull { it.type == HTMLInputType.Password } ?: return inputs

        return inputs.map {
            if (it == firstTextInput) {
                it.value = username
            }
            if (it == firstPasswordInput) {
                it.value = password
            }
            it
        }
    }

    private fun inject(inputs: List<HTMLInput>, parsedMatrix: List<TitechPortalMatrix>, matrixcodes: Map<TitechPortalMatrix, String>): List<HTMLInput> {
        if (parsedMatrix.isEmpty()) {
            return inputs
        }

        var index = 0

        return inputs.map { input ->
            if (input.type == HTMLInputType.Password) {
                if (parsedMatrix.count() > index) {
                    input.value = matrixcodes[parsedMatrix[index]] ?: ""
                    index += 1
                }
            }
            input
        }
    }

    companion object {
        fun changeToMock() {
            BaseURL.changeToMock()
        }
    }
}