package uz.jamshid.exchangerate

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test(){
        println("before ${System.currentTimeMillis()}")
        getList().subscribeOn(Schedulers.computation()).subscribe {
            println(it + Thread.currentThread().name)
        }
        Thread.sleep(3000)
        println("end ${System.currentTimeMillis()}")
    }

    fun getList(): Observable<String>{

        return Observable.create {
            Thread.sleep(2000)
            println("asd")
            it.onNext("Hello")
            it.onComplete()
        }
    }
}
