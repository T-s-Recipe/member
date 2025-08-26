package shop.tsrecipe.member.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import shop.tsrecipe.member.domain.Member
import shop.tsrecipe.member.domain.OAuthProvider

interface MemberRepository: ReactiveMongoRepository<Member, ObjectId> {
    fun findByOauthInfoProviderAndOauthInfoId(provider: OAuthProvider, id: String): Mono<Member>
    fun findByNickname(nickname: String): Mono<Member>
}