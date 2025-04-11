package com.logonedigital.student_management.student;

import com.logonedigital.student_management.common.BaseEntity;
import com.logonedigital.student_management.student.grade.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
@Entity
public class Student extends BaseEntity {

    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    //Generate when we first create and save the Student
    @Column(nullable = false, unique = true)
    private String matricule;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grade> grades;

    public String fullName(){
        return firstname + " " + lastname;
    }

}
