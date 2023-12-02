package com.bytebloomlabs.nestlink.models

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.EggData

// a singleton to hold user data (this is a ViewModel pattern, without inheriting from ViewModel)
object UserData {
    private const val TAG = "UserData"

    //
    // observable properties
    //

    // signed in status
    private val _isSignedIn = MutableLiveData<Boolean>(false)
    var isSignedIn: LiveData<Boolean> = _isSignedIn

    fun test() {}

    fun setSignedIn(newValue : Boolean) {
        // use postvalue() to make the assignation on the main (UI) thread
        _isSignedIn.postValue(newValue)
    }

    // the egg data points
    private val _eggDataPoints = MutableLiveData<MutableList<EggDataPoints>>(mutableListOf())

    // please check https://stackoverflow.com/questions/47941537/notify-observer-when-item-is-added-to-list-of-livedata
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        _eggDataPoints.notifyObserver()
    }
    fun eggDataPoints() : LiveData<MutableList<EggDataPoints>> = _eggDataPoints

    // should be used only in testing for now
    fun addEggDataPoint(data: EggDataPoints) {
        val eggData = _eggDataPoints.value

        if (eggData != null) {
            eggData.add(data)
            _eggDataPoints.notifyObserver()
        } else {
            Log.e(TAG, "addEggDataPoint: note collection is null")
        }
    }

    fun deleteEggDataPoint(at: Int) : EggDataPoints? {
        val data = _eggDataPoints.value?.removeAt(at)
        _eggDataPoints.notifyObserver()
        return data
    }

    fun resetEggData() {
        _eggDataPoints.value?.clear()
        _eggDataPoints.notifyObserver()
    }

    // a note data class
    data class Note(val id: String, val name: String, val description: String, var imageName: String? = null) {
        override fun toString(): String = name

        // bitmap image
        var image : Bitmap? = null

    }

    data class EggDataPoints(val id: String, val telemetry: ByteArray, val telemetryTimestamp: String, val eggType: String) {
        override fun toString(): String = "$id-$telemetryTimestamp-$eggType"

        //ToDo: implement proper parsing, or change value type to string
        val fakeDecodedString: String = telemetry.toString(Charsets.UTF_8)

        val data: EggData
            get() = EggData.builder()
                .id(this.id)
                .telemetry(fakeDecodedString)
                .telemetryTimestamp(this.telemetryTimestamp)
                .eggType(this.eggType)
                .build()

        companion object {
            fun from(eggData: EggData) : EggDataPoints {
                val telem: ByteArray = eggData.telemetry.toByteArray(Charsets.UTF_8)

                val result = EggDataPoints(eggData.id, telem, eggData.telemetryTimestamp, eggData.eggType)
                return result
            }
        }

    }

}