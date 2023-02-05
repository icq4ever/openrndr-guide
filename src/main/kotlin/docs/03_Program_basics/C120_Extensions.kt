@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Extensions")
@file:ParentTitle("Program basics")
@file:Order("120")
@file:URL("programBasics/extensions")

package docs.`03_Program_basics`

import org.openrndr.application
import org.openrndr.dokgen.annotations.*
import org.openrndr.extensions.Screenshots

fun main() {
    @Text 
    """
    # Extensions

    Extensions add functionality to a Program. Extensions can be used to control how a program draws, setup keyboard and
    mouse bindings and much more.

    ## Basic extension use
    
    Here we demonstrate how to use an OPENRNDR extension. The extension that we use is the `Screenshots` extension, which, when 
    the space bar is pressed will capture the application window's contents and save it to a timestamped file.
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
    ## Extension configuration
    
    Some extensions have configurable options. They can be set using the configuring `extend` function as follows:
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
    ## Extension functions
    
    The functional `extend` function allows one to use a single function as an extension. This is commonly used to
    create a "draw loop".
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
    ## Extension order
        
    The order in which calls to the `extend(...)` method appear in the code matters. 
    `Screenshots` and `ScreenRecorder` should usually be placed before other extensions; 
    otherwise, the content of the produced images or video files may be unexpected.

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