package com.tal.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tal.moviecatalogservice.models.Rating;
import com.tal.moviecatalogservice.models.UserRating;

@Service
public class UserRatingInfo {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "50"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
			}
			)
	public UserRating getUserRating(String userId) {
		UserRating a =  restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/users" + userId, UserRating.class);
		return a;
	}
	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating= new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(
					new Rating("0" , 0) 
				));
		return userRating;	
	}
}
