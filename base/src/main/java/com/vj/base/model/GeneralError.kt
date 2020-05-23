package com.vj.base.model

data class GeneralError(val errorCode: String, val errorMessage: String, val body: String? = null)