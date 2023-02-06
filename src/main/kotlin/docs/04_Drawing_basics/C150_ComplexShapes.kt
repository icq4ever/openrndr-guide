@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Complex shapes")
@file:ParentTitle("Drawing basics")
@file:Order("150")
@file:URL("drawingBasics/complexShapes")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineJoin
import org.openrndr.dokgen.annotations.*
import org.openrndr.math.Vector2
import org.openrndr.shape.*
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    @Text 
    """
    # 복잡한 모양 
    
    OPENRNDR은 2차원 모양(shape)을 생성하고 그리는 다양한 도구를 제공합니다.

    ## Shapes
    
    OPENRNDR은 `Shape`을 사용하여 베지어(bezier) 커브들로 구성된 윤곽을 나타내는 평면 도형을 표현합니다.
    
    `Shape`은 하나 혹은 그 이상의 `ShapeContour` 인스턴스로 구성되어있습니다.
    `ShapeContour`은 여러개의 `Segment` 인스턴스로 구성되어있습니다. 각 `Segment`는 베지어(bezier)커브를 나타냅니다.

    ## Shape과 ShapeContour 빌더
    
    `ContourBuilder` 클래스는 복잡한 2차원 도형을 간단하게 표현하는 방법을 제공합니다.
    `ContourBuilder`는 SVG를 사용해본 분들에게 친숙한 용어를 사용합니다.
    
    * `moveTo(position)` 커서를 지정한 위치로 이동시킵니다.
    * `lineTo(position)` 커서에서  주어진 위치까지 선을 그립니다.
    * `moveOrLineTo(position)` 이전에 지정된 커서가 없다면 커서를 이동시키고, 아니라면 선을 그립니다.
    * `curveTo(control, position)` 커서에서 지정된 위치까지 quadratic bezier 커브를 그립니다.
    * `curveTo(controlA, controlB, position)` 커서에서 지정된 위치까지 quadratic bezier 커브를 그립니다. 
    * `continueTo(position)` inside a quadratic bezier curve starting from the cursor and reflecting the tangent of the last control
    * `continueTo(controlB, position)` 커서에서 지정한 위치까지 cubic spline을 그립니다.
    * `arcTo(radiusX, radiusY, largeAngle, sweepFlag, position)` 호를 그립니다.
    * `close()` 윤곽을 닫습니다 (close the contour)
    * `cursor` 는 현재 위치를 `Vector2` 인스턴스로 표현합니다.
    * `anchor` 는 현재 앵커를 `Vector2` 인스턴스로 표현합니다.
    
    Let's create a simple `Contour` and draw it. The following program shows how to use the contour builder to create a triangular contour.
    간단한 `contour`(윤곽)를 그려봅시다.
    아래의 코드는 contour 빌더를 사용해 삼각형을 그리는지 보여줍니다.
    """

    @Media.Image "../media/shapes-001.jpg"

    @Application
    @ProduceScreenshot("media/shapes-001.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            extend {
                val c = contour {
                    moveTo(Vector2(width / 2.0 - 150.0, height / 2.0 - 150.00))
                    // -- here `cursor` points to the end point of the previous command
                    lineTo(cursor + Vector2(300.0, 0.0))
                    lineTo(cursor + Vector2(0.0, 300.0))
                    lineTo(anchor)
                    close()
                }
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = null
                drawer.contour(c)
            }
        }
    }

    @Text 
    """
    Now let's create a `Shape` using the _shape builder_. The shape is created using two contours, one for
    _outline_ of the shape, and one for the _hole_ in the shape
    """

    @Media.Image "../media/shapes-002.jpg"

    @Application
    @ProduceScreenshot("media/shapes-002.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            extend {
                val s = shape {
                    contour {
                        moveTo(Vector2(width / 2.0 - 150.0, height / 2.0 - 150.00))
                        lineTo(cursor + Vector2(300.0, 0.0))
                        lineTo(cursor + Vector2(0.0, 300.0))
                        lineTo(anchor)
                        close()
                    }
                    contour {
                        moveTo(Vector2(width / 2.0 - 80.0, height / 2.0 - 100.0))
                        lineTo(cursor + Vector2(200.0, 0.0))
                        lineTo(cursor + Vector2(0.0, 200.00))
                        lineTo(anchor)
                        close()
                    }
                }
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = null
                drawer.shape(s)
            }
        }
    }

    @Text 
    """
    ## Shapes and contours from primitives
    
    Not all shapes need to be created using the builders. 
    Some of the OPENRNDR primitives have `.shape` and
    `.contour` properties that help in creating shapes quickly.

    * `LineSegment.contour` and `LineSegment.shape`
    * `Rectangle.contour` and `Rectangle.shape`
    * `Circle.contour` and `Circle.shape`
    
    ## Shape Boolean-operations
    
    Boolean-operations can be performed on shapes using the `compound {}` builder. 
    There are three kinds of compounds: _union_, _difference_ and _intersection_, 
    all three of them are shown in the example below.
    """

    @Media.Image "../media/shapes-003.jpg"

    @Application
    @ProduceScreenshot("media/shapes-003.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            extend {
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = null
                // -- shape union
                val su = compound {
                    union {
                        shape(Circle(185.0, height / 2.0 - 80.0, 100.0).shape)
                        shape(Circle(185.0, height / 2.0 + 80.0, 100.0).shape)
                    }
                }
                drawer.shapes(su)

                // -- shape difference
                val sd = compound {
                    difference {
                        shape(Circle(385.0, height / 2.0 - 80.0, 100.0).shape)
                        shape(Circle(385.0, height / 2.0 + 80.0, 100.0).shape)
                    }
                }
                drawer.shapes(sd)

                // -- shape intersection
                val si = compound {
                    intersection {
                        shape(Circle(585.0, height / 2.0 - 80.0, 100.0).shape)
                        shape(Circle(585.0, height / 2.0 + 80.0, 100.0).shape)
                    }
                }
                drawer.shapes(si)
            }
        }
    }

    @Text 
    """
    The _compound builder_ is actually a bit more clever than what the 
    previous example demonstrated because it can actually work with an 
    entire tree of compounds. Demonstrated below is the _union_ of 
    two _intersections_.
    """

    @Media.Image "../media/shapes-004.jpg"

    @Application
    @ProduceScreenshot("media/shapes-004.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            extend {
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = null
                val cross = compound {
                    union {
                        intersection {
                            shape(Circle(width / 2.0 - 160.0, height / 2.0, 200.0).shape)
                            shape(Circle(width / 2.0 + 160.0, height / 2.0, 200.0).shape)
                        }
                        intersection {
                            shape(Circle(width / 2.0, height / 2.0 - 160.0, 200.0).shape)
                            shape(Circle(width / 2.0, height / 2.0 + 160.0, 200.0).shape)
                        }
                    }
                }
                drawer.shapes(cross)
            }
        }
    }

    @Text 
    """
    ## Cutting contours
    
    A contour be cut into a shorter contour using `ShapeContour.sub()`.
    """

    @Media.Video "../media/shapes-005.mp4"

    @Application
    @ProduceVideo("media/shapes-005.mp4", 10.0)
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            extend {
                drawer.fill = null
                drawer.stroke = ColorRGBa.PINK
                drawer.strokeWeight = 4.0

                val sub0 = Circle(185.0, height / 2.0, 100.0).contour.sub(0.0, 0.5 + 0.50 * sin(seconds))
                drawer.contour(sub0)

                val sub1 = Circle(385.0, height / 2.0, 100.0).contour.sub(seconds * 0.1, seconds * 0.1 + 0.1)
                drawer.contour(sub1)

                val sub2 = Circle(585.0, height / 2.0, 100.0).contour.sub(-seconds * 0.05, seconds * 0.05 + 0.1)
                drawer.contour(sub2)
            }
        }
    }

    @Text 
    """
    ## Placing points on contours
    
    Call `ShapeContour.position()` to sample one specific location 
    or `ShapeContour.equidistantPositions()` to sample multiple equidistant 
    locations on a contour.  
    """

    @Media.Video "../media/shapes-006.mp4"

    @Application
    @ProduceVideo("media/shapes-006.mp4", 10.0)
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            extend {

                drawer.stroke = null
                drawer.fill = ColorRGBa.PINK

                val point = Circle(185.0, height / 2.0, 90.0).contour.position((seconds * 0.1) % 1.0)
                drawer.circle(point, 10.0)

                val points0 = Circle(385.0, height / 2.0, 90.0).contour.equidistantPositions(20)
                drawer.circles(points0, 10.0)

                val points1 = Circle(585.0, height / 2.0, 90.0).contour.equidistantPositions((cos(seconds) * 10.0 + 30.0).toInt())
                drawer.circles(points1, 10.0)

            }
        }
    }

    @Text 
    """
    ## Offsetting contours
    
    The function `ShapeContour.offset` can be used to create an offset version 
    of a contour. """

    @Media.Video "../media/shapes-101.mp4"

    @Application
    @ProduceVideo("media/shapes-101.mp4", 6.28318)
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            // -- create a contour from Rectangle object
            val c = Rectangle(100.0, 100.0, width - 200.0, height - 200.0).contour.reversed

            extend {
                drawer.fill = null
                drawer.stroke = ColorRGBa.PINK
                drawer.contour(c)
                for (i in 1 until 10) {
                    val o = c.offset(cos(seconds + 0.5) * i * 10.0, SegmentJoin.BEVEL)
                    drawer.contour(o)
                }
            }
        }
    }

    @Text 
    """
    `ShapeContour.offset` can also be used to offset curved contours. 
    The following demonstration shows a single cubic bezier offset at multiple 
    distances.
    """

    @Media.Video "../media/shapes-100.mp4"

    @Application
    @ProduceVideo("media/shapes-100.mp4", 6.28318)
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            val c = contour {
                moveTo(width * (1.0 / 2.0), height * (1.0 / 5.0))
                curveTo(width * (1.0 / 4.0), height * (2.0 / 5.0), width * (3.0 / 4.0), height * (3.0 / 5.0), width * (2.0 / 4.0), height * (4.0 / 5.0))
            }
            extend {
                drawer.stroke = ColorRGBa.PINK
                drawer.strokeWeight = 2.0
                drawer.lineJoin = LineJoin.ROUND
                drawer.contour(c)
                for (i in -8 .. 8) {
                    val o = c.offset(i * 10.0 * cos(seconds + 0.5))
                    drawer.contour(o)
                }
            }
        }
    }
}
