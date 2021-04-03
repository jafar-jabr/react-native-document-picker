package com.jafar.reactnative.documentpicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.react.bridge.*
import com.jafar.reactnative.documentpicker.utils.FileUtils
import java.io.File
import java.io.IOException


class DocumentPickerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {

  val NAME = "RNDocumentPicker"
  private var AppContext: Context? = null
  private val PICK_FILE_REQUEST_CODE = 9321
  protected var callback: Callback? = null

    override fun getName(): String {
        return NAME
    }

  init {
    AppContext = reactContext.applicationContext
    reactContext.addActivityEventListener(this)
  }

    @ReactMethod
    fun doPicking(cb: Callback) {
      val currentActivity = currentActivity
      callback = cb
      val documentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
      documentIntent.type = "*/*"
      val mimetypes = arrayOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword", "application/pdf", "text/plain", "application/zip")
      documentIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
      assert(currentActivity != null)
      currentActivity!!.startActivityForResult(documentIntent, PICK_FILE_REQUEST_CODE)

    }

  private fun setUpPickingResponse(data: Intent) {
    val pickedImage: File
    val pickedImagePath: String
    try {
      pickedImage = AppContext?.let { data.data?.let { it1 -> FileUtils.from(it, it1) } }!!
      pickedImagePath = pickedImage.absolutePath
      FileUtils.setUpResponseFromPath(pickedImagePath, callback)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
      setUpPickingResponse(data)
    }
  }

  override fun onNewIntent(intent: Intent?) {}

}
