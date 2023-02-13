@file:Suppress("UNUSED_EXPRESSION")
@file:Title("색상 버퍼")
@file:ParentTitle("고급 드로잉")
@file:Order("100")
@file:URL("advancedDrawing/colorbuffers")

package docs.`06_Advanced_drawing`

import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.*
import java.io.File
import java.nio.ByteBuffer

fun main() {
    @Text 
    """
    # 색상 버퍼
    
    색상 버퍼란 GPU 메모리에 저장된 이미지를 말합니다.
    
    ## 색상 버퍼 생성하기
    
    색상 버퍼는 `collorBuffer()` 함수를 사용해 생성할 수 있습니다.
    """

    @Code.Block
    run {
        val cb = colorBuffer(640, 480)
    }

    @Text 
    """
    ### 버퍼 포맷 지정하기
    
    색상 버퍼는 다양한 포맷으로 생성될수 있습니다.
    버퍼 포맷은 이미지내의 채널의 개수와 순서를 지정해줍니다. 
    색상버퍼는 1에서 4개의 채널을 가질 수 있습니다. 
    `format`인수는 [`ColorFormat`](https://github.com/openrndr/openrndr/blob/v0.4.0-rc.7/openrndr-draw/src/commonMain/kotlin/org/openrndr/draw/DrawStyle.kt#L108)중 하나를 사용할 수 있습니다.
    
    """

    @Code.Block
    run {
        val cb = colorBuffer(640, 480, format = ColorFormat.R)
    }

    @Text 
    """
    ### 버퍼 타입 지정하기
    
    버퍼 타입은 버퍼내에 색상을 저장하기 위하 사용되는 데이터 타입입니다.
    `type` 인자는 [`ColorType`](https://github.com/openrndr/openrndr/blob/v0.4.0-rc.7/openrndr-draw/src/commonMain/kotlin/org/openrndr/draw/DrawStyle.kt#L153)중 하나를 사용할 수 있습니다.
    """

    @Code.Block
    run {
        val cb = colorBuffer(640, 480, type = ColorType.FLOAT16)
    }

    @Text 
    """
    ## 색상 버퍼 불러오기

    색상 버퍼는 디스크에 저장된 이미지에서 불러올 수 있습니다.
    지원되는 파일 포맷은 png, jpg, dds, exr(OpenEXR)이 있습니다.
    """

    @Code.Block
    run {
        val cb = loadImage("data/images/pm5544.jpg")
    }

    @Text
    """
    ## 색상 버퍼 해제하기

    새 버퍼를 생성해 프로그램을 구동한다면, 구동중 메모리 누수를 방지하기 위해, 더이상 사용하지 않은 이미지 버퍼들을 해제하는것이 중요합니다.
    """

    run {
        val cb = colorBuffer(640, 480)
        @Code.Block
        run {
            // -- 버퍼사용을 완료했다면, destroy()를 사용해 버퍼메모리를 해제합니다.
            cb.destroy()
        }
    }

    @Text 
    """
    ## 색상 버퍼를 저장하기
    
    `saveToFile` 멤버함수를 사용하여 색상버퍼를 디스크에 저장할 수 있습니다.
    지원되는 파일 포맷은 png, jpg, dds, exr(OpenEXR)이 있습니다.
    """

    @Code.Block
    run {
        val cb = colorBuffer(640, 480)
        cb.saveToFile(File("output.jpg"))
    }

    @Text 
    """
    색상버퍼를 비동기 방식으로(이는 디폴트입니다) 반복해서 저장한다면, 메모리가 부족해질 가능성이 있습니다.
    이러한 문제는 소프트웨어가 파일을 저장하는 속도가 요쳥된 속도를 따라가지 못할때 발생합니다.
    이러한 경우 `saveToFile()`에 `async = false`로 명시적으로 지정해주거나, `saveToFile` 대신 [VideoWriter](https://guide.openrndr.org/videos/writingToVideoFiles.html#writing-to-video-using-render-targets)를 [pngSequence](https://github.com/openrndr/orx/tree/master/orx-jvm/orx-video-profiles#png-sequence) 또는 [tiffSequence](https://github.com/openrndr/orx/tree/master/orx-jvm/orx-video-profiles#tiff-sequence)와 함께 사용할 수 있습니다.
     
    몇몇 이미지 파일 포맷들은 저장하는 시간이 더 걸릴 수 있음에 유의하세요.

    ## 색상버퍼끼리 복사하기
    
    색상 버퍼의 내용들은 `copyTo` 멤버함수를 사용하여 복사될 수 있습니다.
    서로 다른 포맷과 형식끼리도 복사할 수 있습니다.
    """

    @Code.Block
    run {
        // -- create color buffers
        val cb0 = colorBuffer(640, 480, type = ColorType.FLOAT16)
        val cb1 = colorBuffer(640, 480, type = ColorType.FLOAT32)
        //-- copy contents of cb0 to cb1
        cb0.copyTo(cb1)
    }

    @Text 
    """
    ## 색상 버퍼에 쓰기
    
    데이터를 색상버퍼에 업로드하기 위해서는 `write` 멤버함수를 사용합니다.
    """

    @Code.Block
    run {
        // -- create a color buffer that uses 8 bits per channel (the default)
        val cb = colorBuffer(640, 480, type = ColorType.UINT8)

        // -- create a buffer (on CPU) that matches size and layout of the color buffer
        val buffer = ByteBuffer.allocateDirect(cb.width * cb.height * cb.format.componentCount * cb.type.componentSize)

        // -- fill buffer with random data
        for (y in 0 until cb.height) {
            for (x in 0 until cb.width) {
                for (c in 0 until cb.format.componentCount) {
                    buffer.put((Math.random() * 255).toInt().toByte())
                }
            }
        }

        // -- rewind the buffer, this is essential as upload will be from the position we left the buffer at
        buffer.rewind()
        // -- write into color buffer
        cb.write(buffer)
    }

    @Text 
    """
    ## 색상 버퍼에서 읽어오기
    
    색상 버퍼에서 데이터를 받아오기 위해 `read` 멤버함수를 사용합니다.
    """

    @Code.Block
    run {
        // -- create a color buffer that uses 8 bits per channel (the default)
        val cb = colorBuffer(640, 480, type = ColorType.UINT8)

        // -- create a buffer (on CPU) that matches size and layout of the color buffer
        val buffer = ByteBuffer.allocateDirect(cb.width * cb.height * cb.format.componentCount * cb.type.componentSize)

        // -- download data into buffer
        cb.read(buffer)
    }

    @Text 
    """
    ## 색상 버퍼 쉐도우
    
    색상 버퍼에 대해 읽고 쓰기과정을 단순화 하기 위해 우리는 `ColorBuffer`에 쉐도우 버퍼를 추가해두었습니다.
    쉐도우 버퍼는 색상버퍼의 내용에 접근할 수 있는 간단한 인터페이스를 제공합니다.
    
    단, `read()`와 `write()`를 직접 사용하는 것에 비해 약간의 오버헤드가 있다는 점을 기억하세요.
    """

    @Code.Block
    run {
        // -- create a color buffer that uses 8 bits per channel (the default)
        val cb = colorBuffer(640, 480, type = ColorType.UINT8)
        val shadow = cb.shadow

        // -- download cb's contents into shadow
        shadow.download()

        // -- place random data in the shadow buffer
        for (y in 0 until cb.height) {
            for (x in 0 until cb.width) {
                shadow[x, y] = ColorRGBa(Math.random(), Math.random(), Math.random())
            }
        }

        // -- upload shadow to cb
        shadow.upload()
    }
}



