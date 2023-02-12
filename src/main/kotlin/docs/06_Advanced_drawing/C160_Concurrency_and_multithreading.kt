@file:Suppress("UNUSED_EXPRESSION")
@file:Title("Concurrency and multithreading")
@file:ParentTitle("고급 드로잉")
@file:Order("160")
@file:URL("advancedDrawing/concurrencyAndMultithreading")

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.dokgen.annotations.*
import org.openrndr.draw.*
import org.openrndr.internal.finish
import org.openrndr.launch
import java.nio.ByteBuffer
import kotlin.random.Random

fun main() {
    @Text 
    """
    # 동시성과 멀티-스레딩
    

    이제 OPENRNDR의 동시성을 위한 프리미티브에 대해 얘기해볼까 합니다.

    OPENRNDR의 멀티스레딩에서 가장 큰 문제는 OpenGL 컨텍스트가 활성화된 스레드만 
    graphics API(OpenGL)를 사용할 수 있다는 점입니다.
    이는 `Drawer`, `ColorBuffer`, `VertexBuffer`, `RenderTarget`, `Shader`, `BufferTexture`, `CubeMap`, `ArrayTextuire`와의 
    모든 상호작용은 기본 draw 스레드 혹은 특별하게 생성된 draw 스레드에서만 수행될 수 있다는 의미입니다.

    ## 코루틴(Coroutines)
    
    여기서 얘기하는 코루틴은 동시성을 위한 코틀린(Kotlin) 전용 프레임워크 입니다. 
    Kotlin 레퍼런스의 [coroutines overview](https://kotlinlang.org/docs/reference/coroutines-overview.html)를 읽어보세요.
    
    `Program`에는 코루틴이 기본 draw 스레드에서 처리되도록 보장하는 자체 코루틴 디스패처가 함께 제공됩니다.- 
    이것은 프로그램 디스패처에 의해 실행되거나 재개될때 코루틴이 draw 스레드를 블로킹 한다는 것을 의미합니다.

    아래의 예제에서는 99까지 천천히 카운트하는 코루틴을 실행합니다.
    코루틴 내의 delay는 기본 draw 스레드를 블로킹 하지 *않습니다*.
    """

    @Code
    application {
        program {
            var once = true
            extend {
                if (once) {
                    once = false
                    launch {
                        for (i in 0 until 100) {
                            println("Hello from coroutine world ($i)")
                            delay(100)
                        }
                    }
                }
            }
        }
    }

    @Text 
    """
    코루틴을 실행하는것이 메인 draw 스레드를 블로킹 한다면, 도대체 `Program` 디스패처의 목적이 뭔지 궁금할겁니다.
    코루틴을 블로킹 하는것은 수행이 가벼울때에는 유용하다는것이 이에 대한 답변이 될 것입니다.

    You may be asking, what is the purpose of the `Program` dispatcher if 
    running coroutines blocks the primary
    draw thread. The answer is, blocking coroutines are useful when the work 
    performed is light. Light work includes 
    waiting for (off-thread) coroutines to complete and using the results to 
    write to graphics resources.
    
    In the below example we nest coroutines; the outer one is launched on the 
    `Program` dispatcher, the inner  
    one is launched on the `GlobalScope` dispatcher. The `GlobalScope` 
    dispatcher executes the coroutine on a thread
    (from a thread pool) such that it does not block the primary draw thread. 
    By using `.join()` on the inner coroutine
    we wait for it to complete, waiting is non-blocking (thanks to coroutine 
    magic!). Once the join operation completes d
    we can write the results to a graphics resource on the primary draw threaddlkfskdfj ldskjf;slkdjf 니아ㅓㄹㅁ;니ㅏ얼;lkdjsf;lakdsjf;lk;ㅣ아ㅓㄹ;밍나ㅓㄹ;;laskdjf;laskdjf
     """vi     

    @Code
    application {
        program {
            val colorBuffer = colorBuffer(512, 512)
            val data = ByteBuffer.allocateDirect(512 * 512 * 4)
            var once = true
            extend {
                if (once) {
                    once = false
                    launch {
                        // -- launch on GlobalScope
                        // -- this will cause the coroutine to be executed off-thread.
                        GlobalScope.launch {
                            // -- perform some faux-heavy calculations
                            val r = Random(100)
                            for (y in 0 until 512) {
                                for (x in 0 until 512) {
                                    for (c in 0 until 4) {
                                        data.put(r.nextBytes(1)[0])
                                    }
                                }
                            }
                        }.join() // -- wait for coroutine to complete

                        // -- write data to graphics resources
                        data.rewind()
                        colorBuffer.write(data)
                    }
                }
            }
        }
    }

    @Text 
    """
    ## Secondary draw threads
    
    In some scenarios you may want to have a separate thread on which all 
    graphic resources can be used and
    drawing is allowed. In those cases you use `drawThread`.
    
    Most graphic resources can be used and shared between threads, with the 
    exception of the `RenderTarget`.
    
    In the next example we create a secondary draw thread and a `ColorBuffer` 
    that is shared between the threads.
    On the secondary draw thread we create a `RenderTarget` with the color 
    buffer attachment. The image is made visible on the primary draw thread.
    """

    @Code
    application {
        program {
            val result = colorBuffer(512, 512)
            var once = true
            var done = false
            val secondary = drawThread()

            extend {
                if (once) {
                    once = false
                    // -- launch on the secondary draw thread (SDT)
                    secondary.launch {
                        // -- create a render target on the SDT.
                        val rt = renderTarget(512,512) {
                            colorBuffer(result)
                        }

                        // -- make sure we use the draw thread's drawer
                        val drawer= secondary.drawer
                        drawer.withTarget(rt) {
                            drawer.ortho(rt)
                            drawer.clear(ColorRGBa.PINK)
                        }

                        // -- destroy the render target
                        rt.destroy()
                        finish()
                        // -- tell main thread the work is done
                        done = true
                    }
                }
                // -- draw the result when the work is done
                if (done) {
                    drawer.image(result)
                }
            }
        }
    }
}