package app.titech.titechPortalCore

import app.titech.titechPortalCore.`object`.HTMLInput
import app.titech.titechPortalCore.`object`.HTMLInputType
import app.titech.titechPortalCore.`object`.TitechPortalAccount
import app.titech.titechPortalCore.`object`.TitechPortalMatrix
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TitechPortalTest {
    @Test
    fun testPasswordPageValidation() {
        val portal = TitechPortal()

        val html = TitechPortalTest::class.java.getResource("/html/password_page.html")!!.readText()

        assertTrue { portal.validatePasswordPage(html) }
    }

    @Test
    fun testPasswordPageParseHTMLInputs() {
        val portal = TitechPortal()

        val html = TitechPortalTest::class.java.getResource("/html/password_page.html")!!.readText()

        val passwordPageInputs = portal.parseHTMLInput(html)

        assertEquals(
            listOf(
                HTMLInput("usr_name", HTMLInputType.Text, ""),
                HTMLInput("usr_password", HTMLInputType.Password, ""),
                HTMLInput("OK", HTMLInputType.Submit, "    OK    "),
                HTMLInput("AUTHTYPE", HTMLInputType.Hidden, ""),
                HTMLInput("HiddenURI", HTMLInputType.Hidden, "https://portal.nap.gsic.titech.ac.jp/GetAccess/ResourceList"),
                HTMLInput("Template", HTMLInputType.Hidden, "userpass_key"),
                HTMLInput("AUTHMETHOD", HTMLInputType.Hidden, "UserPassword"),
                HTMLInput("pageGenTime", HTMLInputType.Hidden, "100"),
                HTMLInput("LOCALE", HTMLInputType.Hidden, "ja_JP"),
                HTMLInput("CSRFFormToken", HTMLInputType.Hidden, "CSRFFormTokenValue")
            ),
            passwordPageInputs
        )
    }

    @Test
    fun testValidateOtpPageForMatrixcodePage() {
        val portal = TitechPortal()

        val html = TitechPortalTest::class.java.getResource("/html/matrix_code_page.html")!!.readText()

        assertFalse { portal.validateOtpPage(html) }
    }

    @Test
    fun testValidateOtpPageForOTPSelectPage() {
        val portal = TitechPortal()

        val html = TitechPortalTest::class.java.getResource("/html/otp_select_page.html")!!.readText()

        assertTrue { portal.validateOtpPage(html) }
    }

    @Test
    fun testValidateOtpPageForTOTP() {
        val portal = TitechPortal()

        val html = TitechPortalTest::class.java.getResource("/html/totp_page.html")!!.readText()

        assertTrue { portal.validateOtpPage(html) }
    }

    @Test
    fun testResourceMenuValidation() {
        val portal = TitechPortal()

        val html = TitechPortalTest::class.java.getResource("/html/resource_list_page-ja.html")!!.readText()

        assertTrue { portal.validateResourceListPage(html) }
    }

    @Test
    fun login() {
        TitechPortal.changeToMock()
        val portal = TitechPortal()

        runBlocking {
            portal.login(
                TitechPortalAccount(
                    "00B00000",
                    "passw0rd&",
                    TitechPortalMatrix.entries.associateWith { "A" }
                )
            )
        }
    }
}