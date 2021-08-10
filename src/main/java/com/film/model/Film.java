package com.film.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "FILM")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "ID")
	private int id;

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "film")
	private List<Comment> comments;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "film")
	private List<Genere> genere;
	
	@Column (name = "NAME")
	private String name;

	@Column (name = "FILM_DESC")
	private String description;
	
	@Column(name = "RELEASE_DATE", nullable = false, updatable = false)
	private LocalDateTime releaseDate;
	
	@Column (name = "RATING")
	private int rating;
	
	@Column (name = "TICKET_PRICE")
	private int ticketPrice;
	
	@Column (name = "PHOTO")
	private byte[] photo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public List<Genere> getGenere() {
		return genere;
	}

	public void setGenere(List<Genere> genere) {
		this.genere = genere;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}