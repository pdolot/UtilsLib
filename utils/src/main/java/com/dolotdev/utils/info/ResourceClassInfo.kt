package com.dolotdev.utils.info

import android.content.Context

internal object ResourceClassInfo {

	fun getStyleableClass(context: Context): Class<*> {
		val packageName = context.packageName
		return Class.forName("$packageName.R\$styleable")
	}
}