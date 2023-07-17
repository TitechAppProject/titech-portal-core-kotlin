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
    fun loginToProdServer() {
        TitechPortal.changeToMock()
        val portal = TitechPortal()

        runBlocking {
            portal.login(
                TitechPortalAccount(
                    "00B00000",
                    "passw0rd&",
                    mapOf(
                        TitechPortalMatrix.A1 to "A",
                        TitechPortalMatrix.A2 to "A",
                        TitechPortalMatrix.A3 to "A",
                        TitechPortalMatrix.A4 to "A",
                        TitechPortalMatrix.A5 to "A",
                        TitechPortalMatrix.A6 to "A",
                        TitechPortalMatrix.A7 to "A",
                        TitechPortalMatrix.B1 to "A",
                        TitechPortalMatrix.B2 to "A",
                        TitechPortalMatrix.B3 to "A",
                        TitechPortalMatrix.B4 to "A",
                        TitechPortalMatrix.B5 to "A",
                        TitechPortalMatrix.B6 to "A",
                        TitechPortalMatrix.B7 to "A",
                        TitechPortalMatrix.C1 to "A",
                        TitechPortalMatrix.C2 to "A",
                        TitechPortalMatrix.C3 to "A",
                        TitechPortalMatrix.C4 to "A",
                        TitechPortalMatrix.C5 to "A",
                        TitechPortalMatrix.C6 to "A",
                        TitechPortalMatrix.C7 to "A",
                        TitechPortalMatrix.D1 to "A",
                        TitechPortalMatrix.D2 to "A",
                        TitechPortalMatrix.D3 to "A",
                        TitechPortalMatrix.D4 to "A",
                        TitechPortalMatrix.D5 to "A",
                        TitechPortalMatrix.D6 to "A",
                        TitechPortalMatrix.D7 to "A",
                        TitechPortalMatrix.E1 to "A",
                        TitechPortalMatrix.E2 to "A",
                        TitechPortalMatrix.E3 to "A",
                        TitechPortalMatrix.E4 to "A",
                        TitechPortalMatrix.E5 to "A",
                        TitechPortalMatrix.E6 to "A",
                        TitechPortalMatrix.E7 to "A",
                        TitechPortalMatrix.F1 to "A",
                        TitechPortalMatrix.F2 to "A",
                        TitechPortalMatrix.F3 to "A",
                        TitechPortalMatrix.F4 to "A",
                        TitechPortalMatrix.F5 to "A",
                        TitechPortalMatrix.F6 to "A",
                        TitechPortalMatrix.F7 to "A",
                        TitechPortalMatrix.G1 to "A",
                        TitechPortalMatrix.G2 to "A",
                        TitechPortalMatrix.G3 to "A",
                        TitechPortalMatrix.G4 to "A",
                        TitechPortalMatrix.G5 to "A",
                        TitechPortalMatrix.G6 to "A",
                        TitechPortalMatrix.G7 to "A",
                        TitechPortalMatrix.H1 to "A",
                        TitechPortalMatrix.H2 to "A",
                        TitechPortalMatrix.H3 to "A",
                        TitechPortalMatrix.H4 to "A",
                        TitechPortalMatrix.H5 to "A",
                        TitechPortalMatrix.H6 to "A",
                        TitechPortalMatrix.H7 to "A",
                        TitechPortalMatrix.I1 to "A",
                        TitechPortalMatrix.I2 to "A",
                        TitechPortalMatrix.I3 to "A",
                        TitechPortalMatrix.I4 to "A",
                        TitechPortalMatrix.I5 to "A",
                        TitechPortalMatrix.I6 to "A",
                        TitechPortalMatrix.I7 to "A",
                        TitechPortalMatrix.J1 to "A",
                        TitechPortalMatrix.J2 to "A",
                        TitechPortalMatrix.J3 to "A",
                        TitechPortalMatrix.J4 to "A",
                        TitechPortalMatrix.J5 to "A",
                        TitechPortalMatrix.J6 to "A",
                        TitechPortalMatrix.J7 to "A"
                    )
                )
            )
        }
    }
}