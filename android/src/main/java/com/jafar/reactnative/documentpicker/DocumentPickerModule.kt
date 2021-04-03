package com.jafar.reactnative.documentpicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.react.bridge.*
import com.jafar.reactnative.documentpicker.utils.FileUtils
import java.io.File
import java.io.IOException


class DocumentPickerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {

  private val name = "RNDocumentPicker"
  private var AppContext: Context? = null
  private var successCallback: Callback? = null
  private var errorCallback: Callback? = null

  companion object {
    private const val PICK_FILE_REQUEST_CODE = 9321
    private const val NO_ACTIVITY_ERROR_CODE = 404
    private const val FILE_NOT_FOUND_ERROR_CODE = 301
    private const val INTERNAL_ERROR_CODE = 400
  }

    override fun getName(): String {
        return name
    }

  init {
    AppContext = reactContext.applicationContext
    reactContext.addActivityEventListener(this)
  }

    @ReactMethod
    fun doPicking(error: Callback, response: Callback) {
      val currentActivity = currentActivity
      successCallback = response
      errorCallback = error
      val documentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
      documentIntent.type = "*/*"
      val mimetypes = arrayOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword", "application/pdf", "text/plain", "application/zip")
      documentIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
      if (currentActivity != null) {
        currentActivity.startActivityForResult(documentIntent, PICK_FILE_REQUEST_CODE)
      }else{
        error.invoke(NO_ACTIVITY_ERROR_CODE, "current activity is null")
      }
    }

  private fun setUpPickingResponse(data: Intent) {
    val pickedImage: File
    val pickedImagePath: String
    try {
      pickedImage = AppContext?.let { data.data?.let { it1 -> FileUtils.from(it, it1) } }!!
      pickedImagePath = pickedImage.absolutePath
      FileUtils.setUpResponseFromPath(pickedImagePath, successCallback, errorCallback, INTERNAL_ERROR_CODE)
    } catch (e: IOException) {
      errorCallback?.invoke(FILE_NOT_FOUND_ERROR_CODE, e.message)
    }
  }
  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
      setUpPickingResponse(data)
    }
  }

  override fun onNewIntent(intent: Intent?) {}
}
