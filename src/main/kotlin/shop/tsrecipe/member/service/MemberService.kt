package shop.tsrecipe.member.service

import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import shop.tsrecipe.member.domain.Member
import shop.tsrecipe.member.domain.OAuthInfo
import shop.tsrecipe.member.exception.BaseException
import shop.tsrecipe.member.exception.ErrorCode
import shop.tsrecipe.member.util.Logging
import shop.tsrecipe.member.util.NicknameManager
import kotlin.random.Random

@Service
class MemberService(
    private val memberManager: MemberManager,
    private val memberReader: MemberReader,
    private val nicknameManager: NicknameManager
) : Logging {
    suspend fun signUp(command: SignUpCommand): Member {
        checkDuplicatedInfos(oauthInfo = command.oauthInfo, nickname = command.nickname)

        return memberManager.create(command) ?: run {
            logger.warn { "DB save failed.\n" }
            throw BaseException(ErrorCode.DATABASE_ERROR)
        }
    }

    private suspend fun checkDuplicatedInfos(oauthInfo: OAuthInfo, nickname: String) {
        if (isDuplicatedOAuthInfo(oauthInfo)) throw BaseException(ErrorCode.OAUTH_INFO_DUPLICATED)
        if (isDuplicatedNickname(nickname)) throw BaseException(ErrorCode.NICKNAME_DUPLICATED)
    }

    private suspend fun isDuplicatedOAuthInfo(oauthInfo: OAuthInfo): Boolean {
        return memberReader.findOneByOAuthInfo(oauthInfo) != null
    }

    suspend fun getMember(query: GetMemberQuery): Member {
        return if (query.memberId != null) {
            memberReader.findOneById(query.memberId) ?: throw BaseException(ErrorCode.MEMBER_NOT_FOUND)
        } else {
            memberReader.findOneByOAuthInfo(query.oauthInfo!!) ?: throw BaseException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }

    suspend fun getNicknameByRandom(): String {
        var nickname = nicknameManager.getRandom()
        repeat(10) {
            if (!isDuplicatedNickname(nickname)) {
                return nickname
            }
            nickname = nicknameManager.getRandom()
        }

        return "${nickname}+${Random.nextInt(1000)}"
    }

    suspend fun isDuplicatedNickname(nickname: String): Boolean {
        return memberReader.findOneByNickname(nickname) != null
    }

    suspend fun update(command: MemberUpdateCommand): Member {
        return memberManager.update(command) ?: throw BaseException(ErrorCode.MEMBER_NOT_FOUND)
    }

    suspend fun withdrawal(id: ObjectId) {
        memberManager.delete(id)
    }
}