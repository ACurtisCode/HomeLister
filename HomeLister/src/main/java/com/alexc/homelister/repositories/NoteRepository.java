package com.alexc.homelister.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alexc.homelister.models.Listing;
import com.alexc.homelister.models.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
	List<Note> findAll();
	
	

}
