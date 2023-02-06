@file:Suppress("UNUSED_EXPRESSION")
@file:Title("복잡한 모양")
@file:ParentTitle("드로잉 기초")
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
    자 이제 _shape builder_`를 사용하여 `Shape`를 만들어봅시다. 
    Shape은 두개의 contour를 사용하는데, 하나는 Shape의 _외곽선_을, 다른 하나는 Shape의 _구멍_을 그리는데 사용될 것입니다.
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
    ## 기초 도형에서 Shape 과 conour 가져오기
    
    반드시 빌더를 이용하여 shape를 만들 필요는 없습니다.
    몇몇 OPENRNDR 기초도형(premitives)dms `.shape`과 `.contour` 속성을 가지고 있어 손쉽게 shape을 만들수 있습니다.
    
    
    * `LineSegment.contour` and `LineSegment.shape`
    * `Rectangle.contour` and `Rectangle.shape`
    * `Circle.contour` and `Circle.shape`
    
    ## Shape의 boolean 연산
    
    대상 shape의 `compound{}` 빌더를 사용하여 boolean 연산을 수행할 수 있습니다.
    _union_, _difference_, _intersection_ 의 compounds 종류가 있습니다.
    위 모든 사용법은 아래 예제에서 볼 수 있습니다.
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
    아래 예제에서의 _compound builder_는 위 예제보다 더 효율적입니다, compounds의 트리로 동작하고 있기 떄문입니다.
    아래는 두 _intersections_의 _union_을 보여줍니다.
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
    ## contour 잘라내기 
    
    contour는 `ShapeContour.sub()`를 사용하여 일부분으로 자를 수 있습니다.
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
    ## contour 위에 point를 위치시키기
    
    `ShapeContour.position()`를 사용해 하나의 특정 위치를 얻어오거나, 
    `ShapeContour.equidistantPositions()`을 사용하여 여러 등거리(equidistant) 위치를 contour에서 얻어올 수 있습니다.
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
    ## contour 오프셋 
    
    `ShapeContour.offset`함수를 사용하여 기존 contour의 offset된 버전의 contour를 생성할 수 있습니다.
    """

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
    `ShapeContour.offset`은 또한 curved contour에도 사용할 수 있습니다.
    아래는 하나의 cubic bezier에 여러 offset을 적용한 예제입니다.
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
