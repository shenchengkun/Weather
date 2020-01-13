package com.twitter.challenge.repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepository {
  private static final MainRepository ourInstance = new MainRepository();
  private static final String BASE_URL = "https://twitter-code-challenge.s3.amazonaws.com/";

  private HttpService httpService;

  public static MainRepository getInstance() {
    return ourInstance;
  }

  private MainRepository() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    httpService = retrofit.create(HttpService.class);
  }

  public Observable<WeatherResponse> getCurrentWeather() {
    return httpService.getCurrentWeather();
  }

  public Observable<List<WeatherResponse>> getNextFiveDaysWeather() {
    final List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      list.add(i);
    }
    return Observable.fromIterable(list).flatMap(new Function<Integer, ObservableSource<WeatherResponse>>() {
      @Override
      public ObservableSource<WeatherResponse> apply(Integer integer) throws Exception {
        return httpService.getNextNthWeather(integer).subscribeOn(Schedulers.io());
      }
    }).toList().toObservable();
  }
}
