// 제네릭
// 제네릭 클래스들은 반드시 타입을 기재해야 한다.
fun main(args: Array<String>) {
    // 제네릭 클래스들은 반드시 타입을 기재해야 한다.
    var a: List<String>

    var c: Bentz = Bentz()
    c.test()
    c.info(Car())
}

class Car
interface CarImp<T> {
    fun test(): T
    fun info(item: T)

}
//제네릭 적용 : 타입 한정
interface CarImp2<T: Car> {
    fun test(): T
    fun info(item: T)
}
//구현
class Bentz: CarImp<Car> {
    override fun test(): Car {
        TODO()
    }

    override fun info(item: Car) {

    }
}

//제네릭 사용 함수
// 타입이 정해져 있는 함수 인자
fun t1(items: List<String>) {

}
//타입이 정해져있지 않는 경우
fun <T> t2(items: List<T>) {

}