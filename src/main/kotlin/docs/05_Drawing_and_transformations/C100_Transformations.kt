@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Transformations")
@file:ParentTitle("드로잉과 좌표계")
@file:Order("100")
@file:URL("drawingAndTransformations/transformations")

package docs.`05_Drawing_and_transformations`

import kotlin.math.cos
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*

fun main() {

    @Text
    """
    # 좌표변환

    이 섹션에서는 아이템을 화면상에 위치시키는 내용을 다룹니다.
    
    ## 기본적인 좌표변환
    
    ### Translation
    
    Translation은 한 지점을 offset만큼 이동시키는 것입니다.

    아래의 예제에서는 사각형을 화면에서 이동시키기 위해 `Drawer.trasnalte`을 사용하고 있습니다.
    """

    @Media.Video "../media/transformations-001.mp4"

    @Application
    @ProduceVideo("media/transformations-001.mp4", 7.7, 60, 8)
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
                // set up translation
                drawer.translate(seconds * 100.0, height / 2.0)
                drawer.rectangle(-50.0, -50.0, 100.0, 100.0)
            }
        }
    }

    @Text
    """
    좌표변환(translations) (일반적으로 transformations 라고도 불리웁니다)은 누적됩니다.
    예를 들어 가로와 세로 방향으로 나눠 좌표면환을 할 수 있습니다.
    """

    @Media.Video "../media/transformations-002.mp4"

    @Application
    @ProduceVideo("media/transformations-002.mp4", 7.7, 60, 8)
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
                // move the object to the vertical center of the screen
                drawer.translate(0.0, height / 2.0)
                // set up horizontal translation
                drawer.translate(seconds * 100.0, 0.0)
                // set up vertical translation
                drawer.translate(0.0, cos(seconds * Math.PI * 2.0) * 50.00)

                drawer.rectangle(-50.0, -50.0, 100.0, 100.00)
            }
        }
    }

    @Text
    """
    ### 회전
    
    회전 변환은 `Drawser.rotate()`를 사용합니다.
    회전은 좌표계의 원점인 (0, 0)을 중심으로 적용되는데, 이 원점은 윈도우의 좌측 상단을 의미합니다.

    첫번째 예제에서는 사각형을 이 원점에 두었지만, 이후에는 화면의 중심으로 옮겼습니다. 
    `

    In the first rotation example we rotate a rectangle that is placed around the origin but later translated to the center
    of the screen. Here we notice something that may be counter-intuitive at first: the transformations are easiest read
    from bottom to top: first `rotate` is applied and only then `translate`.
    """

    @Media.Video "../media/transformations-003.mp4"

    @Application
    @ProduceVideo("media/transformations-003.mp4", 3.0, 60, 8)
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

                // -- translate
                drawer.translate(width / 2.0, height / 2.0)
                // -- rotate
                drawer.rotate(seconds * 30.0)
                // -- rectangle around the origin
                drawer.rectangle(-50.0, -50.0, 100.0, 100.0)
            }
        }
    }

    @Text
    """
    ### Scaling
    
    Scaling transformations are performed using `Drawer.scale()`. 
    Also scaling is applied around the origin of
    the coordinate system: (0, 0).
    """

    @Media.Video "../media/transformations-004.mp4"

    @Application
    @ProduceVideo("media/transformations-004.mp4", 6.28318, 60, 8)
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

                // -- translate to center of screen
                drawer.translate(width / 2.0, height / 2.0)
                // -- scale around origin
                drawer.scale(cos(seconds) + 2.0)
                // -- rectangle around the origin
                drawer.rectangle(-50.0, -50.0, 100.0, 100.00)
            }
        }
    }

    @Text """
    ### Combining transformations
    """

    @Media.Video "../media/transformations-005.mp4"

    @Application
    @ProduceVideo("media/transformations-005.mp4", 6.0, 60, 8)
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

                // -- translate to center of screen
                drawer.translate(width / 2.0, height / 2.0)

                drawer.rotate(20.00 + seconds * 60.0)
                // -- rectangle around the origin
                drawer.rectangle(-50.0, -50.0, 100.0, 100.0)

                // -- draw a second rectangle, sharing the rotation of the first rectangle but with
                // an offset
                drawer.translate(150.0, 0.0)
                drawer.rectangle(-50.0, -50.0, 100.0, 100.0)
            }
        }
    }

    @Media.Video "../media/transformations-006.mp4"

    @Application
    @ProduceVideo("media/transformations-006.mp4", 6.0, 60, 8)
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

                // -- translate to center of screen
                drawer.translate(width / 2.0, height / 2.0)

                drawer.rotate(seconds * 60.0)
                // -- rectangle around the origin
                drawer.rectangle(-50.0, -50.0, 100.0, 100.0)

                // -- draw a second rectangle, sharing the rotation of the first rectangle but with
                // an offset
                drawer.translate(150.0, 0.0)
                drawer.rotate(seconds * 15.0)
                drawer.rectangle(-50.0, -50.0, 100.0, 100.00)
            }
        }
    }

    @Text
    """
    ## Transform 파이프라인

    OPENRNDR의 `Drawer`는 모델-뷰-프로젝션 transform 파이프라인을 통해 구동됩니다. 즉 화면 위치를 결정하기 위해 세가지의 다른 좌표변환이 적용됩니다.
    OPENRNDR's `Drawer` is build around model-view-projection transform pipeline. That means that three different transformations are applied to determine
    the screen position.
    
    matrix property | pipeline stage
    ----------------|---------------------
    `model`         | model transform
    `view`          | view transform
    `projection`    | projection transform
    
    어떤 매트릭스 속성이 `Drawer`의 계산(메소드)에 의해 영향을 받을가요 ?
    
    operation            | 매트릭스 속성
    ---------------------|----------------
    `fun rotate(…)`      | `model`
    `fun translate(…)`   | `model`
    `fun scale(…)`       | `model`
    `fun rotate(…)`      | `model`
    `fun lookAt(…)`      | `view`
    `fun ortho(…)`       | `projection`
    `fun perspective(…)` | `projection`
    
    
    ## projection matrix
    
    디폴트 transformation으로는 `ortho()`를 사용한 orthographic 프로젝션이 사용됩니다. 기준점은 좌측-상단 코너입니다. y좌표는 아래로, x좌표가 우측으로 갈수록 증가합니다.
    
    ### Perspective 프로젝션
    
    ```kotlin
    override fun draw() {
        drawer.perspective(90.0, width*1.0 / height, 0.1, 100.0)
    }
    ```
    
    ## Transforms
    
    OPENRNDR transforms은 `Matrix44` 인스턴스로 표현됩니다.
    
    OPENRNDR은 `Matrix44`를 생성하기 위한 툴을 제공합니다.
    OPENRNDR offers tools to construct `Matrix44`
    
    ### Transform builder
    
    Relevant APIs
    ```
    Matrix44
    transform {}
    ```
    
    In the snippet below a `Matrix44` instance is constructed using the `transform {}` builder. Note that the application order is from bottom to top.
    
    ```kotlin
    drawer.view *= transform {
        rotate(32.0)
        rotate(Vector3(1.0, 1.0, 0.0).normalized, 43.0)
        translate(4.0, 2.0)
        scale(2.0)
    }
    ```
    
    This is equivalent to the following:
    ```kotlin
    drawer.rotate(32.0)
    drawer.rotate(Vector3(1.0, 1.0, 0.0).normalized, 43.0)
    drawer.translate(4.0, 2.0)
    drawer.scale(2.0)
    ```
    
    ## Applying transforms to vectors
    
    ```kotlin
        val x = Vector3(1.0, 2.0, 3.0, 1.0)
        val m = transform {
            rotate(Vector3.UNIT_Y, 42.0)
        }
        val transformed = m * x
        val transformedTwice = m * m * x
    ```

    """
}
