/* 기본 자료형
* 자바 : 원시타입(byte, short, int, long, float, double, boolean, char) + 참조타입(객체)
* 코틀린 : 타입은 오직 하나(참조타입, 객체타입)만 존재
* - 자바 : byte -> 래퍼클래스 java.lang.Byte
*   => 코틀린 : kotlin.Byte
* - *.kt -> *.class -> 구동 : 변환 과정에서 알아서 자바의 byte로 변환된다.
*
* */


fun main(args: Array<String>) {
    /////////////////////////////////////////////////////////////////
    //1. 타입
    // 변수 키워드 var(변수:읽고쓰기), val(한번할당 후 수정불가:읽기)
    // {키워드 변수명}:{타입}
    // 세미콜론; 이 없다
    // 타입은 생략 가능, 타입 추론이 가능한다면
    var a:Int = 10
    //int a = 10;
    println(a)

    var b:List<Int>
    //List<Integer> b;



    ////////////////////////////////////////////////////////////////
    //2. 숫자대입 체크(진법, 수치 뒤에 사용하는 부호)
    //c라는 변수에 바이트 타입으로 10을 대입하시오
    //10진수
    var c: Byte = 10

    //진법
    var dec_a: Int = 100  //10진수
    var hex_a: Int = 0xFF  //16진수
    var bin_a: Int = 0b100  //2진수

    println( "%d %d %d".format(dec_a, hex_a, bin_a))

    //부호 사용
    val long_a: Long = 100L
    val dou_a: Double = 1.0
    val flo_a: Float = 1.0f

    println( "%d %f %f".format(long_a, dou_a, flo_a))


    /////////////////////////////////////////////////////////////////////
    // 3. 수치연산
    // 논리연산
    // & -> and (AND)
    // | -> or (OR)
    // ^ -> xor (XOR)
    // ~ -> inv (NOT)
    // 쉬프트 연산
    // << -> shl (부호유지)
    // >> -> shr (부호유지)
    // >>> -> ushr (부호무시)

    println( 100 shr 1)

    /////////////////////////////////////////////////////////////////////
    // 4. 문자
    // 자바는 아스키코드로 문자를 표현할 수 있음(숫자 대입 가능) -> 코틀린은 불가
    val str: Char = 'A'
    //val str1: Char = 65 //오류발생
    val str1: Char = 65.toChar() //객체이기 떄문에 .이 가능하다
    println(str)
    println(str1)

    /////////////////////////////////////////////////////////////////////
    // 5. 논리
    // boolean -> Boolean
    // &&, ||, | -> 동일
    // true, false -> 동일
    var b_flag: Boolean = false
    println( b_flag )

    /////////////////////////////////////////////////////////////////////
    // 6. 문자열
    val s_name: String = "가나다라1234abcdeHELLO!@$^"
    println(s_name)
    // 문자열에서 문자 획득 -> get() 혹은 [인덱스]
    println(s_name.get(0))
    println(s_name[1])
    // 전통적 포멧팅
    val cost: Int = 1000
    val f_text: String = String.format("과자의 가격은 : %d", cost)
    println(f_text)
    // 문자열 템플릿 $변수, ${식->결과->값}, ${변수}
    println("과자의 가격은 : $cost 입니다")
    println("과자의 가격은 : ${cost}입니다")
    println("과자의 가격은 : ${cost.toString().length}${'$'}입니다")



    /////////////////////////////////////////////////////////////////////
    // 7. 배열
    // String[] arr = new String[]{"A", "B"};
    // 약식 표현으로 배열을 바로 생성 가능
    val arr: Array<String> = arrayOf("A", "B")
    println(arr[0])
    // 원시 타입형태의 배열
    // 원시타입은 보편적 표현식으로 생성하는 방법 1
    // 원시타입에 대응하는 직접 표현식을 이용하여 생성하는 방법 2
    val intArr: Array<Int> = arrayOf(1, 2, 3)
    val intArr1: IntArray = intArrayOf(1, 2, 3)
    println(intArr[0])
    println(intArr1[0])

    a1(intArr)
    a2(*intArr1)
    // a2(*intArr) //오류
}

fun a1(arr: Array<Int>) {
    println(arr[0])
}

//함수의 인자(가변인자)일떄 스프레드 연산자를 사용해야 할 경우
fun a2(vararg args:Int) {
    println(args[0])
}