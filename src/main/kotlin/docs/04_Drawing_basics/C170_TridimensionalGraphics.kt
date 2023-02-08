@file:Suppress("UNUSED_EXPRESSION")
@file:Title("3차원 그래픽스")
@file:ParentTitle("드로잉 기초")
@file:Order("170")
@file:URL("drawingBasics/tridimensionalGraphics")

package docs.`04_Drawing_basics`

import org.openrndr.WindowMultisample
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.DepthTestPass
import org.openrndr.draw.DrawPrimitive
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.camera.Orbital
import org.openrndr.extra.meshgenerators.boxMesh
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import org.openrndr.shape.*

fun main() {
    @Text """
    # 3D 그래픽스
    
    조명을 시뮬레이션하기 위해 아주 간단한 `shadeStyle`를 적용하여 회전하는 3D 박스를 그려봅시다.
    저수준의 접근 방식을 사용했습니다.
    """

    @Code
    application {
        configure {
            multisample = WindowMultisample.SampleCount(4)
        }
        program {
            val cube = boxMesh(140.0, 70.0, 10.0)

            extend {
                drawer.perspective(60.0, width * 1.0 / height, 0.01, 1000.0)
                drawer.depthWrite = true
                drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL

                drawer.fill = ColorRGBa.PINK
                drawer.shadeStyle = shadeStyle {
                    fragmentTransform = """
                        vec3 lightDir = normalize(vec3(0.3, 1.0, 0.5));
                        float l = dot(va_normal, lightDir) * 0.4 + 0.5;
                        x_fill.rgb *= l; 
                    """.trimIndent()
                }
                drawer.translate(0.0, 0.0, -150.0)
                drawer.rotate(Vector3.UNIT_X, seconds * 15 + 30)
                drawer.rotate(Vector3.UNIT_Y, seconds * 5 + 60)
                drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
            }
        }
    }

    @Text """    
    조명을 시뮬레이션하기 위해 아주 간단한 `shadeStyle`를 적용하여 회전하는 3D 박스를 그려봅시다.
    이번에는 `Orbital`를 사용하여 마우스와 키보드로 조절이 가능한 간단한 3D카메라를 만들어 사용했습니다.
    """

    @Code
    application {
        configure {
            multisample = WindowMultisample.SampleCount(4)
        }
        program {
            val cube = boxMesh(140.0, 70.0, 10.0)
            val cam = Orbital()
            cam.eye = -Vector3.UNIT_Z * 150.0

            extend(cam)
            extend {
                drawer.fill = ColorRGBa.PINK
                drawer.shadeStyle = shadeStyle {
                    fragmentTransform = """
                        vec3 lightDir = normalize(vec3(0.3, 1.0, 0.5));
                        float l = dot(va_normal, lightDir) * 0.4 + 0.5;
                        x_fill.rgb *= l; 
                    """.trimIndent()
                }
                drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
            }
        }
    }

    @Text """    
    3차원 공간에 10개의 2D 사각형을 그려봅시다.
    """

    @Code
    application {
        configure {
            multisample = WindowMultisample.SampleCount(4)
        }
        program {
            val cam = Orbital()
            cam.eye = Vector3.UNIT_Z * 150.0
            cam.camera.depthTest = false

            extend(cam)
            extend {
                drawer.fill = null
                drawer.stroke = ColorRGBa.PINK
                repeat(10) {
                    drawer.rectangle(Rectangle.fromCenter(Vector2.ZERO, 150.0))
                    drawer.translate(Vector3.UNIT_Z * 10.0)
                }
            }
        }
    }

    @Text """
    `drawer.rectangle`이나 `drawer.contour`와 2D 그래픽을  3D에 그려내면, 
    3D용으로 디자인되지 않았으므로 심도와 관련하여 폐색 문제가 발생할 수 있습니다.
    따라서 이런 문제를 방지하기 위해 직접 버텍스 버퍼와 메쉬를 만들어 이런 문제를 해결할 수 있습니다.
    
    ## 추가 자료
    
    - [orx-camera](https://github.com/openrndr/orx/tree/master/orx-camera)
    - [orx-dnk3](https://github.com/openrndr/orx/tree/master/orx-jvm/orx-dnk3)
    - [orx-mesh-generators](https://github.com/openrndr/orx/tree/master/orx-mesh-generators)
    """

}