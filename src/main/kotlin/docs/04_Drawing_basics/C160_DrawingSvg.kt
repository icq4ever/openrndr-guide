@file:Suppress("UNUSED_EXPRESSION")
@file:Title("SVG 그리기")
@file:ParentTitle("드로잉 기초")
@file:Order("160")
@file:URL("drawingBasics/drawingSVG")

package docs.`04_Drawing_basics`

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.math.Vector2
import org.openrndr.shape.*
import org.openrndr.svg.loadSVG
import org.openrndr.svg.saveToFile
import java.io.File

fun main() {
    @Text 
    """
    # SVG 그리기
    
    컴포지션을 불러와 그것을 그려내는 방법은 아래와 같습니다:
    """

    @Code
    application {
        program {
            val composition = loadSVG("data/drawing.svg")
            extend {
                drawer.composition(composition)
            }
        }
    }

    @Text 
    """
    OPENRNDR은 Tiny SVG 1.x 프로파일로 저장된 SVG파일과 가장 호환성이 좋습니다.    
    
    SVG에서 불러온 컴포지션을 그려낼때, `Drawer`의 색이 아닌, SVG파일에서 지정된 선과 fill의 색상이 사용되었음을 알 수 있습니다.
    
    ## Compositions
    
    `Composition`은 `Composition.root`를 루트노드로 하는 트리 구조를 갖고 있습니다.
    
    ##### CompositionNode 타입들 
    
    Node Type         | Function
    ------------------|-----------------------------------------
    `GroupNode`       | Holds multiple child nodes in `children`
    `ShapeNode`       | Holds a a single shape in `shape`
    `TextNode`        | Holds text (currently not implemented)
    `ImageNode`       | Holds an image (currently not implemented)
    `CompositionNode` | The base class for composition node
    
    ##### CompositionNode 프로퍼티
    
    Property name                | Property type      | Description
    -----------------------------|--------------------|------------
    `transform`                  | `Matrix44`         | local transformation
    `fill`                       | `CompositionColor` | fill color
    `stroke`                     | `CompositionColor` | stroke color
    `id`                         | `String?`          | node id
    `parent`                     | `CompositionNode?` | node parent
    `effectiveFill` (read-only)  | `ColorRGBa?`       | the effective fill color, potentially inherited from ancestor
    `effectiveStroke`(read-only) | `ColorRGBa?`       | the effective stroke color, potentially inherited from ancestor
    
    ##### GroupNode 프로퍼티
    
    Property name   | Property type                   | Description
    ----------------|---------------------------------|---------------
    `children`      | `MutableList<CompositionNode>`  | child nodes
    
    ##### ShapeNode 프로퍼티
    
    Property name   | Property type  | Description
    ----------------|----------------|---------------
    `shape`         | `Shape`        | a single shape
    
    ## 컴포지션 조회하기
    
    ### 컴포지션 내 모든 shape 찾기 
    
    ```kotlin
    val shapeNodes = composition.findShapes()
    ```
    
    ## 컴포지션 수정
    
    `Composition`은 여러 수정가능한 파트들을 갖고 있으므로, composition의 일부를 쉽게 교체 할 수 있습니다.
    Since a `Composition` contains many immutable parts it is easier to (partially) replace parts of the composition.
    
    ```kotlin
    val m = translate(1.0, 0.0, 0.0);
    composition.root.map {
      if (it is ShapeNode) {
        it.copy(shape=it.shape.transform(m))
      } else {
        it
      }
    }
    ```
    ## 수동으로 컴포지션 생성하기
    
    컴포지션은 트리구조로 되어있어 수동으로 생성할 수 있습니다. 
    아래 코드는 `Composition`을 생성하는 방법을 보여주는 예제입니다.
    
    """

    @Code.Block
    run {
        val root = GroupNode()
        val composition = Composition(root)
        val shape = Circle(200.0, 200.0, 100.0).shape
        val shapeNode = ShapeNode(shape)
        shapeNode.fill = ColorRGBa.PINK
        shapeNode.stroke = ColorRGBa.BLACK
        // -- 루트에 shape 노드를 추가합니다
        root.children.add(shapeNode)
    }

    @Text 
    """
    ## 컴포지션 Drawer
    
    OPENRNDR는 컴포지션을 생성하기 위한 훨씬 편리한 인터페이스를 제공합니다.
    이 인터페이스는 `Drawer`와 유사한 방법으로 동작합니다.

    아래는 `drawComposition {}`위 예제와 똑같은 컴포지션을 생성하는 코드입니다.
    """

    @Code.Block
    run {
        val composition = drawComposition {
            fill = ColorRGBa.PINK
            stroke = ColorRGBa.BLACK
            circle(Vector2(100.0, 100.0), 50.0)
        }
    }

    @Text 
    """
    # 좌표변환(Transforms)
        
    좌표변환(Transforms) 역시 `Drawer`와 같은 방식으로 동작합니다.
    """
    @Code.Block
    run {
        val composition = drawComposition {
            fill = ColorRGBa.PINK
            stroke = ColorRGBa.BLACK
            isolated {
                for (i in 0 until 100) {
                    circle(Vector2(0.0, 0.0), 50.0)
                    translate(50.0, 50.0)
                }
            }
        }
    }

    @Text 
    """
    # 컴포지션을 SVG로 저장하기
    
    컴포지션은 `saveToFile` 함수를 이용해 SVG 파일로 저장할 수 있습니다.
    """

    @Code.Block
    run {
        // -- load in a composition
        val composition = loadSVG("data/example.svg")
        composition.saveToFile(File("output.svg"))
    }

}