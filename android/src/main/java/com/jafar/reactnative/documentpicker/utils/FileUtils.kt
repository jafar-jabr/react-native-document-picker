package com.jafar.reactnative.documentpicker.utils

import android.content.Context
import android.graphics.BitmapFactory
import com.facebook.react.bridge.Callback
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.*


object FileUtils {
  private const val EOF = -1
  private const val DEFAULT_BUFFER_SIZE = 1024 * 4

  @Throws(IOException::class)
  fun from(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val fileName = getFileName(context, uri)
    val splitName = splitFileName(fileName)
    val tempName = if (splitName[0]!!.length < 3) splitName[0].toString() + "_doc" else splitName[0]!!
    var tempFile = File.createTempFile(tempName, splitName[1])
    tempFile = rename(tempFile, fileName)
    tempFile.deleteOnExit()
    var out: FileOutputStream? = null
    try {
      out = FileOutputStream(tempFile)
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    }
    if (inputStream != null) {
      copy(inputStream, out)
      inputStream.close()
    }
    out?.close()
    return tempFile
  }

  private fun splitFileName(fileName: String?): Array<String?> {
    var name = fileName
    var extension: String? = ""
    val i = fileName!!.lastIndexOf(".")
    if (i != -1) {
      name = fileName.substring(0, i)
      extension = fileName.substring(i)
    }
    return arrayOf(name, extension)
  }

  private fun getFileName(context: Context, uri: Uri): String? {
    var OrdersModel: String? = null
    if (uri.scheme == "content") {
      try {
        context.contentResolver.query(uri, null, null, null, null).use { cursor ->
          if (cursor != null && cursor.moveToFirst()) {
            OrdersModel = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    if (OrdersModel == null) {
      OrdersModel = uri.path
      val cut = OrdersModel!!.lastIndexOf(File.separator)
      if (cut != -1) {
        OrdersModel = OrdersModel!!.substring(cut + 1)
      }
    }
    return OrdersModel
  }

  private fun rename(file: File, newName: String?): File {
    val newFile = File(file.parent, newName)
    if (newFile != file) {
      if (newFile.exists()) {
        newFile.delete()
      }
      file.renameTo(newFile)
    }
    return newFile
  }

  @Throws(IOException::class)
  private fun copy(input: InputStream, output: OutputStream?): Long {
    var count: Long = 0
    var n: Int
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    while (EOF != input.read(buffer).also { n = it }) {
      output!!.write(buffer, 0, n)
      count += n.toLong()
    }
    return count
  }

  fun setUpResponseFromPath(filePath: String?, cb: Callback, errorCode: Number) {
    val responseHelper = ResponseHelper()
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(filePath, options)
    if (filePath != null) {
      responseHelper.putString("path", filePath)
    }
    try {
      // size && filename
      val f = File(filePath)
      responseHelper.putDouble("fileSize", f.length().toDouble())
      responseHelper.putString("fileName", f.name)
      // type
      var extension = MimeTypeMap.getFileExtensionFromUrl(filePath)
      val fileName = f.name
      if (extension != "") {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)?.let { responseHelper.putString("type", it) }
      } else {
        val i = fileName.lastIndexOf('.')
        if (i > 0) {
          extension = fileName.substring(i + 1)
          MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)?.let { responseHelper.putString("type", it) }
        }
      }
    } catch (e: Exception) {
      e.message?.let { responseHelper.invokeError(cb, errorCode, it) }
    }
    responseHelper.invokeResponse(cb)
  }
}
