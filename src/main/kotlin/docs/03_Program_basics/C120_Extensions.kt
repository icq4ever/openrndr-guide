@file:Suppress("UNUSED_EXPRESSION")
@file:Title("익스텐션")
@file:ParentTitle("프로그램 기초")
@file:Order("120")
@file:URL("programBasics/extensions")

package docs.`03_Program_basics`

import org.openrndr.application
import org.openrndr.dokgen.annotations.*
import org.openrndr.extensions.Screenshots

fun main() {
    @Text 
    """
    # 익스텐션 (애드온)

    익스텐션은 프로그램에 기능을 추가해줍니다. 익스텐션은 키보드 셋업, 마우스 연결, 드로잉 제어, 외 다양한 것들을 할 수 있습니다.

    ## 긴단한 익스텐션 사용
    
    아래에서는 OPENRNDR 익스텐션을 어떻게 사용하는지 보여줍니다.
    사용할 익스텐션은 `Screenshots`입니다. 이 익스텐션은 스페이스바 키를 누를때마다 어플레케이션의 화면을 캡쳐해 타임스탬프를 파일명으로 하여 저장합니다.
    """

    @Code
    application {
        program {
            // -- one time setup code goes here
            extend(Screenshots())
            extend {

                // -- drawing code goes here
            }
        }
    }

    @Text 
    """
    ## 익스텐션 설정
    
    몇몇 익스텐션은 설정할 수 있는 옵션이 있습니다. 아래의 예제에서처럼 `extend` 함수를 이용하여 셋업할 수 있습니다.
    """

    @Code
    application {
        program {
            extend(Screenshots()) {
                contentScale = 4.0
            }
        }
    }

    @Text 
    """
    ## 익스텐션 함수들 
    
    `extend` 함수는 익스텐션으로써의 단일 함수로 사용할 수 있도록 해줍니다. 
    이러한 기법은 사실 "draw loop"를 생성하는데 사용되기도 합니다.
    
    """

    @Code
    application {
        program {
            extend {
                drawer.circle(width / 2.0, height / 2.0, 50.0)
            }
        }
    }

    @Text
    """
    ## 익스텐션의 순서
        
    `extend(...)` 메소드에 어떠한 순서로 호출을 하는지는 코드의 작성에 따라 달리집니다.
    `Screenshot`과 `ScreenRecorder`은 다른 익스텐션 이전에 위치하여야 합니다. 
    그렇게 하지 않으면, 이미지나 비디오의 결과가 예상과 달라질 것입니다.

    """

    @Text 
    """
    ## 빌트인 기여 익스텐션 
    
    OPENRNDR은 일반적인 수행을 위한 빌트인 익스텐션을 제공합니다. 
    `Screenshots`는 프로그램의 스크린샷을 생성하는데 사용됩니다. 
    `ScreenRecorder`는 비디오 파일로 화면을 캡쳐하여 저장하는데 사용됩니다.

    빌트인 익스텐션 외에는 [ORX](https://github.com/openrndr/orx)가 있습니다. ORX는 OPENRNDS의 추가 기능을 제공합니다.
    `openrndr-template`을 사용하면, `build.gradle./kts`의 `orxFeatures` 프로퍼티 속성을 수정하여 손쉽게 프로젝트에 익스텐션을 추가하거나 삭제할 수 있습니다.
    
    """
}