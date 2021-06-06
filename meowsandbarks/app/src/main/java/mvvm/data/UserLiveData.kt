package mvvm.data

data class UserLiveData(
        var username: String? = null,
        var password: String? = null,
        var email: String? = null,
        val profile: ProfileLiveData = ProfileLiveData())