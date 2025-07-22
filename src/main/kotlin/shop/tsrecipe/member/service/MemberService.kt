package shop.tsrecipe.member.service

import org.springframework.stereotype.Service
import shop.tsrecipe.member.domain.Member
import shop.tsrecipe.member.domain.OAuthInfo
import shop.tsrecipe.member.exception.BaseException
import shop.tsrecipe.member.exception.ErrorCode
import shop.tsrecipe.member.util.Logging

@Service
class MemberService(
    private val memberManager: MemberManager,
    private val memberReader: MemberReader
): Logging {
    suspend fun signUp(command: SignUpCommand): Member {
        if (isDuplicated(command.oAuthInfo)) throw BaseException(ErrorCode.MEMBER_DUPLICATED)

        return memberManager.create(command) ?: run {
            logger.warn { "DB save failed.\n" }
            throw BaseException(ErrorCode.DATABASE_ERROR)
        }
    }

    private suspend fun isDuplicated(oAuthInfo: OAuthInfo): Boolean {
        return memberReader.findOneByOAuthInfo(oAuthInfo) != null
    }

    suspend fun getMember(query: GetMemberQuery): Member {
        return if (query.memberId != null) {
            memberReader.findOneById(query.memberId) ?: throw BaseException(ErrorCode.MEMBER_NOT_FOUND)
        } else {
            if (query.oauthInfo == null) {
                throw BaseException(ErrorCode.BAD_REQUEST)
            }
            memberReader.findOneByOAuthInfo(query.oauthInfo) ?: throw BaseException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }
}