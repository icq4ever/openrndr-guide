@file:Suppress("UNUSED_EXPRESSION")
@file:Title("어플리케이션 프로그램")
@file:ParentTitle("프로그램 기초")
@file:Order("100")
@file:URL("programBasics/applicationProgram")

package docs.`03_Program_basics`

import org.openrndr.application
import org.openrndr.dokgen.annotations.*

fun main() {

    @Text 
    """
    # 프로그램 기초

    OPENRNDR이 어떤 구조를 갖고 있는지 살펴봅시다.
    대부분의 프로그램은 아래와 같은 구조로 이루어져 있습니다.
    """

    @Code
    application {
        configure {

            // 설정 내용이 이곳에 작성됩니다.
        }

        program {
            // -- 한번만 실행되는 내용이 이곳에 작성됩니다.
            extend {

                // -- `가능한 한 자주` 실행되는 내용이 이곳에 작성됩니다.
            }
        }
    }

    @Text
    """
    ## application 블럭
    
    `application` 블럭은 우리가 작성하는 소프트웨어의 런타임 환경을 설정하는데 사용됩니다.
    이 블럭은 두개의 블럭으로 구성됩니다: `configure`와 `program` 입니다.
    
    ## configure 블럭
    
    `configure` 블럭은 옵션 블럭으로, 런타임환경을 설정하는데 사용됩니다. 일반적으로 실행 윈도우의 크기를 지정하는내용이 작성됩니다.

    윈도우의 크기, 윈도우의 리사이즈 가능여부, 윈도우 타이틀을 지정하는 예제입니다:
    """

    @Code
    application {
        configure {
            width = 1280
            height = 720
            windowResizable = true
            title = "OPENRNDR Example"
        }
        program {
        }
    }

    @Text
    """
    ## program 블럭
        
    program 블럭은 실제로 동작하는 로직입니다.  `program {}`은 [`Program`](https://github.com/openrndr/openrndr/blob/v0.4.0-rc.7/openrndr-application/src/commonMain/kotlin/org/openrndr/Program.kt#L63) 리시버를 갖는다는 점을 참고하세요.
    
    `program` 블럭 내부에 작성되는 코드는 윈도우가 생성되고, 그래픽 콘텍스트가 셋업된 이후에만 시작됩니다. 이 코드는 한번만 실행됩니다.
    The code inside the `program` block is only executed after a window has 
    been created and a graphical context has been set up. This code is only 
    executed once.

    코드 블럭에서, `extend`를 사용하여 익스텐션을 설치할 수 있습니다. 익스텐션들은 기본적으로 가능한 한 자주 실행됩니다. 
    가장 중요한 익스텐션의 타입은 사용자 코드를 갖고 있는 타입니다.
    
    From the code block one can install extensions using `extend`. Extensions 
    are by default executed as often as possible. The most important type of 
    extension is the one that holds user code.
    
    최소한의 application-program-extend 셋업은 아래와 같습니다:
    """

    @Code
    application {
        program {
            extend {
                drawer.circle(width / 2.0, height / 2.0, 100.0)
            }
        }
    }
}
