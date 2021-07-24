package com.tal.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tal.moviecatalogservice.models.CatalogItem;
import com.tal.moviecatalogservice.models.Movie;
import com.tal.moviecatalogservice.models.Rating;
import com.tal.moviecatalogservice.models.UserRating;
import com.tal.moviecatalogservice.services.MovieInfo;
import com.tal.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
			UserRating userRating = userRatingInfo.getUserRating(userId);
			return userRating.getRatings().stream()
					.map(rating -> movieInfo.getCatalogItem(rating))
					.collect(Collectors.toList());
	}
	

}

/*
Movie movie = webClientBuilder.build()
					.get()
					.uri("http://localhost:8081/movies/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
*/