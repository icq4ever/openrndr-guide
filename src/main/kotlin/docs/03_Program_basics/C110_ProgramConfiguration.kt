@file:Suppress("UNUSED_EXPRESSION", "UNREACHABLE_CODE")
@file:Title("프로그램 설정")
@file:ParentTitle("프로그램 기초")
@file:Order("110")
@file:URL("programBasics/programConfiguration")

package docs.`03_Program_basics`

import org.openrndr.Fullscreen
import org.openrndr.UnfocusBehaviour
import org.openrndr.application
import org.openrndr.dokgen.annotations.*
import org.openrndr.math.IntVector2

fun main() {

    @Text
    """
    # 프로그램 설정
    
    커스텀 설정을 갖는 프로그램은 대충 이와 같습니다.
    """

    @Code
    application {
        configure {

            // 세팅이 여기에 작성됨
        }
        program {
            // -- 한번만 실행되는 셋업코드가 여기에 작성됨
            extend {

                // -- 드로잉 코드들이 이곳에 작성됨
            }
        }
    }

    @Text
    """
    ## 윈도우 크기
    
    윈도우 크기를 셋업하려면 `width`와 `height` 프로퍼티를 사용합니다.
    """

    @Code
    application {
        configure {
            width = 640
            height = 480
        }
    }

    @Text
    """
    ## 윈도우 위치
    
    `position`의 기본값은 `null`입니다. 이는 메인 디스플레이의 중앙을 의미합니다.
    """

    @Code
    application {
        configure {
            position = IntVector2(100, 400)
        }
    }

    @Text
    """
    ## 전체화면

    윈도우 크기를 셋업하려면 `width`와 `height` 프로퍼티를 사용합니다.

    """

    @Code
    application {
        configure {
            width = 1920
            height = 1080
            fullscreen = Fullscreen.SET_DISPLAY_MODE
        }
    }

    @Text
    """
    or if no mode change is desired use `Fullscreen.CURRENT_DISPLAY_MODE`
    """


    @Code
    application {
        configure {
            fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
        }
    }

    @Text
    """
   
    # 윈도우 타이틀
    """

    @Code
    application {
        configure {
            title = "Lo and behold!"
        }
    }

    @Text 
    """
    
    # 비활성화된 윈도우 동작
    
    비활성 상태일때 두가지 모드를 사용할 수 있습니다.
    `NORMAL`모드에서 프로그램은 최대 속도로 실행합니다. 
    반대로 `THROTTLE`에서는 10Hz의 속도로 쓰로틀링됩니다.
    """

    @Code
    application {
        configure {
            unfocusBehaviour = UnfocusBehaviour.THROTTLE
        }
    }
}
