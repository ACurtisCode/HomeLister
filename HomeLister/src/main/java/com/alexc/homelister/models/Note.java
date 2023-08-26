package com.alexc.homelister.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="notes")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message="you must have a note")
	private String message;
	
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="note_id")
	private User noteCreator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="listing_id")
	private Listing listingNote;
	
	public Note() {}
	
	public Note(String message) {
		this.message = message;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getNoteCreator() {
		return noteCreator;
	}

	public void setNoteCreator(User noteCreator) {
		this.noteCreator = noteCreator;
	}

	public Listing getListingNote() {
		return listingNote;
	}

	public void setListingNote(Listing listingNote) {
		this.listingNote = listingNote;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
