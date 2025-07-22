package shop.tsrecipe.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemberApplication

suspend fun main(args: Array<String>) {
	runApplication<MemberApplication>(*args)
}
