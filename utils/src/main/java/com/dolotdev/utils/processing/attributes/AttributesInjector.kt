package com.dolotdev.utils.processing.attributes

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import com.dolotdev.annotations.*
import com.dolotdev.annotations.Throwable
import com.dolotdev.utils.info.ResourceClassInfo
import java.lang.reflect.Field

object AttributesInjector {

	fun inject(view: View, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
		val styleableAnnotations = mutableListOf<StyleableAnnotation>()
		view::class.java.getSuperclassStyleableAnnotations(styleableAnnotations)

		styleableAnnotations.forEach { styleableAnnotation ->
			val context = view.context
			val styleableAttrs: IntArray = ResourceClassInfo
				.getStyleableClass(context)
				.getField(styleableAnnotation.annotation.styleableResName)
				.get(ResourceClassInfo.getStyleableClass(context)) as IntArray
			val a = context.obtainStyledAttributes(attrs, styleableAttrs, defStyleAttr, defStyleRes)
			view.processAttributesAnnotations(styleableAnnotation, a)
			a.recycle()
		}
	}

	private data class StyleableAnnotation(
		val viewClass: Class<in Nothing>,
		val annotation: StyleableRes
	)

	private fun Class<in Nothing>.getSuperclassStyleableAnnotations(collection: MutableList<StyleableAnnotation>) {
		if (View::class.java.isAssignableFrom(this)) {
			val annotation = annotations.filterIsInstance<StyleableRes>().firstOrNull()
			if (annotation != null) {
				collection.add(StyleableAnnotation(this, annotation))
			}

			if (superclass != View::class.java && View::class.java.isAssignableFrom(superclass)) {
				superclass.getSuperclassStyleableAnnotations(collection)
			}
		}
	}

	private fun Field.getAnnotatedAttribute(styleableRes: StyleableRes): Attribute<*>? {
		return when {
			isAnnotationPresent(BooleanAttribute::class.java) ->
				Attribute.BooleanAttribute(styleableRes, getAnnotation(BooleanAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(ColorAttribute::class.java) ->
				Attribute.ColorAttribute(styleableRes, getAnnotation(ColorAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(ColorStateListAttribute::class.java) ->
				Attribute.ColorStateListAttribute(styleableRes, getAnnotation(ColorStateListAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(DrawableAttribute::class.java) ->
				Attribute.DrawableAttribute(styleableRes, getAnnotation(DrawableAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(DimensionAttribute::class.java) ->
				Attribute.DimensionAttribute(styleableRes, getAnnotation(DimensionAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(DimensionPixelOffsetAttribute::class.java) ->
				Attribute.DimensionPixelOffsetAttribute(styleableRes, getAnnotation(DimensionPixelOffsetAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(DimensionPixelSizeAttribute::class.java) ->
				Attribute.DimensionPixelSizeAttribute(styleableRes, getAnnotation(DimensionPixelSizeAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(FloatAttribute::class.java) ->
				Attribute.FloatAttribute(styleableRes, getAnnotation(FloatAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(FontAttribute::class.java) ->
				Attribute.FontAttribute(styleableRes, getAnnotation(FontAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(FractionAttribute::class.java) ->
				Attribute.FractionAttribute(styleableRes, getAnnotation(FractionAttribute::class.java))
			isAnnotationPresent(IntAttribute::class.java) ->
				Attribute.IntAttribute(styleableRes, getAnnotation(IntAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(IntegerAttribute::class.java) ->
				Attribute.IntegerAttribute(styleableRes, getAnnotation(IntegerAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(LayoutDimensionAttribute::class.java) ->
				Attribute.LayoutDimensionAttribute(styleableRes, getAnnotation(LayoutDimensionAttribute::class.java))
			isAnnotationPresent(NamedLayoutDimensionAttribute::class.java) ->
				Attribute.NamedLayoutDimensionAttribute(styleableRes, getAnnotation(NamedLayoutDimensionAttribute::class.java))
			isAnnotationPresent(NonResourceStringAttribute::class.java) ->
				Attribute.NonResourceStringAttribute(styleableRes, getAnnotation(NonResourceStringAttribute::class.java))
			isAnnotationPresent(ResourceAttribute::class.java) ->
				Attribute.ResourceAttribute(styleableRes, getAnnotation(ResourceAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(SourceResourceAttribute::class.java) ->
				Attribute.SourceResourceAttribute(styleableRes, getAnnotation(SourceResourceAttribute::class.java))
			isAnnotationPresent(StringAttribute::class.java) ->
				Attribute.StringAttribute(styleableRes, getAnnotation(StringAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(TextAttribute::class.java) ->
				Attribute.TextAttribute(styleableRes, getAnnotation(TextAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(TextArrayAttribute::class.java) ->
				Attribute.TextArrayAttribute(styleableRes, getAnnotation(TextArrayAttribute::class.java), isAnnotationPresent(Throwable::class.java))
			isAnnotationPresent(TypeAttribute::class.java) ->
				Attribute.TypeAttribute(styleableRes, getAnnotation(TypeAttribute::class.java))
			else -> null
		}
	}

	private fun View.processAttributesAnnotations(annotation: StyleableAnnotation, attrs: TypedArray) {
		annotation.viewClass.declaredFields.forEach { field ->
			val attr = field.getAnnotatedAttribute(annotation.annotation)
			attr?.context = this.context
			attr?.getValue(attrs)?.let {
				val isPublic = field.isAccessible
				field.isAccessible = true
				try {
					field.set(this, it)
				} finally {
					field.isAccessible = isPublic
				}
			}
		}
	}
}

