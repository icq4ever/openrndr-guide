@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Drawing primitives")
@file:ParentTitle("Drawing basics")
@file:Order("100")
@file:URL("drawingBasics/drawingPrimitives")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.LineCap
import org.openrndr.math.Vector2

fun main() {
    @Text 
    """
    # 기초도형 그리기
    
    OPENRNDR의 기초도형을 그리는 방법을 다뤄봅니다. 여기서는 선, 사각형, 삼각형, 원을 그리는 법을 알아볼 것입니다.

    ## 원 그리기
    
    원은 원의 중심을 나타내는 `x`, `y` 좌표를 중심으로 그려집니다.\
    원은 `Drawer.fill`에 지정된 색으로 채워지며, `Drawer.stroke`에 지정된 색으로 선이 그려집니다. 선의 굵기는 `Drawer.strokeWeight`에 의해 지정됩니다.
    """

    @Media.Image "../media/circle-001.jpg"

    @Application
    @ProduceScreenshot("media/circle-001.jpg")
    @Code
    application {
        configure {
        }
        program {
            extend {
                drawer.clear(ColorRGBa.PINK)

                // -- draw circle with white fill and black stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.circle(width / 6.0, height / 2.0, width / 8.0)

                // -- draw circle without fill, but with black stroke
                drawer.fill = null
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.circle(
                    width / 6.0 + width / 3.0,
                    height / 2.0,
                    width / 8.0
                )

                // -- draw circle with white fill, but without stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = null
                drawer.strokeWeight = 1.0
                drawer.circle(
                    width / 6.0 + 2 * width / 3.0,
                    height / 2.0,
                    width / 8.0
                )
            }
        }
    }

    @Text 
    """
    원을 그리기 위해 또다른 API들이 사용됨을 볼 수 있습니다.
    `Drawer.circle(center: Vector2, radius: Double)`과 `Drawer.circle(circle: Circle)`인데요. 
    이들은 `Vector2` 혹은 `Circle` 타입으로 전달된 값들을 사용해 손쉽게 같은 원을 그려내는 문법을 사용합니다.
   
    """

    run {
        application {
            program {
                @Code.Block
                run {
                    drawer.circle(mouse.position, 50.0)
                }
            }
        }
    }

    @Text
    """## Drawing rectangles"""

    @Media.Image "../media/rectangle-001.jpg"

    @Application
    @ProduceScreenshot("media/rectangle-001.jpg")
    @Code
    application {
        program {
            extend {
                drawer.clear(ColorRGBa.PINK)

                // -- draw rectangle with white fill and black stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.rectangle(
                    width / 6.0 - width / 8.0,
                    height / 2.0 - width / 8.0,
                    width / 4.0,
                    width / 4.0
                )

                // -- draw rectangle without fill, but with black stroke
                drawer.fill = null
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0
                drawer.rectangle(
                    width / 6.0 - width / 8.0 + width / 3.0,
                    height / 2.0 - width / 8.0,
                    width / 4.0,
                    width / 4.0
                )

                // -- draw rectangle with white fill, but without stroke
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = null
                drawer.strokeWeight = 1.0
                drawer.rectangle(
                    width / 6.0 - width / 8.0 + 2.0 * width / 3.0,
                    height / 2.0 - width / 8.0,
                    width / 4.0,
                    width / 4.0
                )
            }
        }
    }

    @Text
    """
    ## 선 그리기
    선은 `lineSegment`를 사용한 두 좌표로 구성된 세그먼트로 하나가 그려집니다. 
    `Drawer.stroke`로 선의 색을, `Drawer.strokeWEight`으로 선의 굵기를 지정하여 사용할 수 있습니다.

    선의 끝모양은 `Drawer.lineCap`의 세가지 스타일를 사용할 수 있습니다.

    LineCap. | description
    ---------|------------
    BUTT     | butt cap
    ROUND    | round cap
    SQUARE   | square cap
    """

//    run {
//        val a = LineCap.ROUND
//        val b = when (a) {
//            LineCap.ROUND -> TODO()
//            LineCap.BUTT -> TODO()
//            LineCap.SQUARE -> TODO()
//        }
//    }

    @Media.Image "../media/line-001.jpg"

    @Application
    @ProduceScreenshot("media/line-001.jpg")
    @Code
    application {
        program {
            extend {
                drawer.clear(ColorRGBa.PINK)
                // -- setup line appearance
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                drawer.lineSegment(
                    10.0,
                    height / 2.0 - 20.0,
                    width - 10.0,
                    height / 2.0 - 20.0
                )

                drawer.lineCap = LineCap.BUTT
                drawer.lineSegment(
                    10.0,
                    height / 2.0,
                    width - 10.0,
                    height / 2.0
                )

                drawer.lineCap = LineCap.SQUARE
                drawer.lineSegment(
                    10.0,
                    height / 2.0 + 20.0,
                    width - 10.0,
                    height / 2.0 + 20.0
                )
            }
        }
    }

    @Text
    """
    ### 선 스트립 그리기
    연결된 선 세그먼트는 라인 스트립이라 불리우며 `linewStrip`을 사용하여 그릴 수 있습니다.
    라인 스트립을 그리기 위해서는 점들의 리스트가 사용됩니다.
    """

    @Media.Image "../media/line-002.jpg"

    @Application
    @ProduceScreenshot("media/line-002.jpg")
    @Code
    application {
        program {
            extend {
                drawer.clear(ColorRGBa.PINK)
                // -- setup line appearance
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                val points = listOf(
                    Vector2(10.0, height - 10.0),
                    Vector2(width / 2.0, 10.0),
                    Vector2(width - 10.0, height - 10.0)
                )
                drawer.lineStrip(points)
            }
        }
    }
}