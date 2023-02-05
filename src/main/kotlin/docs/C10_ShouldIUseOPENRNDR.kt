@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Should I use OPENRNDR?")
@file:ParentTitle("What is OPENRNDR?")
@file:Order("10")
@file:URL("/shouldIUseOPENRNDR")

package docs

import org.openrndr.dokgen.annotations.*

fun main() {
    @Text
    """
    # OPENRNDR를 사용해야 할까요??
    
    그럼요!
    
    .. 하지만—
    
    ## 대상 
    
    혹시 컴퓨터 그래픽 프로그래밍 경험이 처음이라면, 먼저 _Processing_ 언어를 접해보시는걸 추천드립니다. Processing은 프로그래밍을 처음 접하는 분들을 위해 디자인 되어있어서 학습커브가 얕습니다. 
    즉, OPENRNDR은 단순함과 일관성에 촛점을 맞추었다고 믿고 있으므로 엄청 어렵진 않을겁니다. 혹시 Processing 경험이 있으시다구요? 그렇다면 프로세싱 포럼에서 프로세싱과 OPENRNDR을 비교하는 [이 게시글](https://openrndr.discourse.group/search?q=%22Processing%22%20%23openrndr%3Atutorials)을 읽어보시길 바랍니다. 
    
    ## 지원 플랫폼
    
    OPENRNDR 은 현재 맥OS, 윈도우, 리눅스를 포함한 데스크탑 플랫폼에서 동작합니다.

    맥OS의 경우, 10.10에서 10.14까지를 지원합니다. 이전 버전은 동작할 수도 있지만, 확인하기 어렵습니다.

    윈도우의 경우, 윈도우 10를 지원합니다. 윈도우 8.1에서는 동작할 수 있지만 확인되지 않았습니다.

    리눅스의 경우, 우분두 18.04 LTS를 지원합니다. 다른 버전/배포판에서도 동작할 것입니다.

    OPENRNDR은 최소 OpenGL 3.3을 지원하는 그래픽카드를 필요로 합니다. 이는 상대적으로 오래된 GPU인 nVidia 320M, intel HD4000을 포함합니다만 intel HD3000은 제외됩니다.
    
    ## 지원되는 프로그래밍 언어

    OPENRNDR은 코틀린으로 작성되었으며, JVM(자바 가상머신) 위에서 동작하도록 되어있습니다. 우리는 코틀린이 읽기 쉽고 잘 설명되는, 두가지 성격을 잘 갖춘 프로그래밍 언어라고 생각합니다.

    라이브러리는 코틀린 Java-interop에 따라 Java 8 이상에서 사용될 수 있지만, OPENRNDR에서 제공되는 API들이 코틀린에 특화된 광범위한 사용을 가능하게 해주기 때문에 Java로 번역이 잘 되지 않을 수 있습니다.

    
    ## 지원되는 개발 환경
    
    OPENRNDR은 특정 환경에 국한되지 않습니다만, 제공되는 모든 튜토리얼과 레퍼런스는 Gradel과 IntelliJ가 사용되었습니다.
    
    ## 장기적인 지원 
    
    아직 안정화된 API를 약속할 단계에 다다르지는 않았습니다. OPENRNDR은 현재 pre-1.0 버전이며, 계획은 없지만, API의 불완정성, 혹은 불일치함을 유지하는것보다 변경하는것이 낫다고 판단되면 변경될 수 있습니다.
    
    """
}