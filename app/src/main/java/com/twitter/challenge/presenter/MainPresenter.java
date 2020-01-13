package com.twitter.challenge.presenter;

import android.util.Log;

import com.twitter.challenge.StandardDeviationCalculator;
import com.twitter.challenge.ViewPresenterContract;
import com.twitter.challenge.repo.MainRepository;
import com.twitter.challenge.repo.WeatherResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements ViewPresenterContract.WeatherPresenter {
  private ViewPresenterContract.BaseView baseView;

  public MainPresenter(ViewPresenterContract.BaseView baseView) {
    this.baseView = baseView;
  }

  @Override
  public void loadCurrentWeather() {
    baseView.showProgressBar();
    MainRepository.getInstance()
      .getCurrentWeather()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Observer<WeatherResponse>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(WeatherResponse weatherResponse) {
          baseView.showCurrentWeather(BigDecimal.valueOf(weatherResponse.getWeather().getTemp()).setScale(2, RoundingMode.HALF_UP).doubleValue(),
            String.valueOf(weatherResponse.getWind().getSpeed()),
            weatherResponse.getClouds().getCloudiness() > 50);
          baseView.hideProgressBar();
        }

        @Override
        public void onError(Throwable e) {
          Log.i(getClass().getName(), e.toString());
          baseView.showError(e.toString());
          baseView.hideProgressBar();
        }

        @Override
        public void onComplete() {

        }
      });

  }

  @Override
  public void loadNextFiveDaysWeather() {
    baseView.showProgressBar();
    MainRepository.getInstance().getNextFiveDaysWeather()
      .flatMap(new Function<List<WeatherResponse>, ObservableSource<String>>() {
        @Override
        public ObservableSource<String> apply(List<WeatherResponse> weatherResponses) throws Exception {
          return Observable.just(calDeviation(weatherResponses));
        }
      })
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
          baseView.showNextFiveDaysDeviation(s);
          baseView.hideProgressBar();
        }

        @Override
        public void onError(Throwable e) {
          Log.i(getClass().getName(), e.toString());
          baseView.showError(e.toString());
          baseView.hideProgressBar();
        }

        @Override
        public void onComplete() {

        }
      });
  }

  private String calDeviation(List<WeatherResponse> list) {
    int size = list.size();
    if (size == 0) {
      return "";
    }

    List<Double> list1 = new ArrayList<>();
    for (WeatherResponse weatherResponse : list) {
      list1.add(weatherResponse.getWeather().getTemp());
    }

    return String.valueOf(StandardDeviationCalculator.calculatorStandardDeviation(list1));
  }

  @Override
  public void detachView() {
    baseView = null;
  }
}
