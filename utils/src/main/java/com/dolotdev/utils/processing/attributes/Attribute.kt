package com.dolotdev.utils.processing.attributes

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.*
import com.dolotdev.annotations.StyleableRes
import com.dolotdev.utils.R
import com.dolotdev.utils.info.ResourceClassInfo

internal sealed class Attribute<T>(private val styleable: StyleableRes) : AttributeStrategy<T> {

	lateinit var context: Context

	protected fun getAttributeIndex(): Int {
		return ResourceClassInfo.getStyleableClass(context).getField("${styleable.styleableResName}_${getAttributeName()}")
			.get(R.styleable::class.java) as Int
	}

	class BooleanAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.BooleanAttribute,
		private val throwable: Boolean
	) : Attribute<Boolean>(styleable) {

		override fun getValue(attrs: TypedArray): Boolean {
			return if (!throwable)
				attrs.getBoolean(getAttributeIndex(), annotation.defValue)
			else
				attrs.getBooleanOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class ColorAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.ColorAttribute,
		private val throwable: Boolean
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return if (!throwable)
				attrs.getColor(getAttributeIndex(), annotation.defValue)
			else
				attrs.getColorOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class ColorStateListAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.ColorStateListAttribute,
		private val throwable: Boolean
	) : Attribute<ColorStateList?>(styleable) {

		override fun getValue(attrs: TypedArray): ColorStateList? {
			return if (!throwable)
				attrs.getColorStateList(getAttributeIndex())
			else
				attrs.getColorStateListOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class DrawableAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.DrawableAttribute,
		private val throwable: Boolean
	) : Attribute<Drawable?>(styleable) {
		override fun getValue(attrs: TypedArray): Drawable? {
			return if (!throwable)
				attrs.getDrawable(getAttributeIndex())
			else
				attrs.getDrawableOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class DimensionAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.DimensionAttribute,
		private val throwable: Boolean
	) : Attribute<Float>(styleable) {

		override fun getValue(attrs: TypedArray): Float {
			return if (!throwable)
				attrs.getDimension(getAttributeIndex(), annotation.defValue)
			else
				attrs.getDimensionOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class DimensionPixelOffsetAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.DimensionPixelOffsetAttribute,
		private val throwable: Boolean
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return if (!throwable)
				attrs.getDimensionPixelOffset(getAttributeIndex(), annotation.defValue)
			else
				attrs.getDimensionPixelOffsetOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class DimensionPixelSizeAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.DimensionPixelSizeAttribute,
		private val throwable: Boolean
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return if (!throwable)
				attrs.getDimensionPixelOffset(getAttributeIndex(), annotation.defValue)
			else
				attrs.getDimensionPixelSizeOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class FloatAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.FloatAttribute,
		private val throwable: Boolean
	) : Attribute<Float>(styleable) {

		override fun getValue(attrs: TypedArray): Float {
			return if (!throwable)
				attrs.getFloat(getAttributeIndex(), annotation.defValue)
			else
				attrs.getFloatOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class FontAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.FontAttribute,
		private val throwable: Boolean
	) : Attribute<Typeface?>(styleable) {

		override fun getValue(attrs: TypedArray): Typeface? {
			return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				if (!throwable) {
					attrs.getFont(getAttributeIndex())
				} else {
					attrs.getFontOrThrow(getAttributeIndex())
				}
			else
				null
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class FractionAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.FractionAttribute
	) : Attribute<Float>(styleable) {

		override fun getValue(attrs: TypedArray): Float {
			return attrs.getFraction(getAttributeIndex(), annotation.base, annotation.pbase, annotation.defValue)
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class IntAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.IntAttribute,
		private val throwable: Boolean
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return if (!throwable)
				attrs.getInt(getAttributeIndex(), annotation.defValue)
			else
				attrs.getIntOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class IntegerAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.IntegerAttribute,
		private val throwable: Boolean
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return if (!throwable)
				attrs.getInteger(getAttributeIndex(), annotation.defValue)
			else
				attrs.getIntegerOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class LayoutDimensionAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.LayoutDimensionAttribute
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return attrs.getLayoutDimension(getAttributeIndex(), annotation.defValue)
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class NamedLayoutDimensionAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.NamedLayoutDimensionAttribute
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return attrs.getLayoutDimension(getAttributeIndex(), annotation.name)
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class NonResourceStringAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.NonResourceStringAttribute
	) : Attribute<String>(styleable) {

		override fun getValue(attrs: TypedArray): String {
			return attrs.getNonResourceString(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class ResourceAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.ResourceAttribute,
		private val throwable: Boolean
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return if (!throwable)
				attrs.getResourceId(getAttributeIndex(), annotation.defValue)
			else
				attrs.getResourceIdOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class SourceResourceAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.SourceResourceAttribute
	) : Attribute<Int?>(styleable) {

		override fun getValue(attrs: TypedArray): Int? {
			return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				attrs.getSourceResourceId(getAttributeIndex(), annotation.defValue)
			} else {
				null
			}
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class StringAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.StringAttribute,
		private val throwable: Boolean
	) : Attribute<String?>(styleable) {

		override fun getValue(attrs: TypedArray): String? {
			return if (!throwable)
				attrs.getString(getAttributeIndex())
			else
				attrs.getStringOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class TextAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.TextAttribute,
		private val throwable: Boolean
	) : Attribute<CharSequence>(styleable) {

		override fun getValue(attrs: TypedArray): CharSequence {
			return if (!throwable)
				attrs.getText(getAttributeIndex())
			else
				attrs.getTextOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class TextArrayAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.TextArrayAttribute,
		private val throwable: Boolean
	) : Attribute<Array<CharSequence>>(styleable) {

		override fun getValue(attrs: TypedArray): Array<CharSequence> {
			return if (!throwable)
				attrs.getTextArray(getAttributeIndex())
			else
				attrs.getTextArrayOrThrow(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

	class TypeAttribute(
		styleable: StyleableRes,
		private val annotation: com.dolotdev.annotations.TypeAttribute
	) : Attribute<Int>(styleable) {

		override fun getValue(attrs: TypedArray): Int {
			return attrs.getType(getAttributeIndex())
		}

		override fun getAttributeName(): String {
			return annotation.attrName
		}

	}

}