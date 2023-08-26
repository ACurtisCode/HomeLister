package com.alexc.homelister.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alexc.homelister.models.Listing;

@Repository
public interface ListingRepo extends CrudRepository<Listing, Long>{
	List<Listing> findAll();
	Optional<Listing> findListingById(Long id);
 }
