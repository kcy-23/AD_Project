package com.example.adproject.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.adproject.helper.StatusEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Goal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String goalDescription;
	private int totalMealCount;
	private int targetCount;
	@Column(columnDefinition = "ENUM('STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Goal(String goalDescription, int totalMealCount, int targetCount,
				StatusEnum status, LocalDate startDate, LocalDate endDate) {
		this.goalDescription = goalDescription;
		this.totalMealCount = totalMealCount;
		this.targetCount = targetCount;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}
