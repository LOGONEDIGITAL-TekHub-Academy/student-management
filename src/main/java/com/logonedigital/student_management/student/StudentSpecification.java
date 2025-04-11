package com.logonedigital.student_management.student;

import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> withIsDeleted(boolean isDeleted) {
        return (studentRoot,
                query,
                criteriaBuilder) -> criteriaBuilder.equal(studentRoot.get("isDeleted"), isDeleted);
    }
}
