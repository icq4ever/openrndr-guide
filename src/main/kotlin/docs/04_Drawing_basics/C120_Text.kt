@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Text")
@file:ParentTitle("Drawing basics")
@file:Order("120")
@file:URL("drawingBasics/text")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.loadFont

import org.openrndr.shape.Rectangle
import org.openrndr.writer
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    @Text
    """
    # 텍스트 그리기 
    
    OPENRNDR은 비트맵 텍스트 렌더링 기능을 지원합니다. 텍스트를 그려내기 위해선 두가지 동작모드가 있습니다. 
    direct mode는 요청된 위치에 텍스트의 문자열을 단순하게 출력하는 것이고, 좀더 고급 모드를 사용하면 텍스트 영역 내에 텍스트를 그려낼 수 있습니다.
    """

    @Text 
    """
    ## 간단한 문자 렌더링

    간단한 문자를 그리려면 먼저 `drawer.fontMap`에 폰트를 불러와 할당해야 합니다. 
    그리고 나서 [`drawer.text`](https://github.com/openrndr/openrndr/blob/v0.4.0-rc.7/openrndr-draw/src/commonMain/kotlin/org/openrndr/draw/Drawer.kt#L1200)를 사용해 텍스트를 그립니다.
    
    """

    @Media.Image "../media/text-001.jpg"

    @Application
    @ProduceScreenshot("media/text-001.jpg")
    @Code
    application {
        program {
            val font = loadFont("data/fonts/default.otf", 48.0)
            extend {
                drawer.clear(ColorRGBa.PINK)
                drawer.fontMap = font
                drawer.fill = ColorRGBa.BLACK
                drawer.text(
                    "HELLO WORLD",
                    width / 2.0 - 100.0,
                    height / 2.0
                )
            }
        }
    }

    @Text
    """
    ## 고급 텍스트 렌더링 
    
    OPENRNDR은 [`Writer`](https://github.com/openrndr/openrndr/blob/v0.4.0-rc.7/openrndr-draw/src/commonMain/kotlin/org/openrndr/draw/Writer.kt#L22) 
    클래스를 제공합니다. 이 클래스를 사용해 간단한 서체세팅을 할 수 있습니다. `Writer` 도구는 텍스트 박스와 커서의 개념을 기반으로 하고 있습니다.

    아래 예제에서 간단한 사용법을 확인할 수 있습니다:
    """

    @Media.Image "../media/text-002.jpg"

    @Application
    @ProduceScreenshot("media/text-002.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            val font = loadFont("data/fonts/default.otf", 24.0)
            extend {
                drawer.clear(ColorRGBa.PINK)
                drawer.fontMap = font
                drawer.fill = ColorRGBa.BLACK

                writer {
                    newLine()
                    text("Here is a line of text..")
                    newLine()
                    text("Here is another line of text..")
                }
            }
        }
    }


    @Text 
    """
    ### 텍스트 영역 지정하기
    
    `Writer`의 `box` 필드는 어디에 텍스트를 그려내는지 지정하는데 사용됩니다.
    텍스트 영역을 (40, 40)을 시작으로 하는 300, 300 픽셀 크기의 사각형으로 지정해봅시다.

    앞 예제와 비교했을때, 위 및 좌측영역에 여백을 갖고 있으며, 두번째 줄에서의 한줄의 텍스트가 두줄로 변경된 것을 확인할 수 있습니다.
    """

    @Media.Image "../media/text-003.jpg"

    @Application
    @ProduceScreenshot("media/text-003.jpg")
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            val font = loadFont("data/fonts/default.otf", 24.0)
            extend {
                drawer.clear(ColorRGBa.PINK)
                drawer.fontMap = font
                drawer.fill = ColorRGBa.BLACK

                writer {
                    box = Rectangle(40.0, 40.0, 300.0, 300.0)
                    newLine()
                    text("Here is a line of text..")
                    newLine()
                    text("Here is another line of text..")
                }
            }
        }
    }

    @Text 
    """
    ### 텍스트 속성을

    tracking (글자간의 좌우 간격), leading(줄간격)은 `Writer.style.leading`과 `Writer.style.tracking`을 사용해 적용할 수 있습니다.
    
    """

    @Media.Video "../media/text-004.mp4"

    @Application
    @ProduceVideo("media/text-004.mp4", 6.28318, 60)
    @Code
    application {
        configure {
            width = 770
            height = 578
        }
        program {
            val font = loadFont("data/fonts/default.otf", 24.0)
            extend {
                drawer.clear(ColorRGBa.PINK)
                drawer.fontMap = font
                drawer.fill = ColorRGBa.BLACK

                writer {
                    // -- animate the text leading
                    leading = cos(seconds) * 20.0 + 24.0
                    // -- animate the text tracking
                    tracking = sin(seconds) * 20.0 + 24.0
                    box = Rectangle(40.0, 40.0, width - 80.0, height - 80.0)
                    newLine()
                    text("Here is a line of text..")
                    newLine()
                    text("Here is another line of text..")
                    newLine()
                    text("Let's even throw another line of text in, for good measure! yay")
                }
            }
        }
    }
}
