package kr.mooner510.dsmpractice.security.data.entity.user

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.mooner510.dsmpractice.security.data.types.UserType
import java.util.*

@Entity
@DiscriminatorValue("Student")
class Student(
    id: UUID,
    loginId: String,
    password: String,
) : User(id, loginId, UserType.Student, password)
