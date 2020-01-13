package com.twitter.challenge.repo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HttpService {

  @GET("current.json")
  Observable<WeatherResponse> getCurrentWeather();

  @GET("future_{n}.json")
  Observable<WeatherResponse> getNextNthWeather(@Path("n") Integer integer);
}