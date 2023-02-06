@file:Suppress("UNUSED_EXPRESSION")
@file:Title("이미지")
@file:ParentTitle("드로잉 기초")
@file:Order("110")
@file:URL("drawingBasics/images")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.grayscale
import org.openrndr.draw.invert
import org.openrndr.draw.loadImage
import org.openrndr.draw.tint
import org.openrndr.shape.Rectangle

fun main() {

    @Text
    """
    # 이미지

    이미지는 `ColorBuffer` 인스턴스에 저장되는데, 이 데이터는 GPU메모리에 위치합니다.y

    ## 이미지를 불러와 그리기

    이미지는 `loadImage` 함수를 이용하여 불러오고, `Drawer.image`를 사용해 그려집니다.
    """

    @Media.Image "../media/image-001.jpg"

    @Application
    @ProduceScreenshot("media/image-001.jpg")
    @Code
    application {
        configure {}
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                drawer.image(image)
            }
        }
    }

    @Text
    """
    이미지의 위치를 변경하고 싶다면, `Drawer.image`에 좌표를 추가하면 됩니다.
    """

    run {
        application {
            program {
                val image = loadImage("data/images/cheeta.jpg")
                extend {
                    @Code.Block
                    run {
                        drawer.image(image, 40.0, 40.0)
                    }
                }
            }
        }
    }

    @Text
    """
    `width`, `height` 인자를 추가하여 그려지는 이미지의 크기를 지정할 수 있습니다.
    """

    run {
        application {
            program {
                val image = loadImage("data/images/cheeta.jpg")
                extend {
                    @Code.Block
                    run {
                        drawer.image(image, 40.0, 40.0, 64.0, 48.0)
                    }
                }
            }
        }
    }

    @Text
    """
    ## 이미지의 일부분을 그리기
    
    _source_, _target_ 사각형을 지정하여 이미지의 부분을 그려낼 수도 있습니다.
    source 사각형은 원본의 이미지에서 얻어올 영역을, target 사각형은 그려질 영역을 의미합니다.
    """

    @Media.Image "../media/image-002.jpg"

    @Application
    @ProduceScreenshot("media/image-002.jpg")
    @Code
    application {
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                val source = Rectangle(0.0, 0.0, 320.0, 240.0)
                val target = Rectangle(160.0, 120.0, 320.0, 240.0)
                drawer.image(image, source, target)

            }
        }
    }

    @Text
    """
    ## 이미지의 여러 부분을 그리기 
    """

    @Media.Image "../media/image-003.jpg"

    @Application
    @ProduceScreenshot("media/image-003.jpg")
    @Code
    application {
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                val areas = (0..10).flatMap { y ->
                    (0..10).map { x ->
                        val source = Rectangle(
                            x * (width / 10.0),
                            y * (height / 10.0),
                            width / 5.0,
                            height / 5.0
                        )
                        val target = Rectangle(
                            x * (width / 10.0),
                            y * (height / 10.0),
                            width / 10.0,
                            height / 10.0
                        )
                        source to target
                    }
                }
                drawer.image(image, areas)
            }
        }
    }

    @Text
    """
    ## 이미지의 외형 변경하기 

    선형적 컬러변환은 `Drawer.drawStyle.colorMatrix`를 `Matrix55` 로 변경하여 적용할 수 있습니다.
    
    ### 틴팅 (Tinting)
    
    Tinting multiplies the image color with a _tint color_.
    틴팅은 _tint color_를 이미지에 'multiply` 합니다.
    """

    @Media.Image "../media/image-004.jpg"

    @Application
    @ProduceScreenshot("media/image-004.jpg")
    @Code
    application {
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                drawer.drawStyle.colorMatrix = tint(ColorRGBa.RED)
                drawer.image(image, 0.0, 0.0)
            }
        }
    }

    @Text 
    """
    ### 반전
    
    `inver` 컬러 매트릭스를 사용하여 이미지의 색을 반전시킬 수 있습니다.
    """

    @Media.Image "../media/image-005.jpg"

    @Application
    @ProduceScreenshot("media/image-005.jpg")
    @Code
    application {
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                drawer.drawStyle.colorMatrix = invert
                drawer.image(image, 0.0, 0.0)
            }
        }
    }

    @Text
    """
    ### 그레이스케일
    
    `grayscale` 컬러 매트릭스를 사용하여 이미지를 그레이스케일 화 할 수 있습니다.

    """

    @Media.Image "../media/image-006.jpg"

    @Application
    @ProduceScreenshot("media/image-006.jpg")
    @Code
    application {
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                // -- the factors below determine the RGB mixing factors
                drawer.drawStyle.colorMatrix =
                    grayscale(1.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0)
                drawer.image(image)
            }
        }
    }

    @Text
    """
    ### 컬러변환 연결시키기 
    
    컬러 변환은 곱 연산자(multiplication operator)를 사용하여 조합할 수 있습니다. 이것을 변환 연결(transform concatenation) 이라고 합니다.
    변환 연결은 우측에서 좌측으로 읽어야 함을 기억해주시기 바랍니다. 아래의 예제는 먼저 `grayscale` 변환을 한 뒤 `tint`변환을 하는 예제입니다.
    """

    @Media.Image "../media/image-007.jpg"

    @Application
    @ProduceScreenshot("media/image-007.jpg")
    @Code
    application {
        program {
            val image = loadImage("data/images/cheeta.jpg")

            extend {
                // -- here we concatenate the transforms using the multiplication operator.
                drawer.drawStyle.colorMatrix =
                    tint(ColorRGBa.PINK) * grayscale()
                drawer.image(image)
            }
        }
    }
}
