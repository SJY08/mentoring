package kr.mooner510.dsmpractice.security.data.entity.user

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import kr.mooner510.dsmpractice.global.data.entity.BaseEntity
import kr.mooner510.dsmpractice.security.data.dto.UserDto
import kr.mooner510.dsmpractice.security.data.types.UserType
import kr.mooner510.dsmpractice.utils.UUIDParser.toURL
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@SQLRestriction(value = "is_deleted = false")
@SQLDelete(sql = "UPDATE user SET is_deleted = true where id = ?")
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
abstract class User(
    @Id
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 31)
    var loginId: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userType: UserType,

    @Column(nullable = false)
    private var password: String,

    @Column(nullable = true)
    var thumbnail: UUID? = null,
) : UserDetails, BaseEntity() {

    @Column(name = "is_deleted", nullable = false)
    val isDeleted = false

    companion object {
        private val ADMIN_AUTH = listOf(SimpleGrantedAuthority("ADMIN"))
        private val STUDENT = listOf(SimpleGrantedAuthority("STUDENT"))
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return if (userType == UserType.Admin) ADMIN_AUTH else STUDENT
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return loginId
    }

    override fun isAccountNonExpired(): Boolean {
        return !isDeleted
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return !isDeleted
    }

    fun toDto(): UserDto {
        return UserDto(this.id, this.loginId, this.thumbnail?.toURL())
    }
}
