@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Render targets")
@file:ParentTitle("고급 드로잉")
@file:Order("105")
@file:URL("advancedDrawing/renderTargets")

package docs.`06_Advanced_drawing`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.*

fun main() {
    @Text 
    """
    # Render target과 색상 버퍼
    
    `RenderTarget`은 어디에 그릴것읜지를 명시합니다. `RengerTarget`에는 두 종류의 버퍼 첨부가 있습니다.

    `ColorBuffer` 첨부와 `DepthBuffer`첨부가 그것입니다.

    `RenderTarget`에 무언가를 그리기 위해서는 최소한 하나의 `ColorBuffer`가 필요합니다.

    `ColorBuffer`는 4채널 색상을 갖고있는 버퍼입니다.
    `ColorBuffer`는 8비트 int, 혹은 16/32비트 float 채널을 가질 수 있습니다.
    
    `DepthBuffe`는 깊이와 스텐실 값을 가지는 버퍼입니다.

    ## render target 생성하기
    
    `RenderTarget` 인스턴스를 생성하기 위해 권장하는 방법은 `renderTarget{}` 빌더를 사용하는 것입니다.
    """

    @Code.Block
    run {
        val rt = renderTarget(640, 480) { }
    }

    @Text 
    """
    위 코드는 render target를 생성하지만, 생성한 render target은 실제 이미지 데이터를 갖고 있는 첨부자료를 갖고 있지 않습니다.
    아래의 코드는 하나의 color buffer 첨부와 함께 render target을, 빌더를 사용하여 생성하고 있습니다.
    """

    @Code.Block
    run {
        val rt = renderTarget(640, 480) {
            colorBuffer()
        }
    }

    @Text 
    """
    ## render target에 그리기

    아래의 코드는 off-screen 버퍼에 이미지를 그린 뒤, offscreen 버퍼를 화면에 그려내는 방법을 보여주고 있습니다.
    
    """

    @Code
    application {
        program {
            // -- color buffer 를 포함하여 render target을 빌드합니다.
            val rt = renderTarget(width, height) {
                colorBuffer()
            }

            extend {
                // 컬러버퍼를 대상으로 그립니다.
                drawer.isolatedWithTarget(rt) {
                    drawer.clear(ColorRGBa.BLACK)
                    drawer.fill = ColorRGBa.WHITE
                    drawer.stroke = null
                    drawer.rectangle(40.0, 40.0, 80.0, 80.0)
                }

                // 컬러버퍼를 화면에 그립니다.
                drawer.image(rt.colorBuffer(0))
            }
        }
    }

    @Text 
    """
    ## render target과 프로젝션 변환
    
    프로젝션 변환은 반드시 render target에 맞게 설정되어야 함을 명심하시기 바랍니다. 
    이는 특히나 설정된 render target과 윈도우의 크기가 다를때 명확해집니다. 직교(2D) 프로젝션의 경우는 다음과 같이 사용할 수 있습니다:
    """

    @Code
    application {
        program {
            // -- build a render target with a single color buffer attachment
            val rt = renderTarget(400, 400) {
                colorBuffer()
            }

            extend {
                drawer.isolatedWithTarget(rt) {
                    drawer.clear(ColorRGBa.BLACK)

                    // -- set the orthographic transform that matches with the render target
                    ortho(rt)

                    drawer.fill = ColorRGBa.WHITE
                    drawer.stroke = null
                    drawer.rectangle(40.0, 40.0, 80.0, 80.0)
                }

                // -- draw the backing color buffer to the screen
                drawer.image(rt.colorBuffer(0))
            }
        }
    }

    @Text 
    """
    ## render target과 알파 채널 조합하기
    
    OPENRNDR은 알파 채널에 인코딩된 투명도와 `RenderTargets`를 사용하여 합성할 수 있습니다.
    아래의 코드는 두 `RenderTarget` 인스턴스를 `ColorRGBa.TRANSPARENT`를 사용하여 초기화 하는 것을 보여줍니다.
    """

    @Code
    application {
        program {
            val rt0 = renderTarget(width, height) { colorBuffer() }
            val rt1 = renderTarget(width, height) { colorBuffer() }

            extend {
                drawer.stroke = null

                // -- bind our first render target, clear it, draw on it, unbind it
                drawer.isolatedWithTarget(rt0) {
                    drawer.clear(ColorRGBa.TRANSPARENT)
                    drawer.fill = ColorRGBa.WHITE
                    drawer.rectangle(40.0, 40.0, 80.0, 80.0)
                }

                // -- bind our second render target, clear it, draw on it, unbind it
                drawer.isolatedWithTarget(rt1) {
                    drawer.clear(ColorRGBa.TRANSPARENT)
                    drawer.fill = ColorRGBa.PINK
                    drawer.rectangle(140.0, 140.0, 80.0, 80.0)
                }
                // -- draw the backing color buffer to the screen
                drawer.image(rt0.colorBuffer(0))
                drawer.image(rt1.colorBuffer(0))
            }
        }
    }

    @Text 
    """
    ## 고정밀 부동소수점 render target 만들기
    
    색상 버퍼의 기본 포맷은 unsigned 8비트 RGBa입니다. 하지만 부동소수점 또한 지원합니다.
    """

    @Code.Block
    run {
        val rt = renderTarget(640, 480) {
            colorBuffer(ColorFormat.RGBa, ColorType.FLOAT16)
            colorBuffer(ColorFormat.RGBa, ColorType.FLOAT32)
        }
    }

    @Text 
    """
    ## 멀티 샘플링과 안티앨리어싱
    
    render target은 멀티샘플링과 안티앨리어싱을 사용할 수 있습니다.
    `renderTarget{}` 빌더 내에 추가되는 모든 색상 및 깊이 버퍼는 동일한 멀티샘플링 설정이 적용됩니다.
    """

    @Code.Block
    run {
        // -- here we create a multi-sampled render target
        val rt = renderTarget(640, 480, multisample = BufferMultisample.SampleCount(8)) {
            colorBuffer(ColorFormat.RGBa, ColorType.FLOAT16)
            colorBuffer(ColorFormat.RGBa, ColorType.FLOAT32)
        }
    }

    @Text 
    """
    멀티샘플링된 render target에 첨부된 색상버퍼는 바로 그려낼 수 없습니다. 사용하기 전 컬러버퍼는 설정 처리를 먼저 해주어야 합니다.
    """

    @Code
    application {
        program {
            // -- build a render target with a single color buffer attachment
            val rt = renderTarget(width, height, multisample = BufferMultisample.SampleCount(8)) {
                colorBuffer()
                depthBuffer()
            }

            val resolved = colorBuffer(width, height)

            extend {
                drawer.isolatedWithTarget(rt) {
                    drawer.clear(ColorRGBa.BLACK)
                    drawer.fill = ColorRGBa.WHITE
                    drawer.stroke = null
                    drawer.circle(0.0, 0.0, 400.0)
                }

                // -- resolve the render target attachment to `resolved`
                rt.colorBuffer(0).copyTo(resolved)

                // draw the backing color buffer to the screen
                drawer.image(resolved)

                // draw a second circle with no multisampling to compare
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = null
                drawer.circle(width * 1.0, height * 1.0, 400.0)
            }
        }
    }

    @Text
    """
    ## 깊이 버퍼
     
    깊이(심도) 버퍼는 `Shape`과 `ShapeContour`요소들을 render target에 그려낼때 필요합니다. 
    깊이 버퍼가 없다면, 실행에 실패하며 에러 메시지로 필요한 내용을 알려줍니다.
    
    3D 그래픽을 그려낼떄 역시 깊이버퍼가 필요합니다. 이를 사용하여 카메라 근처의 요소가 멀리 있는 요소 앞에 그려지게 됩니다.
    
    ## 버퍼 초기화하기
       
    """

    run {
        val rt = renderTarget(640, 480) {
            colorBuffer()
            depthBuffer()
        }

        @Code.Block
        run {
            // 색상 버퍼를 초기화합니다.
            rt.clearColor(0, ColorRGBa.TRANSPARENT)

            // 심도 버퍼를 초기화합니다.
            rt.clearDepth()
        }
    }

    @Text 
    """
    ## 첨부데이터에 이름 붙이기
    """

    @Code.Block
    run {
        val rt = renderTarget(640, 480) {
            colorBuffer("albedo", ColorFormat.RGBa, ColorType.FLOAT16)
            colorBuffer("position", ColorFormat.RGBa, ColorType.FLOAT32)
        }
    }
}



