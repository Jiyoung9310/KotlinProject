//코틀린만의 특성
//클래스
fun main(args:Array<String>) {
    val c1 = Car2("하이브리드", 2000)
    val c2 = Car2("가솔린", 2001)
    val c3 = Car2("하이브리드", 2000)

    println( c1 === c2 )
    println( c1 === c3 )
    println( c1 == c2 )
    println( c1 == c3 )
    println( c1.hashCode() )
    println( c1.toString() )

    val c4 = CarEx("버스", 3500)
    println("${c4.name}은 대형차 입니까? ${c4.bigCar}")
    c4.color = "파랑색"
    println(c4.color)
}
//////////////////////////////////////////
// data class
// 자료를 담는 그릇
// 자바는 번잡함 => getter / setter 를 만들어야하기 때문
// 자료를 담는 과정은 단순하게 가자
// 프로퍼티만 정의하면 나머지는 알아서 만들어 준다
// equals(), hashCode(), toString() 같은 것들.....
data class Car2(val name:String, val cc: Int)

//////////////////////////////////////////////
// 프로퍼티 사용자 정의(재정의)
// getter(), setter()를 커스터마이즈
class CarEx(val name:String, val cc:Int) {
    val bigCar: Boolean
        get() = cc >= 2400
    var color: String = "white"
        set(value) {
            field = "나만의 색상 $value"
        }
}