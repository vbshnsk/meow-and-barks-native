package mvvm.data

import android.graphics.Bitmap
import android.media.Image

data class PetLiveData(
    var name: String? = null,
    var age: Number? = null,
    var type: String? = null,
    var breed: String? = null,
    var image: Bitmap? = null)
