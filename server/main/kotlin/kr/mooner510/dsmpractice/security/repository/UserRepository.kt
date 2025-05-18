package kr.mooner510.dsmpractice.security.repository

import kr.mooner510.dsmpractice.security.data.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByLoginId(loginId: String): Optional<User>

    fun existsByLoginIdIgnoreCase(loginId: String): Boolean

    @Transactional
    @Modifying
    @Query("update User u set u.thumbnail = ?1 where u.id = ?2")
    fun updateThumbnailById(thumbnail: UUID, id: UUID)
}
