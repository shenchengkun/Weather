package com.twitter.challenge.repo;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

  /**
   * coord : {"lon":-122.42,"lat":37.77}
   * weather : {"temp":14.77,"pressure":1007,"humidity":85}
   * wind : {"speed":0.51,"deg":284}
   * rain : {"3h":1}
   * clouds : {"cloudiness":65}
   * name : San Francisco
   */

  private CoordBean coord;
  private WeatherBean weather;
  private WindBean wind;
  private RainBean rain;
  private CloudsBean clouds;
  private String name;

  public CoordBean getCoord() {
    return coord;
  }

  public void setCoord(CoordBean coord) {
    this.coord = coord;
  }

  public WeatherBean getWeather() {
    return weather;
  }

  public void setWeather(WeatherBean weather) {
    this.weather = weather;
  }

  public WindBean getWind() {
    return wind;
  }

  public void setWind(WindBean wind) {
    this.wind = wind;
  }

  public RainBean getRain() {
    return rain;
  }

  public void setRain(RainBean rain) {
    this.rain = rain;
  }

  public CloudsBean getClouds() {
    return clouds;
  }

  public void setClouds(CloudsBean clouds) {
    this.clouds = clouds;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static class CoordBean {
    /**
     * lon : -122.42
     * lat : 37.77
     */

    private double lon;
    private double lat;

    public double getLon() {
      return lon;
    }

    public void setLon(double lon) {
      this.lon = lon;
    }

    public double getLat() {
      return lat;
    }

    public void setLat(double lat) {
      this.lat = lat;
    }
  }

  public static class WeatherBean {
    /**
     * temp : 14.77
     * pressure : 1007
     * humidity : 85
     */

    private double temp;
    private int pressure;
    private int humidity;

    public double getTemp() {
      return temp;
    }

    public void setTemp(double temp) {
      this.temp = temp;
    }

    public int getPressure() {
      return pressure;
    }

    public void setPressure(int pressure) {
      this.pressure = pressure;
    }

    public int getHumidity() {
      return humidity;
    }

    public void setHumidity(int humidity) {
      this.humidity = humidity;
    }
  }

  public static class WindBean {
    /**
     * speed : 0.51
     * deg : 284
     */

    private double speed;
    private int deg;

    public double getSpeed() {
      return speed;
    }

    public void setSpeed(double speed) {
      this.speed = speed;
    }

    public int getDeg() {
      return deg;
    }

    public void setDeg(int deg) {
      this.deg = deg;
    }
  }

  public static class RainBean {
    /**
     * 3h : 1
     */

    @SerializedName("3h")
    private int _$3h;

    public int get_$3h() {
      return _$3h;
    }

    public void set_$3h(int _$3h) {
      this._$3h = _$3h;
    }
  }

  public static class CloudsBean {
    /**
     * cloudiness : 65
     */

    private int cloudiness;

    public int getCloudiness() {
      return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
      this.cloudiness = cloudiness;
    }
  }
}
