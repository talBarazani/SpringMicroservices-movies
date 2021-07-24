package com.tal.ratingdataservice.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tal.ratingdataservice.models.Rating;
import com.tal.ratingdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("/users{userId}")
	public UserRating getUserRating(@PathVariable ("userId") String userId){
		List<Rating> ratings = Arrays.asList(
				new Rating("100", 3),
				new Rating("200", 4)
		);
		UserRating userRating = new UserRating();
		userRating.setRatings(ratings);
		return userRating;
	}

}
