// 타입 별칭 (type alias)
fun main(args: Array<String>) {
    f20( listOf(Person5("A"), Person5("B")))

    val p6 = Person6("ncia", 100)
    //분해
    val (n1, a1) = p6
    println(n1)
    println(a1)

    val (_, age) = p6
    println(age)

    val bs: Map<String, String> = mapOf("A" to "a", "B" to "b")
    for ( (k, v) in bs ) {
        println("$k and $v")
    }
}
// 복잡한 구조의 타입을 별칭으로 구현하여 간결하게 표현
class Person5(val name:String)
typealias PL = List<Person5>
typealias PW = Map<String, Person5>

fun f20(pl: PL) {
    // 이름을 다 찍어라
    pl.forEach { println(it.name) }
}

// 분해
// 복잡한 컬렉션에서 일부만 취할 때
data class Person6(val name: String, val age: Int)