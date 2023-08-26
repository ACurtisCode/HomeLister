package com.alexc.homelister.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="listings")
public class Listing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="listing must have an address")
	@Size(min = 3, max = 30, message="address must be at least 3 characters")
	private String address;
	
	@NotNull(message="you must provide a price")
	@Min(value=1, message="price must be greater than 1")
	private float price;
	
	@NotNull(message="you must provide a room count")
	@Min(value=1, message="room count must be greater than 1")
	private int rooms;
	
	@NotNull(message="you must provide a bath count")
	@Min(value=1, message="bath count must be greater than 1")
	private float baths;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date listingDate;
	
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @Column(updatable=false)
    @OneToMany(mappedBy="listingNote", fetch=FetchType.LAZY)
    List<Note> notes = new ArrayList<Note>();
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User listCreator;
    
    public Listing() {}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public User getListCreator() {
		return listCreator;
	}
	public void setListCreator(User listCreator) {
		this.listCreator = listCreator;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Date getListingDate() {
		return listingDate;
	}
	public void setListingDate(Date listingDate) {
		this.listingDate = listingDate;
	}
	
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public float getBaths() {
		return baths;
	}
	public void setBaths(float baths) {
		this.baths = baths;
	}
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
