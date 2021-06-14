package mvvm.data

import android.media.Image

data class ProfileLiveData(
    var name: String? = null,
    val location: Location? = null,
    var birthDate: BirthDate? = null,
    val picture: Image? = null,
    var tags: MutableList<String> = mutableListOf(),
    val pets: MutableList<PetLiveData> = mutableListOf()
)
