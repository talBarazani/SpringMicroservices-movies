package com.tal.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tal.moviecatalogservice.models.CatalogItem;
import com.tal.moviecatalogservice.models.Movie;
import com.tal.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie  = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId() , Movie.class);
		return new  CatalogItem(movie.getName(), "Test", rating.getRating());
	}
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new  CatalogItem("Movie name not found", "", rating.getRating());
	}
	
}
