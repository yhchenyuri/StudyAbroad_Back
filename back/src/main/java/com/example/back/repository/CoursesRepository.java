package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.back.model.Courses;
@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

}
