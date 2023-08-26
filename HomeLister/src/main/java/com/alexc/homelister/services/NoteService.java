package com.alexc.homelister.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexc.homelister.models.Listing;
import com.alexc.homelister.models.Note;
import com.alexc.homelister.repositories.NoteRepository;

@Service
public class NoteService {
	@Autowired
	NoteRepository noteRepo;
	
	public Note newNote(Note note) {
		return noteRepo.save(note);
	}

}
