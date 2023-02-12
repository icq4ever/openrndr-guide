@file:Suppress("UNUSED_EXPRESSION")
@file:Title("벡터")
@file:ParentTitle("드로잉과 좌표계")
@file:Order("110")
@file:URL("drawingAndTransformations/vectors")

package docs.`05_Drawing_and_transformations`

import org.openrndr.dokgen.annotations.*


fun main() {
    @Text 
    """
    # 벡터
    
    `Vector2`, `Vector3`, `Vector4` 는 2, 3, 4차원 벡터를 표현하는 클래스입니다. 벡터 인스턴스는 수정이 불가능합니다. 한번 벡터가 인스턴스화 되고 나면 값을 변경할 수 없습니다.
    ```kotlin
    val v2 = Vector2(1.0, 10.0)
    val v3 = Vector3(1.0, 1.0, 1.0)
    val v3 = Vector4(1.0, 1.0, 1.0, 1.0)
    ```
    
    ## 표준 벡터
    
    ```kotlin
    Vector2.ZERO    // (0, 0)
    Vector2.UNIT_X  // (1, 0)
    Vector2.UNIT_Y  // (0, 1)
    
    Vector3.ZERO    // (0, 0, 0)
    Vector3.UNIT_X  // (1, 0, 0)
    Vector3.UNIT_Y  // (0, 1, 0)
    Vector3.UNIT_Z  // (0, 0, 1)
    
    Vector4.ZERO    // (0, 0, 0, 0)
    Vector4.UNIT_X  // (1, 0, 0, 0)
    Vector4.UNIT_Y  // (0, 1, 0, 0)
    Vector4.UNIT_Z  // (0, 0, 1, 0)
    Vector4.UNIT_W  // (0, 0, 0, 1)
    ```
    
    ## 벡터 연산
    벡터 클래스는 가장 필수적인 작업을 위한 연산자 오버로드들을 제공합니다.

    left operand | operator | right operand | result
    -------------|----------|---------------|---------------------------
    `VectorN`    | `+`      | `VectorN`     | 두 벡터의 덧셈
    `VectorN`    | `-`      | `VectorN`     | 두 벡터의 뺄셈
    `VectorN`    | `/`      | `Double`      | 벡터의 스칼라 나눗셈
    `VectorN`    | `*`      | `Double`      | 벡터의 스칼라 곱
    `VectorN`    | `*`      | `VectorN`     | 벡터의 성분별 곱셈  (l.x * r.x, l.y * r.y)
    `VectorN`    | `/`      | `VectorN`     | 벡터의 성분별 나눗셈 (l.x / r.x, l.y / r.y)
    
    벡터연산에 대한 예제는 다음과 같습니다.
    ```kotlin
    val a = Vector2(2.0, 4.0)
    val b = Vector2(1.0, 3.0)
    val sum = a + b
    val diff = a - b
    val scale = a * 2.0
    val div = a / 2.0
    val cwdiv = a / b
    ```
    
    ## 벡터 속성들
    
    property     | description
    -------------|-------------------------
    `length`     | 벡터의 길이
    `normalized` | 정규화된 벡터
    
    ## Swizzling and sizing
    
    Vector2 swizzles는 벡터필드의 재정렬을 허용합니다. 이는 GLSL에서의 일반적인 패턴입니다.
    
    ```kotlin
    val v3 = Vector2(1.0, 2.0).vector3(z=0.0)
    val v2 = Vector3(1.0, 2.0, 3.0).xy
    ```
    
    ## Let/copy 패턴
    
    여기 불변의 성격을 갖는 벡터클래스 작업을 더 편리하게 사용하게 만드는 두가지 패턴을 제시합니다.
    
    Kotlin 데이터 클래스인 Vector에서 가져온 복사 패턴
    
    ```kotlin
    val v = Vector2(1.0, 2.0)
    val w = v.copy(y=5.0)      // (1.0, 5.0)
    ```
    
    Kotlin의 `let`과 `copy`를 조합한 let/copy 패턴 
    
    ```kotlin
    val v = someFunctionReturningAVector().let { it.copy(x=it.x + it.y) }
    ```
    
    ## Mixing
    
    `mix()`를 사용한 vector의 선형 보간 
    
    ```kotlin
    val m = mix(v0, v1, f)
    ```
    
    이는 아래와 같은 코드의 축약된 버전입니다.
    ```kotlin
    val m = v0 * (1.0 - f) + v1 * f
    ```
    
    ## Randomness
    
    랜덤한 벡터를 생성합니다.
    
    ```kotlin
    val v2 = Random.vector2(-1.0, 1.0)
    val v3 = Random.vector3(-1.0, 1.0)
    val v4 = Random.vector4(-1.0, 1.0)
    ```
    
    vector의 무작위 분포를 생성하려면 아래를 참고해주세요.
    [orx-noise](https://guide.openrndr.org/OPENRNDRExtras/noise.html).

    """
}