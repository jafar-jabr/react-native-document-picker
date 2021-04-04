package com.jafar.reactnative.documentpicker.utils

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Callback

class ResponseHelper {
  var response = Arguments.createMap()
    private set

  fun cleanResponse() {
    response = Arguments.createMap()
  }

  fun putString(key: String,
                value: String) {
    response.putString(key, value)
  }

  fun putInt(key: String,
             value: Int) {
    response.putInt(key, value)
  }

  fun putBoolean(key: String,
                 value: Boolean) {
    response.putBoolean(key, value)
  }

  fun putDouble(key: String,
                value: Double) {
    response.putDouble(key, value)
  }

  fun invokeCustomButton(callback: Callback,
                         action: String) {
    cleanResponse()
    response.putString("customButton", action)
    invokeResponse(callback)
  }

  fun invokeCancel(callback: Callback) {
    cleanResponse()
    response.putBoolean("didCancel", true)
    invokeResponse(callback)
  }

  fun invokeError(callback: Callback,
                  errorCode: Number, errorMessage: String) {
    cleanResponse()
    response.putInt("code", errorCode as Int)
    response.putString("message", errorMessage)
    callback.invoke(response, null)
  }

  fun invokeResponse(callback: Callback) {
    callback.invoke(null, response)
  }
}

