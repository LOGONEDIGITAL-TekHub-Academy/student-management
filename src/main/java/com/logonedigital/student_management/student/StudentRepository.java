package com.logonedigital.student_management.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student> {

    @Query("""
            SELECT student
            FROM Student student
            WHERE student.isDeleted = false
            """)
    Page<Student> findExistingStudents(Pageable pageable);
}
