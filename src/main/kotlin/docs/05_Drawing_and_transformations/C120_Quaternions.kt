@file:Suppress("UNUSED_EXPRESSION")
@file:Title("쿼터니언")
@file:ParentTitle("드로잉과 좌표계")
@file:Order("120")
@file:URL("drawingAndTransformations/quaternions")

package docs.`05_Drawing_and_transformations`

import org.openrndr.application
import org.openrndr.dokgen.annotations.*
import org.openrndr.math.Quaternion
import org.openrndr.math.Quaternion.Companion.fromAngles
import org.openrndr.math.slerp

fun main() {
    @Text 
    """
    # 쿼터니언(Quaternions)
    
    쿼터니언은 회전을 복소수의 확장을 사용하여 표현하는 방법입니다. 쿼터니언및 쿼터니언이 내장하고 있는 함수에 대한 모든 설명은 이문서의 범위를 벗어나므로, 이 섹션에서는 쿼터니언을 도구로 효과적으로 사용할 수 있는 정보들을 제공하려 합니다.
    
    실제로 쿼터니언은 인수의 값을 직관적으로 파악하기가 상당히 어렵기 때문에, 직접 생성할 일은 거의 없습니다.
    """

    @Code.Block
    run {
        val q = Quaternion(0.4, 0.3, 0.1, 0.1)
    }

    @Text
    """

    대신에, 쿼터니언은 오일러 회전각에서 생성되어, 쿼터니언 공간에 연결됩니다. 쿼터니언 도메인에서의 작업은 일관된 회전이 보장되며, 짐벌락을 피할 수 있습니다.
    """

    run {
        val pitch0 = 0.0
        val yaw0 = 0.0
        val roll0 = 0.0
        val pitch1 = 0.0
        val yaw1 = 0.0
        val roll1 = 0.0
        @Code.Block
        run {
            val q0 = fromAngles(pitch0, yaw0, roll0)
            val q1 = fromAngles(pitch1, yaw1, roll1)
            val q = q0 * q1
        }
    }

    @Text
    """
    ## Slerp

    구형 선형 보간(Spherical linear interpolation) 또는 "slerping"는 회전 사이의 보간/혼합 문제를 해결해줍니다.
    """

    run {
        val pitch0 = 0.0
        val yaw0 = 0.0
        val roll0 = 0.0
        val pitch1 = 0.0
        val yaw1 = 0.0
        val roll1 = 0.0
        @Code.Block
        run {
            val q0 = fromAngles(pitch0, yaw0, roll0)
            val q1 = fromAngles(pitch1, yaw1, roll1)
            val q = slerp(q0, q1 , 0.5)
        }
    }

    @Text
    """
    ## 쿼터니언을 행렬로

    당연히 쿼터니언은 행렬로 변환될 수 있습니다.
    쿼터니언은 쿼터니언이 나타내는 방향을 `matrix44`으로 표현하는 `matrix`속성을 갖고 있습니다.
    """

    application {
        program {
            val pitch = 0.0
            val yaw = 0.0
            val roll = 0.0
            @Code.Block
            run {
                val q0 = fromAngles(pitch, yaw, roll)
                drawer.model *= q0.matrix.matrix44
            }
        }
    }
}