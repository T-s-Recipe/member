package shop.tsrecipe.member.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@CompoundIndex(
    name = "members_oauth_info_provider_id_uidx",
    def = "{'oauthInfo.provider': 1, 'oauthInfo.id': 1}",
    unique = true
)
@Document(collection = "members")
class Member(
    @Id
    val id: ObjectId = ObjectId.get(),
    val oauthInfo: OAuthInfo,
    val nickname: String,
    val isVerified: Boolean = false
): Auditable()

data class OAuthInfo(
    val provider: OAuthProvider,
    val id: String
)

enum class OAuthProvider {
    GOOGLE,
    APPLE
}