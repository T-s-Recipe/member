package shop.tsrecipe.member.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class NicknameManager(
    @Value("classpath:/adjectives.txt")
    private val adjectiveResource: Resource,
    @Value("classpath:/ingredients.txt")
    private val ingredientResource: Resource
) {
    val adjectives: List<String> by lazy { load(adjectiveResource) }
    val ingredients: List<String> by lazy { load(ingredientResource) }

    private fun load(resource: Resource): List<String> =
        resource.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
            lines.map { it.trim() }.filter { it.isNotEmpty() }.toList()
        }

    suspend fun getRandom(): String {
        return "${adjectives.random()} ${ingredients.random()}"
    }
}