package com.example.imageloaderlibrary


import com.example.imageloaderlibrary.transform.Transformation

class ImageRequest private constructor(
    val transformations: List<Transformation>,
    val placeholderResId: Int?,
    val errorResId: Int?,
) {
    class Builder {
        private var transformations: List<Transformation>
        private var placeholderResId: Int?
        private var errorResId: Int?
        constructor() {
            transformations = emptyList()
            placeholderResId = null
            errorResId = null
        }

        /**
         * Set the list of [Transformation]s to be applied to this request.
         */
        fun transformations(transformations: List<Transformation>) = apply {
            this.transformations = transformations.toList()
        }

        /**
         * Set the placeholder drawable to use when the request starts.
         */
        fun placeholder(drawableResId: Int) = apply {
            this.placeholderResId = drawableResId
        }

        /**
         * Set the error drawable to use if the request fails.
         */
        fun error(drawableResId: Int) = apply {
            this.errorResId = drawableResId
        }

        /**
         * Create a new [ImageRequest].
         */
        fun build(): ImageRequest {
            return ImageRequest(
                transformations = transformations,
                placeholderResId = placeholderResId,
                errorResId = errorResId,
            )
        }
    }
}