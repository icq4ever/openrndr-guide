@file:Suppress("UNUSED_EXPRESSION")
@file:Title("그리기 스타일 관리")
@file:ParentTitle("드로잉 기초")
@file:Order("140")
@file:URL("drawingBasics/managingDrawStyle")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.isolated

fun main() {
    @Text 
    """
    # 그리기 스타일의 관리
    
    이전 섹션까지는 기초도형의 모양을 제어하는 법을 살펴보았습니다.
    이 섹션에서는 그리기 스타일과 그리기 스타일을 관리하는 도구들을 살펴봅니다.
    
    #### (그리기) 스타일의 모든 요소
    
    Property             | Type             | Default            | Description
    ---------------------|------------------|--------------------|-------------------
    `fill`               | `ColorRGBa?`     | `ColorRGBa.BLACK`  | The fill color
    `stroke`             | `ColorRGBa?`     | `ColorRGBa.BLACK`  | The stroke color
    `strokeWeight`       | `Double`         | `1.0`              | The stroke weight
    `lineCap`            | `LineCap`        |                    |
    `lineJoin`           | `LineJoin`       |                    |
    `fontMap`            | `FontMap?`       | `null`             | The font to use
    `colorMatrix`        | `Matrix55`       | `IDENTITY`         | The color matrix (used for images)
    `channelWriteMask`   | `ChannelMask`    | `ALL`              | The channel write mask
    `shadeStyle`         | `ShadeStyle?`    | `null`             | The shade style
    `blendMode`          | `BlendMode`      | `OVER`             | The blend mode
    `quality`            | `DrawQuality`    | `QUALITY`          | A hint that controls the quality of some primitives
    `depthTestPass`      | `DepthTestPass`  | `ALWAYS`           | When a fragment should pass the depth test
    `depthWrite`         | `Boolean`        | `false`            | Should the fragment depth be written to the depth buffer?
    `stencil`            | `StencilStyle`   |                    | The stencil style
    `frontStencil`       | `StencilStyle`   |                    | The stencil style for front-facing fragments
    `backStencil`        | `StencilStyle`   |                    | The stencil style for back-facing fragments
    `clip`               | `Rectangle?`     | `null`             | A rectangle that describes where drawing will take place
    
    ## 활성화된 그리기 스타일 
    
    ```kotlin
    val active = drawer.drawStyle.copy()
    ```
    
    ## 스타일 스택 
    
    스타일은 `Drawer`에 의해 스택의 구조로 push되거나 pop될 수 잇습니다.
    
    """

    application {
        program {
            @Code
            extend {
                drawer.pushStyle()
                drawer.fill = ColorRGBa.PINK
                drawer.rectangle(100.0, 100.0, 100.0, 100.0)
                drawer.popStyle()
            }
        }
    }

    @Text 
    """
    `Drawer`는 `isolated {}`라 불리우는 헬퍼 함수를 제공합니다. 이 함수를 사용하여 스타일(style)과 좌표계(transform)를 스택에 push 하여 사용자 코드를 실행하고, 그 뒤 pop을 하여 스타일과 좌표계 되돌립니다.
    """

    application {
        program {
            @Code
            extend {
                drawer.isolated {
                    fill = ColorRGBa.PINK
                    rectangle(100.0, 100.0, 100.0, 100.0)
                }
            }
        }
    }
}
