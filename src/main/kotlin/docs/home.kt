@file:Suppress("UNUSED_EXPRESSION")
@file:Title("OPENRNDR이 뭐죠?")
@file:Order("0")
@file:URL("/index")

package docs

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import kotlin.math.abs
import kotlin.math.cos

fun main() {
    @Text
    """
    # OPENRNDR이 뭐죠?

    OPENRNDR은 [Kotlin](https://kotlinlang.org)로 작성된 창의적 코딩을 위한 라이브러리이자, 어플리케이션 프레임워크입니다.

    OPENRNDR은 가속 그래픽 환경에서의 손쉽고 유연한 프로그래밍을 위한 API를 제공합니다.

    OPENRNDR은 프로토타이핑 뿐만 아니라, 프로덕션 레벨의 고품질 소프트웨어를 위한 도구입니다.

    OPENRNDR은 무료 오픈소스 소프트웨어입니다. 소스코드는 [Github](https://github.com/openrndr/openrndr)에서 제공됩니다.

    OPENRNDR은 네덜란드에 위치한 인터랙티브 및 인터랙션 스튜디오 [RNDR](http://rndr.studio)의 이니셔티브입니다.


    # 간단한 OPENRNDR 프로그램
    아래는 OPENRNDR을 사용해 작성된 아주 간단한 프로그램입니다.
    """

    @Media.Video "media/what-is-001.mp4"

    @Application
    @ProduceVideo("media/what-is-001.mp4", 6.28318)
    @Code
    application {
        @Exclude
        configure {
            width = 770
            height = 568
        }
        program {
            extend {
                drawer.clear(ColorRGBa.PINK)
                drawer.fill = ColorRGBa.WHITE
                drawer.circle(
                    drawer.bounds.center,
                    abs(cos(seconds)) * height * 0.51
                )
            }
        }
    }
}
