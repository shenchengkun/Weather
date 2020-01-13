package com.twitter.challenge;

public interface ViewPresenterContract {

  interface BaseView {

    void showProgressBar();

    void hideProgressBar();

    void showCurrentWeather(Double temp, String windSpeed, boolean isCloudy);

    void showNextFiveDaysDeviation(String deviation);

    void showError(String error);
  }

  interface WeatherPresenter {

    void loadCurrentWeather();

    void loadNextFiveDaysWeather();

    void detachView();
  }
}
