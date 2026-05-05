package com.example.back.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.back.dto.ConsultantWorkloadDto;
import com.example.back.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	Optional<Employee> findByEmployeeCode(String employeeCode);
	
	@Query("""
		    SELECT new com.example.back.dto.ConsultantWorkloadDto(
		        e.id,
		        e.name,
		        COUNT(c.id)
		    )
		    FROM Employee e
		    LEFT JOIN Consultations c ON c.employeesId = e AND c.status NOT IN ('已成交', '暫不考慮 (結案)', '暫緩處理')
		    WHERE e.role != 'manager' AND e.role != 'Manager'
		    GROUP BY e.id, e.name
		    ORDER BY COUNT(c.id) ASC
		""")
		List<ConsultantWorkloadDto> findConsultantsWithWorkload();
}
