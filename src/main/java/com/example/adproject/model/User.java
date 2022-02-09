package com.example.adproject.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String name;
	private String gender;
	
	@Past
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private LocalDate dateOfBirth;
	private double height;
	private double weight;
	private String profilePic;
	
	@OneToMany(mappedBy = "author", cascade = { CascadeType.ALL }, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Comment> comments;
	
	
	@OneToMany(mappedBy = "author", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MealEntry> entries;
	
	@ManyToMany(mappedBy = "likers")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MealEntry> likedEntries;
	
	@OneToMany(targetEntity=Goal.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Goal> goals;

    @OneToMany(mappedBy = "recipient")
    private List<FriendRequest> receivedRequests;


    @OneToMany(mappedBy="sender")
    private List<FriendRequest> sentRequests;
	
//	@OneToOne
//	private Session session;
	
	public User(Integer id, String username, String password, String name, String gender, LocalDate dateOfBirth, 
			double height, double weight, String profilePic) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.height = height;
		this.weight = weight;
		this.profilePic = profilePic;
	}
	
	// Constructor used in FriendRequestTest unit tests
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}


	
}