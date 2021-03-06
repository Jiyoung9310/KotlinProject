// 클래스, 인터페이스, 기타 관련 사항

fun main(args: Array<String>) {
    //A클래스를 받을 변수
    //객체 생성 시 new 사용 안함
    val a: A = A()
    val b: B = B()

    //추상클래스 객체 생성법
    val a1: A1 = object : A1() {
        //재정의, 구현
        override fun go() {
            println("go() call")
        }
    }
    a1.go()
    //인터페이스 생성
    val c: C = object : C {

    }

    // 프로퍼티
    val p1: Person = Person()
    // home이라는 프로퍼티를 출력
    // p1.home => getter
    println(p1.home)
    // setter
    p1.addr = "hi"
    println(p1.addr)
    println(p1.name)
    // 초기화하지 않았으므로 오류
    //println(p1.tel)

    //접근 제어자
    val u: User = User()
    //public
    println(u.a)
    //internal
    println(u.d)

    // 생성자
    F1()
    // 주생성자
    val f2: F2 = F2(10, "ncia")
    //주생성자의 멤버들은 외부에서 접근이 불가
    //f2. 사용 불가
    //주생성자에서 var, val을 세팅하면 외부 접근이 가능
    val f3: F3 = F3(10, "ncia")
    println(f3.a)
    println(f3.b)
    // 주생성자, 세컨 생성자 사용
    val f4: F4 = F4(10)
    println("${f4.a} / ${f4.b}")

    //클래스 함수
    F5()

    //인터페이스 구현
    F6().ia()

    //클래스 상속
    println(F7().PI)
    F7().f1()
    F7().f2()

    //동반 객체 -> 정적 표현
    val user: U = U.a("Hi")
    println(user.name)

    //싱글톤
    println(ST.name)
    ST.ss()

    //열거형
    println(Dir.A)

    //중첩클래스
    val s1 = OutClass.StaticIn() //바로 접근이 가능하다
    val s2 = OutClass().NonStaticIn() //OutClass의 객체를 생성한 후에 접근해야 한다.

}
////////////////////////////////////////////////////////////////
//클래스 기본
//접근 제어자(java : private, default, protected, public)
// kotlin : private, internal, protected, public
//접근 제어자를 사용하지 않는 클래스는 무조건 public으로 간주
class A {

}
//코틀린의 class는 {}을 안써도 된다.
class B

//인터페이스 형태
interface C {

}
interface D

////////////////////////////////////////////////////////////////
//추상 클래스
//멤버 중에 구현이 안된 멤버가 있는 경우, 상속 받아서 구현해라(자바)
abstract class A1{
    abstract fun go()
}

////////////////////////////////////////////////////////////////
// 프로퍼티 (멤버 변수)
// 자바 -> 멤버 정의, setter/getter 준비
// 코틀린은 간결하게 처리
class Person {
    // 코틀린의 프로퍼티 초기값을 반드시 명시적으로 지정해야 함
    // 단, 생성자에서 초기화하면 선언 시 안해줘도 된다.
    // 이럴 경우, lateinit 키워드를 붙여야 한다.

    // ?는 null값이 올 수 있다
    var name: String? = null
    // 읽고 쓰기가 가능 -> getter/setter 제공
    var addr: String? = null
    // 나중에 초기화
    lateinit var tel:String
    // 초기값이 타입 추론이 가능하면 타입은 안쓸 수 있다
    val home = "no home"

}

////////////////////////////////////////////////////////////////
//클래스 접근 제어자 프로퍼티 적용
class User {
    // 접근 제어자 : public
    val a: Int = 1
    // 상속관계
    protected val b: Int = 2
    // 혼자 사용
    private val c: Int = 3
    //internal : 동일 모듈에서 사용, 같은 프로젝트 안에서 다 ok
    internal val d: Int = 4

}

////////////////////////////////////////////////////////////////
// 생성자
// 인자가 없는 기본 생성자
class F1{
    init {
        println("인자가 없는 기본 생성자 call")
    }
}
//주 생성자, primary constructor
// 클래스명 뒤에 ()형태로 위치된다
class F2(a: Int, b: String) {
    // 주생성자의 파라미터는 변수로 사용 가능
    init {
        println("인자 값은 a=$a, b=$b 입니다")
    }
}
// 주생성자의 파라미터를 통해서 클래스의 프로퍼티로 할당
// var이나 val을 사용
class F3(var a: Int, val b: String)
// 주생성자, 세컨 생성자 사용
class F4(val a:Int, val b: String) {
    // 자바의 생성자 오버로딩의 느낌
    constructor(a: Int) : this(a, "기본값")
    private constructor():this(0, "기본값")
}
////////////////////////////////////////////////////////////////
//클래스 함수
class F5{
    init {
        f1()
        f2()
        println(sum(1,2))
    }
    //Unit 리턴타입이 없다 => void
    // fun {함수명}(): {리턴타입}
    fun f1(): Unit {
        println("리턴 값이 없는 함수 Unit")
    }
    fun f2() {
        println("리턴 값이 없는 함수 call")
    }
    //a, b 두개 정수값으로 인자를 받아서 합한 값을 리턴하는 함수를 만드시오
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}
////////////////////////////////////////////////////////////////
//인터페이스 구현하기
interface I {
    fun ia()
}
class F6: I {
    override fun ia() {
        println("ia call")
    }
}
////////////////////////////////////////////////////////////////
//클래스 상속처리
//상속을 주려면(부모입장에서) open 키워드로 정의되어있어야한다
//부모
open class O1 {
    //member도 open이 붙어있어야만 재정의가 된다
    open val PI = 3.14
    val a = "hello"
    open fun f1() {
        println("f1() call")
    }
    fun f2() {
        println("f2() call")
    }
}
//자식 : 부모생성자() -> 상속
class F7: O1() {
    override val PI = 3.145
    override fun f1() {
        super.f1()
        println("f1() override call")
    }
}
////////////////////////////////////////////////////////////////
// this
// 클래스 내에 클래스에서 상위 클래스 접근하는 방식
// 자바 : 상위클래스.this
// 코틀린 : this@상위클래스

////////////////////////////////////////////////////////////////
// 정적 필드, 메소드 -> 동반객체
// companion object
// 코틀린의 클래스는 내부에 정적 필드, 함수 사용 불가
// 클래스는 한개 씩 동반 객체를 가질 수가 있다
// 이를 활용하여 같은 효과를 낼 수 있다
class U private constructor(val name: String) {
    companion object {
        fun a(name: String): U {
            return U(name)
        }
    }
}

////////////////////////////////////////////////////////////////
// 싱글톤 : object
object ton

object ST{
    val name = "ncia"
    fun ss() {
        println("싱글톤 함수")
    }
}

////////////////////////////////////////////////////////////////
// enum
enum class Dir{
    A, B, C, D
}

////////////////////////////////////////////////////////////////
// 어노테이션

////////////////////////////////////////////////////////////////
// 중첩클래스
// 1. nested class
// 2. inner class
class OutClass {
    // nested class
    class StaticIn {

    }
    //inner class
    inner class NonStaticIn {

    }
}
