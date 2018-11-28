package io.valhala.security;

import org.springframework.data.jpa.repository.JpaRepository;

import io.valhala.security.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
