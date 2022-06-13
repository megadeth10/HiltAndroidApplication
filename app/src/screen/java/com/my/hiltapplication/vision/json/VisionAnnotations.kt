package com.deleo.c2cmarketplace.network.json

import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

/**
 * https://github.com/GoogleCloudPlatform/cloud-vision
 * android // AnnotateImageResponse Object를 참조 함
 */
class VisionAnnotations {
    @SerializedName("responses")
    var responses: ArrayList<VisionResponse> = ArrayList(0)

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        responses.forEach {
            stringBuilder.append("responses = \n").append(it.toString()).append("\n")
        }
        return stringBuilder.toString()
    }
}

class VisionResponse{
    @SerializedName("labelAnnotations")
    var labelAnnotations: ArrayList<LabelAnnotations> = ArrayList(0)

    @SerializedName("imagePropertiesAnnotation")
    var imagePropertiesAnnotation: ImagePropertiesAnnotation =
        ImagePropertiesAnnotation()

    @SerializedName("logoAnnotations")
    var logoAnnotations: ArrayList<LogoAnnotations> = ArrayList(0)

    @SerializedName("localizedObjectAnnotations")
    var localizedObjectAnnotations: ArrayList<LocalizedObjectAnnotations> = ArrayList(0)

    @SerializedName("textAnnotations")
    var textAnnotations: ArrayList<TextAnnotations> = ArrayList(0)

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        labelAnnotations.forEach {
            stringBuilder.append("labelAnnotations =\n").append(labelAnnotations.toString())
        }
        if(labelAnnotations.size > 0){
            stringBuilder.append("\n\n")
        }
        val imageAnnotation = imagePropertiesAnnotation.toString()
        if(imageAnnotation.isNotEmpty()) {
            stringBuilder.append("imagePropertiesAnnotation =\n")
                .append(imagePropertiesAnnotation.toString()).append("\n\n")
        }

        logoAnnotations.forEach {
            stringBuilder.append("logoAnnotations =\n").append(logoAnnotations.toString())
        }
        if(logoAnnotations.size > 0){
            stringBuilder.append("\n\n")
        }
        textAnnotations.forEach {
            stringBuilder.append("textAnnotations =\n").append(textAnnotations.toString())
        }
        if(textAnnotations.size > 0){
            stringBuilder.append("\n\n")
        }
        localizedObjectAnnotations.forEach {
            stringBuilder.append("localizedObjectAnnotations =\n").append(localizedObjectAnnotations.toString())
        }
        if(localizedObjectAnnotations.size > 0){
            stringBuilder.append("\n\n")
        }
        return stringBuilder.toString() + "\n"
    }
}

/**
 * type : TEXT_DETECTION
 */
class TextAnnotations{
    @SerializedName("locale")
    var locale: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("boundingPoly")
    var boundingPoly: LogoBoundingPoly =
        LogoBoundingPoly()

    override fun toString(): String {
//        return "locale: $locale / description: $description / boundingPoly: ${boundingPoly.toString()}\n"
        return "description: $description\n"
    }
}

/**
 * type : LOGO_DETECTION
 */
class LogoAnnotations{
    @SerializedName("mid")
    var mid: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("score")
    var score: Double = .0

    @SerializedName("boundingPoly")
    var boundingPoly: LogoBoundingPoly =
        LogoBoundingPoly()

    override fun toString(): String {
//        return "mid: $mid / description: $description / score: $score / boundingPoly: ${boundingPoly.toString()}\n"
        return "description: $description\n"
    }
}

/**
 * type : IMAGE_PROPERTIES
 */
class ImagePropertiesAnnotation{
    @SerializedName("dominantColors")
    var dominantColors: DominantColors =
        DominantColors()

    override fun toString(): String {
        return dominantColors.toString()
    }
}

/**
 * type : LABEL_DETECTION
 */
class LabelAnnotations{
    @SerializedName("mid")
    var mid: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("score")
    var score: Double = .0

    @SerializedName("topicality")
    var topicality: Double = .0

    override fun toString(): String {
//        return "\nmid: $mid / description: $description / score: $score / topicality: $topicality\n"
        return "description: $description\n"
    }
}

/**
 * type : OBJECT_LOCALIZATION
 */
class LocalizedObjectAnnotations {
    @SerializedName("mid")
    var mid: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("score")
    var score: String = ""

    @SerializedName("boundingPoly")
    var boundingPoly: BoundingPoly =
        BoundingPoly()

    override fun toString(): String {
//        val stringBuilder = StringBuilder()
//        stringBuilder.append("\nmid=").append(mid)
//            .append("\nname=").append(name)
//            .append("\nscore=").append(score)
//            .append("\nboundingPoly=").append(boundingPoly.toString())
//        return stringBuilder.toString()
        return "name: $name\n"
    }
}

class BoundingPoly {
    @SerializedName("normalizedVertices")
    var normalizedVertices: ArrayList<NormalizedVertice> = ArrayList(0)

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        normalizedVertices.forEach {
            stringBuilder.append(it.toString())
        }
        return stringBuilder.toString()
    }
}

class LogoBoundingPoly {
    @SerializedName("vertices")
    var normalizedVertices: ArrayList<NormalizedVertice> = ArrayList(0)

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        normalizedVertices.forEach {
            stringBuilder.append(it.toString()).append("\n")
        }
        return stringBuilder.toString()
    }
}

class NormalizedVertice {
    @SerializedName("x")
    var x: Float = 0f

    @SerializedName("y")
    var y: Float = 0f

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\nx=").append(x).append("\ny=").append(y).append("\n")
        return stringBuilder.toString()
    }
}

class DominantColors {
    @SerializedName("colors")
    var colors: ArrayList<Color> = ArrayList(0)
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        colors.forEach{
            stringBuilder.append("${it.toString()}\n")
        }
        return stringBuilder.toString()
    }
}

class Color{
    @SerializedName("color")
    var color: RGB =
        RGB()

    @SerializedName("score")
    var score: Float = 0f

    @SerializedName("pixelFraction")
    var pixelFraction: Float = 0f

    override fun toString(): String {
//        return "\n${color.toString()}\nscore: $score / pixelFraction: ${pixelFraction}\n"
        return "${color.toString()}"
    }
}

class RGB{
    @SerializedName("red")
    var red: Int = 0

    @SerializedName("green")
    var green: Int = 0

    @SerializedName("blue")
    var blue: Int = 0

    override fun toString(): String {
        return "red = $red / green: $green / blue: $blue"
    }
}