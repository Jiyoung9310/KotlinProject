// 예외
fun main(args: Array<String>) {

    var b: Boolean = try {
        println("1")
        1/0
        println("2")
        true
    } catch (e:Exception) {
        println("3")
        false
    } finally {
        println("4")
        true
    }
    println(b)
}