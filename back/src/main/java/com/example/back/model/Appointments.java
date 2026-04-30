package com.example.back.model;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
public class Appointments {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

<<<<<<< Updated upstream
	    @Column(nullable = false, unique = true)
=======
	    @Column(nullable = false, unique = true, length = 36)
>>>>>>> Stashed changes
	    private String uuid;

	    private String name;
	    private String phone;
	    private String email;
<<<<<<< Updated upstream
=======
	    
	    @Column(name = "country_id")
>>>>>>> Stashed changes
	    private Integer countryId;

	    @Column(columnDefinition = "TEXT")
	    private String requirement;
<<<<<<< Updated upstream

=======
	    
	    @Column(name = "created_at", insertable = false, updatable = false)
>>>>>>> Stashed changes
	    private Timestamp createdAt;

	}

