package campagnolo.cantiero.kartapp.services.listener

class FileValidator(errorMessage: String = "") {
    private var mStatus: Boolean = true
    private var mValidationMessage: String = ""

    init {
        if (errorMessage != "") {
            mStatus = false
            mValidationMessage = errorMessage
        }
    }

    fun success() = mStatus
    fun failure() = mValidationMessage
}