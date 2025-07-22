package shop.tsrecipe.member.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import shop.tsrecipe.member.domain.Member
import shop.tsrecipe.member.domain.OAuthInfo
import shop.tsrecipe.member.domain.OAuthProvider
import shop.tsrecipe.member.repository.MemberRepository

@Repository
class MemberReader(
    private val memberRepository: MemberRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    suspend fun findOneById(id: ObjectId): Member? {
        return memberRepository.findById(id).awaitSingleOrNull()
    }

    suspend fun findOneByOAuthInfo(oAuthInfo: OAuthInfo): Member? {
        return memberRepository.findByOAuthInfoProviderAndOAuthInfoId(oAuthInfo.provider, oAuthInfo.id).awaitSingleOrNull()
    }
}