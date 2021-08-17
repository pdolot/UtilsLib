package com.dolotdev.utils.processing.attributes

import android.content.res.TypedArray

internal interface AttributeStrategy<T> {
	fun getValue(attrs: TypedArray): T
	fun getAttributeName(): String
}