@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Drawing primitives batched")
@file:ParentTitle("Drawing basics")
@file:Order("180")
@file:URL("drawingBasics/drawingPrimitivesBatched")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.circleBatch
import org.openrndr.draw.rectangleBatch
import org.openrndr.shape.Circle
import org.openrndr.extra.noise.Random
import org.openrndr.math.Polar
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle

fun main() {
    @Text 
    """
    # 일괄적으로 기초도영 한번에 그리기

    OPENRNDR은 원, 사각형, 점 등 다양한 기초도형을 일괄적으로 한번에 그리는 "batching"이라고 하는 특별한 API를 제공합니다.

    이 방법을 사용하면 렌더링 타임이 CPU에 요청하는 횟수에 묶여있기 때문에 훨씬 빠릅니다. 
    CPU에서 GPU로 작은 데이터들을 여러번 전송하는것보다 큰 데이터를 한번에 전송하는 것이 훨씬 빠르기 떄문입니다.
    이러한 방법을 사용하면 수백개 혹은 수천개의 요소들을 그려낼때 훨씬 효율적입니다.
    
    ## 원 일괄적으로 한번에 그리기 

    이 예제는 `Circle`(`center`나 `radius`와 같은 속성 및 여러 유용한 메소드들을 갖는 클래스)를 사용하는 것이므로, `drawer.circle()`(화면에 픽셀들을 그리는 메소드)와 혼동해서는 안됩니다.
    `center` 위치와, `radius`를 사용하여 `Circle`을 생성하는것이 가능하지만, 대신에 `Circle`을 위한 두개 혹은 세개의 `Vector2` points들을 사용할수도 있습니다.
 
    `Circle`의 리스트를 그리기 위해 `drawer.circles()`를 호출하는것은 `drawer.circle()`을 여러번 호출하는것이 훨씬 빠릅니다.
    """

    @Media.Image "../media/batching-circles-001.jpg"

    @Application
    @ProduceScreenshot("media/batching-circles-001.jpg")
    @Code
    application {
        program {
            extend {
                drawer.clear(ColorRGBa.PINK)
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = ColorRGBa.BLACK
                drawer.strokeWeight = 1.0

                val circles = List(50000) {
                    Circle(
                        Math.random() * width,
                        Math.random() * height,
                        Math.random() * 10.0 + 10.0
                    )
                }
                drawer.circles(circles)
            }
        }
    }

    @Text 
    """
    `drawer.circles`는 몇가지 함수 원형이 있습니다. 
    그 중 하나는 받아모든 원에 대해 `Vector2`의 리스트를 중심좌표로, `Double`를 원의 지름으로 지정하는 것입니다.
    이 예제는 화면에서 100.0 픽셀만큽의 여백을 두고 5000개의 원을 그려냅니다.
    """

    application {
        program {
            @Code.Block
            run {
                val area = drawer.bounds.offsetEdges(-100.0)
                val positions = List(5000) { Random.point(area) }
                drawer.circles(positions, 20.0)
            }
        }
    }

    @Text 
    """
    각 원마다 고유한 크기를 갖게 하려면, 두번째 인자로 Doblue 리스트를 넘겨주면 됩니다:
    """

    @Media.Image "../media/batching-circles-002.jpg"

    @Application
    @ProduceScreenshot("media/batching-circles-002.jpg")
    @Code
    application {
        program {
            extend {
                val area = drawer.bounds.offsetEdges(-100.0)
                val positions = List(400) { Random.point(area) }
                val radii = List(400) { Random.double(5.0, 50.0) }
                drawer.circles(positions, radii)
            }
        }
    }

    @Text 
    """
    각 원마다 `strokeWeight`과 색을 다르게 지정하고 싶다면요?
    정적 혹은 동적 배치를 사용하면 가능합니다, 두 방법 모두 아래의 예제에서 살펴볼 수 있습니다:
    """

    @Media.Image "../media/batching-circles-003.jpg"

    @Application
    @ProduceScreenshot("media/batching-circles-003.jpg")
    @Code
    application {
        program {
            val staticBatch = drawer.circleBatch {
                for (i in 0 until 2000) {
                    fill = ColorRGBa.GRAY.shade(Math.random())
                    stroke = ColorRGBa.WHITE.shade(Math.random())
                    strokeWeight = 1 + Math.random() * 5
                    val pos = Random.ring2d(100.0, 200.0) as Vector2
                    circle(
                        pos + drawer.bounds.center,
                        5 + Math.random() * 20
                    )
                }
            }

            extend {
                drawer.clear(ColorRGBa.GRAY)
                drawer.circles(staticBatch)

                // dynamic batch
                drawer.circles {
                    repeat(100) {
                        fill = ColorRGBa.PINK.shade(Math.random())
                        stroke = null
                        val pos =
                            Vector2((it * 160.0) % width, height * 1.0)
                        val radius = Random.double(2.5, 110.0 - it) * 2
                        circle(pos, radius)
                    }
                }
            }
        }
    }

    @Text 
    """
    ## 사각형 일괄로 한번에 그리기 

    이 예제는 배치를 생성하기 위해  `RectangleBatchBuilder`의 `.rectangle()`메소드를 여러번 호출합니다.
    두가지 방식으로 동작하는데, 첫번째는 흑백 사각형을 정적으로 배치 생성하는 것이고, 두번째는 동적으로 반투명한, 애니메이션 프레임이 있는 핑크색 사각형을 동적으로 배치 생성하는것입니다.
    
    This example calls the `.rectangle()` method of 
    `RectangleBatchBuilder` multiple times to construct a batch.
    It does so in two different ways: first, to construct a static batch of 
    monochrome rectangles and second, to construct a dynamic batch of 
    translucent pink rectangles in each animation frame.
    
    `.rectangle()` 메소드는 두개의 인자를 갖습니다: `Rectangle` 오브젝트와 옵션 값인 회전값입니다. `Rectangle`들은 위치와 가로 및 옵션으로 세로크기의 방법 혹은 `Rectangle.fromCenter()`를 사용하여 생성될 수 있습니다.

    `drawer.rectangles()`를 호출하여 사각형을 일괄로 그리는것이 `drawer.rectangle()`을 여러본 호출하는 것보다 훨씬 빠릅니다.
    """

    @Media.Image "../media/batching-rectangles-001.jpg"

    @Application
    @ProduceScreenshot("media/batching-rectangles-001.jpg")
    @Code
    application {
        program {
            val staticBatch = drawer.rectangleBatch {
                for (i in 0 until 1000) {
                    fill = ColorRGBa.GRAY.shade(Math.random())
                    stroke = ColorRGBa.WHITE.shade(Math.random())
                    strokeWeight = Random.double(1.0, 5.0)
                    val angle = Random.int0(20) * 18.0
                    val pos = drawer.bounds.center +
                            Polar(angle, Random.double(100.0, 200.0)).cartesian
                    val rect = Rectangle.fromCenter(pos, width = 40.0, height = 20.0)
                    rectangle(rect, angle) // add rect to the batch
                }
            }

            extend {
                drawer.clear(ColorRGBa.GRAY)
                drawer.rectangles(staticBatch)

                // dynamic batch
                drawer.rectangles {
                    repeat(100) {
                        fill = ColorRGBa.PINK.opacify(0.05)
                        stroke = null
                        val pos = Vector2((it * 200.0) % width, 0.0)
                        val size = 5 + Math.random() * Math.random() * height
                        rectangle(Rectangle(pos, size))
                    }
                }
            }
        }
    }


    @Text 
    """
    ## 점 일괄로 그리기
    
    일괄로 점을 그리는것은 원 및 사각형을 일괄로 그려내는것과 비슷합니다.
    여기서는 `PointbatchBuilder`와 그것의 `.point()`메소드를 사용합니다.
    여기서 각 점을 `.fill`를 사용해 색을 다르게 하였습니다.
    """

    @Media.Image "../media/batching-points-001.jpg"

    @Application
    @ProduceScreenshot("media/batching-points-001.jpg")
    @Code
    application {
        program {
            extend {
                drawer.clear(ColorRGBa.BLACK)
                drawer.points {
                    repeat(20000) {
                        fill = rgb((it * 0.01 - seconds) % 1)
                        point(
                            (it * it * 0.011) % width,
                            (it * 4.011) % height
                        )
                    }
                }
            }
        }
    }
}
