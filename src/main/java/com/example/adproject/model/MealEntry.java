package com.example.adproject.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.adproject.helper.FeelingEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class MealEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String imageURL;
	private boolean visibility;
	private String title;
	private String description;
	private boolean flagged;
	@Column(columnDefinition = "ENUM('CRYING', 'PENSIVE', 'HAPPY', 'JOYFUL')")
	@Enumerated(EnumType.STRING)
	private FeelingEnum feeling;
	private int trackScore;
	private LocalDateTime timeStamp;
	
	@ManyToOne
	private Goal goal;
	
	@ManyToMany
	private List<User> likers;
	
	@ManyToOne
	private User author;
	
	@OneToMany
	private List<Comment> comments;
	
	
	public MealEntry(String imageURL, boolean visibility, String title, String description, 
					 boolean flagged, FeelingEnum feeling, int trackScore, LocalDateTime timeStamp, 
					 Goal goal, List<User> likers, User author, List<Comment> comments) {
		this.imageURL = imageURL;
		this.visibility = visibility;
		this.title = title;
		this.description = description;
		this.flagged = flagged;
		this.feeling = feeling;
		this.trackScore = trackScore;
		this.timeStamp = timeStamp;
		this.goal = goal;
		this.likers = likers;
		this.author = author;
		this.comments = comments;
	}
	
}
