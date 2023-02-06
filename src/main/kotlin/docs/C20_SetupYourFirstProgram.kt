@file:Suppress("UNUSED_EXPRESSION")
@file:Title("첫 프로그램을 셋업해봅시다")
@file:ParentTitle("OPENRNDR이 뭐죠?")
@file:Order("20")
@file:URL("/setUpYourFirstProgram")

package docs

import org.openrndr.dokgen.annotations.*

fun main() {
    @Text
    """
    # OPENRNDR 시작하기
    
    Let's get it started!
    
    ## 환경 설정하기 
    
    OPENRNDR을 편집하고 실행하기 위해서는 몇가지 소프트웨어 도구를 설치해야 합니다.
    
     * `git`이 설치되어 있는지 확인하세요. 만약에 없다면 [Git website](https://git-scm.com/)에서 설치할 수 있습니다.
       from the 
    
     * [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download)를 다운받아 설치하세요.
     처음 실행하면 기본설정을 제공할 수 있는데, 기본 설정을 사용하시기 바랍니다.
     
    ## 템플릿 clone 하기
    
    OPENRNDR 기반의 프로젝트를 간편하게 시작하기 위해 추천되는 방법은 [`openrndr-템플릿`](https://github.com/openrndr/openrndr-template)을 사용하는 것입니다.
    이 템플릿은 첫 프로그램을 바로 실행할수 있는 프로젝트입니다.
    
     * IntelliJ Idea에서 메뉴가 보이신다면 "File > New > Project from version control..." 를 선택하세요.
      만약 처음 실행하거나, 열린 프로젝트가 없다면, 프로그램 메뉴가 보이지 않을겁니다. 
      우측 상단의  "Get from VCS" 를 클릭하세요.
     * 다이얼로그에서 :
       * Version Control: "Git"을 선택하세요.
       * URL: `https://github.com/openrndr/openrndr-template`를 입력하세요
       * Directory: 저장할 위치를 선택합니다.
     * 프로젝트를 어디에서 열지 물어볼때, "new window"를 선택하세요.
    
  
    만약 clone이 실패한다면, Git이 설치되어있는지 확인하세요. 저장소 URL이 올바른지도 확인해주시기 바랍니다.
    
    ## 첫 프로그램 실행하기 
    
    템플릿을 복제하면 IntelliJ IDEA가 의존성패키지를 다운받고 인덱싱 하는 걸 보실수 있습니다, 처음이라면 상당한 시간이 걸릴것입니다.
    IntelliJ 창의 하단에 위치한 상태바에서 진행상황을 확인할 수 있습니다.
       
    아마 IntelliJ에서 `openrndr-template` 프로젝트의 `README.md`가 열려있는걸 알아차리신 분들이 있을겁니다. 템플릿 프로젝트에 관한 도움말이 담겨 있습니다.

    IntelliJ 인덱싱이 끝나면, 프로젝트 뷰의 `src/main/kotlin/TemplateProgram.kt` 파일을 클릭하세요
    IntelliJ의 버전에 따라 프로젝트뷰가 접혀있을 수 있습니다. 좌측의 세로 탭에서 프로젝트뷰를 클릭하여 열 수 있습니다.
     
    템플릿 프로그램이 열리면, `fun main()` 옆에 작은 삼각형 버튼이 보일것입니다. 삼각형을 클릭하면 프로그램이 실행됩니다.
    
    *Lo' and behold!*
    
    ## 맥OS 사용자를 위한 팁
    
    프로그램을 선택하면 콘솔창에 `Warning: Running on macOS without -XstartOnFirstThread JVM argument` 경고 메시지가 뜰겁니다.
    해당 경고를 없애려면 (디버깅 역시 활성화됩니다):
    - 메뉴에서 `Run > Edit Configurations...`를 클릭하고 
    - in the `VM Options`의 [text field](https://stackoverflow.com/a/44184837)에  `-XstartOnFirstThread`를 추가합니다. 
    - `Ok`를 눌러 다이얼로그를 닫습니다.
    
    ## 그 다음은 뭐가 있죠?
    
    여기서 이 프로그앰의 구조가 어떻게 되는지 궁금하실겁니다.
    [프로그램 기초](https://guide.openrndr.org/programBasics/applicationProgram.html) 챕터에서 이어집니다.
    
    소스코드가 어떻게 되는지 궁금하시다면, [`openrndr-examples` 저장소](https://github.com/openrndr/openrndr-examples). 에서 이 가이드에서 사용되는 예제코드들을 볼 수 있습니다. 
    보다 고급 예제들이 궁금하시다면, [`orx` 저장소](https://github.com/openrndr/orx)를 살펴보시길 바랍니다. 대부분의 `orx` 모듈은 `src/demo/kotlin` 폴더에 데모를 갖고 있습니다.
    """
}