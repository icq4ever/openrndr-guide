@file:Suppress("UNUSED_EXPRESSION")
@file:Title("컬러")
@file:ParentTitle("드로잉 기초")
@file:Order("130")
@file:URL("drawingBasics/color")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.*
import org.openrndr.dokgen.annotations.*


fun main() {

    @Text 
    """
    # 컬러

    이 챕터에서는 OPENRNDR의 컬러 기능에 대해 다뤄봅니다.
    ## 기본 컬러

    OPENRNDR은 기본적으로 red-green-blud(-alpha)를 사용하여 `ColorRGBa` 인스턴스에 저장합니다.
    `ColorRGBa`의 각 채널은 [0, 1]의 범위를 갖습니다.

    ### 미리 지정된 컬러들 
    """

    @Code.Block
    run {
        ColorRGBa.BLACK
        ColorRGBa.WHITE
        ColorRGBa.RED
        ColorRGBa.GREEN
        ColorRGBa.BLUE
        ColorRGBa.YELLOW
        ColorRGBa.GRAY
        ColorRGBa.PINK
    }

    @Text """
    ### 커스텀 컬러

    커스텀 컬러는 ColorRGBa 생성자를 이용할수도 있지만, `rgb`, `rgba` 함수를 사용할 수도 있습니다.
    두 함수 모두 0.0에서 1.0사이의 값을 갖습니다.
    """

    @Code.Block
    run {
        // -- using the ColorRGBa constructor
        val red = ColorRGBa(1.0, 0.0, 0.0)
        val green = ColorRGBa(0.0, 1.0, 0.0)
        val blue = ColorRGBa(0.0, 0.0, 1.0)
        val blueOpaque = ColorRGBa(0.0, 0.0, 1.0, 0.5)

        // -- using the rgb and rgba functions
        val magenta = rgb(1.0, 0.0, 1.0)
        val magentaOpaque = rgb(1.0, 0.0, 1.0, 0.5)
    }

    @Text 
    """
    ### hex 컬러에서 변환 
    
    RGB 컬러는 일반적으로 16진수(HEX)로 사용됩니다. `ColorRGBa`에는 손쉽게 HEX 코드에서 컬러를 생성하는 방법을 제공합니다.
    """
    @Code.Block
    run {
        // -- construct the OPENRNDR pink from hexadecimal code, using an integer argument
        val color1 = ColorRGBa.fromHex(0xffc0cb)
        // -- construct the OPENRNDR pink from hexadecimal code, using a string argument, the leading # is optional
        val color2 = ColorRGBa.fromHex("#ffc0cb")
    }

    @Text 
    """
    ### 컬러 연산

    `ColorRGBa` 클래스는 컬러의 다양한 변형을 생성하는 여러 방법을 제공합니다.
    `ColorRGBa.shade`을 사용하면 원래 생보다 좀 더 밝거나, 좀더 어두운 색을 만들수 있습니다.
    """


    @Media.Image "../media/color-001.jpg"

    @Application
    @ProduceScreenshot("media/color-001.jpg")
    @Code
    application {
        @Exclude
        configure {
            width = 770
            height = 196
        }
        program {
            extend {
                drawer.stroke = null
                val baseColor = ColorRGBa.PINK
                // -- draw 16 darker shades of pink
                for (i in 0..15) {
                    drawer.fill = baseColor.shade(i / 15.0)
                    drawer.rectangle(35.0 + (700 / 16.0) * i, 32.0, (700 / 16.0), 64.0)
                }
                // -- draw 16 lighter shades of pink
                for (i in 0..15) {
                    drawer.fill = baseColor.shade(1.0 + i / 15.0)
                    drawer.rectangle(35.0 + (700 / 16.0) * i, 96.0, (700 / 16.0), 64.0)
                }
            }
        }
    }

    @Text 
    """
    `ColorRGBa.opacify`를 사용하면, 투명도를 조절할 수 있습니다.
    """

    @Media.Image "../media/color-002.jpg"

    @Application
    @ProduceScreenshot("media/color-002.jpg")
    @Code
    application {
        @Exclude
        configure {
            width = 770
            height = 160
        }
        program {
            extend {
                drawer.stroke = null
                val baseColor = ColorRGBa.PINK

                drawer.fill = ColorRGBa.GRAY.shade(0.5)
                drawer.rectangle(35.0, 32.0, 700.0, 64.00)

                // -- draw 16 darker shades of pink
                for (i in 0..15) {
                    drawer.fill = baseColor.opacify(i / 15.0)
                    drawer.rectangle( 35.0 + (700 / 16.0) * i, 64.0, (700 / 16.0), 64.0 )
                }
            }
        }
    }

    @Text 
    """
    `mix(ColorRGBa, ColorRGBa, Double)`를 사용하여 색을 섞을 수 있습니다.
    """


    @Media.Image "../media/color-003.jpg"

    @Application
    @ProduceScreenshot("media/color-003.jpg")
    @Code
    application {
        @Exclude
        configure {
            width = 770
            height = 128
        }
        program {
            extend {
                drawer.stroke = null
                val leftColor = ColorRGBa.PINK
                val rightColor = ColorRGBa.fromHex(0xFC3549)

                // -- draw 16 color mixes
                for (i in 0..15) {
                    drawer.fill = mix(leftColor, rightColor, i / 15.0)
                    drawer.rectangle(35.0 + (700 / 16.0) * i,32.0, (700 / 16.0), 64.0)
                }
            }
        }
    }

    @Text 
    """
    ## 대체 컬러 모델
    
    OPENRNDR은 다양한 대체 컬러 모델을 제공하고 있습니다. 데체 모델들은 RGB와 다른 원색을 사용합니다.
    
    Class name    | Color space description
    --------------|---------------------------------------
    `ColorRGBa`   | sRGB and linear RGB
    `ColorHSVa`   | Hue, saturation, value
    `ColorHSLa`   | Hue, saturation, lightness
    `ColorXSVa`   | Xue, saturation, value, _Kuler_-like colorspace
    `ColorXSLa`   | Xue, saturation, lightness, _Kuler_-like colorspace
    `ColorXYZa`   | CIE XYZ colorspace
    `ColorYxya`   | CIE Yxy colorspace
    `ColorLABa`   | LAB colorspace
    `ColorLCHABa` | LCHab colorspace, a cylindrical variant of LAB
    `ColorLSHABa` | LSHab colorspace, a cylindrical variant of LAB, chroma replaced with normalized saturation
    `ColorLUVa`   | LUV colorspace
    `ColorLCHUVa` | LCHuv colorspace, a cylindrical variant of LUV
    `ColorLSHUVa` | LSHuv colorspace, a cylindrical variant of LUV, chroma replaced with normalized saturation
    `ColorATVa`   | Coloroid color space, partial implementation
    
    ## HSV, HSL, XSV 그리고 XSL 컬러
    
    HSV ("hue-saturation-value") 와 HSL ("hue-saturation-lightness") 는 원통형 색상공간(cylindrical color spaces)입니다/

    XSV와 XSV(더 나은 이름이 없어서)는 HSV와 HSL의 변형된 버전으로, 아티스트들이 사용하기 적합하도록 hue 속성을 늘리거나 압축한 색공간입니다.
    이 색공간이 예술가에게 적합한 이유는 red-green과 blue-yellow 원색을 갖고 있기 떄문입니다. XSV와 XSL 색공간은 Adobe의 Kuler 색공간을 기반으로 하고 있습니다.
    
    아래는 각 색공간의 컬러 팔레트입니다. 
    (위에서 아래로) HSV, HSL, XSV, XSL.
    XSV와 XSL의 hue가 확연하게 다름을 볼 수 있습니다.
    """

    @Media.Image "../media/color-004.jpg"

    @Application
    @ProduceScreenshot("media/color-004.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 672
        }
        program {
            extend {
                drawer.stroke = null

                // -- draw hsv swatches
                for (j in 0..7) {
                    for (i in 0..31) {
                        drawer.fill = ColorHSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                        drawer.rectangle(35.0 + (700 / 32.0) * i, 32.0 + j * 16.0, (700 / 32.0), 16.0)
                    }
                }

                // -- draw hsl swatches
                drawer.translate(0.0, 160.0)
                for (j in 0..7) {
                    for (i in 0..31) {
                        drawer.fill = ColorHSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0).toRGBa()
                        drawer.rectangle(35.0 + (700 / 32.0) * i, 32.0 + j * 16.0, (700 / 32.0), 16.0)
                    }
                }

                // -- draw xsv (Kuler) swatches
                drawer.translate(0.0, 160.0)
                for (j in 0..7) {
                    for (i in 0..31) {
                        drawer.fill = ColorXSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                        drawer.rectangle(35.0 + (700 / 32.0) * i, 32.0 + j * 16.0, (700 / 32.0), 16.0)
                    }
                }

                // -- draw xsl (Kuler) swatches
                drawer.translate(0.0, 160.0)
                for (j in 0..7) {
                    for (i in 0..31) {
                        drawer.fill = ColorXSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0, 1.0).toRGBa()
                        drawer.rectangle(35.0 + (700 / 32.0) * i, 32.0 + j * 16.0, (700 / 32.0), 16.00)
                    }
                }
            }
        }
    }
}