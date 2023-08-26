package com.alexc.homelister.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexc.homelister.models.Listing;
import com.alexc.homelister.repositories.ListingRepo;

@Service
public class ListingService {
	@Autowired
	ListingRepo listRepo;
	public Listing createListing(Listing listing) {
		Date date = new Date();
		listing.setListingDate(date);
		return listRepo.save(listing);
	}
	public List<Listing> allListings() {
		return listRepo.findAll();
	}
	public Listing findListing(Long id) {
		Optional<Listing> optList = listRepo.findById(id);
		if(optList.isPresent()) {
			return optList.get();
		} else {
			return null;
		}
	}
	public Listing updateListing(Listing listing) {
		return listRepo.save(listing);
	}
	public void deleteListing(Listing listing) {
		listRepo.delete(listing);
	}
}
