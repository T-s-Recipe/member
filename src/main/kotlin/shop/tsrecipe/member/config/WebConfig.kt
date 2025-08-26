package shop.tsrecipe.member.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import shop.tsrecipe.member.annotation.MemberIdArgumentResolver

@Configuration
class WebConfig(
    private val memberIdArgumentResolver: MemberIdArgumentResolver
): WebFluxConfigurer {
    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(memberIdArgumentResolver)
    }
}