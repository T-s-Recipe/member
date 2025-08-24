package shop.tsrecipe.member.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import shop.tsrecipe.member.domain.Member
import shop.tsrecipe.member.repository.MemberRepository
import shop.tsrecipe.member.util.buildQueryById

@Repository
class MemberManager(
    private val memberRepository: MemberRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    suspend fun create(command: SignUpCommand): Member? {
        return memberRepository.save(
            Member(
                oauthInfo = command.oauthInfo,
                nickname = command.nickname
            )
        ).awaitSingleOrNull()
    }

    suspend fun delete(id: ObjectId) {
        memberRepository.deleteById(id).awaitSingle()
    }

    suspend fun update(command: MemberUpdateCommand): Member? {
        return reactiveMongoTemplate.findAndModify(
            buildQueryById(command.id),
            Update().set("nickname", command.nickname),
            Member::class.java
        ).awaitSingleOrNull()
    }
}