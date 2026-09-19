package app.titech.titechPortalCore.http

class BaseURL {
    companion object {
        var origin = "https://portal.nap.gsic.titech.ac.jp"

        fun changeToMock() {
            origin = "https://portal-mock.titech.app"
        }
    }
}