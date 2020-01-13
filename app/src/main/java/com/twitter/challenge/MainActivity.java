package com.twitter.challenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.challenge.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements ViewPresenterContract.BaseView {
    private MainPresenter mainPresenter;
    private String deviation;
    private Double temperature;
    private String windSpeed;
    private boolean isCloudy;

    private TextView temperatureView;
    private TextView windSpeedView;
    private TextView deviationView;
    private ImageView cloudView;
    private Button deviationButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureView = (TextView) findViewById(R.id.temperature);
        windSpeedView = (TextView) findViewById(R.id.speed);
        deviationView = (TextView) findViewById(R.id.deviation);
        cloudView = (ImageView) findViewById(R.id.cloudness);
        deviationButton = (Button) findViewById(R.id.deviationButton);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mainPresenter = new MainPresenter(this);
        if (savedInstanceState == null) {
            mainPresenter.loadCurrentWeather();
        } else {
            if (savedInstanceState.getString("windSpeed") != null) {
                showCurrentWeather(savedInstanceState.getDouble("temperature"),
                  savedInstanceState.getString("windSpeed"),
                  savedInstanceState.getBoolean("isCloudy"));
            }
            if (savedInstanceState.getString("deviation") != null) {
                showNextFiveDaysDeviation(savedInstanceState.getString("deviation"));
            }
        }

        deviationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progressBar.getVisibility() == View.GONE){
                    mainPresenter.loadNextFiveDaysWeather();
                }
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showCurrentWeather(Double temperature,String windSpeed, boolean isCloudy) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.isCloudy = isCloudy;
        temperatureView.setText(getString(R.string.temperature, temperature,
          TemperatureConverter.celsiusToFahrenheit(temperature.floatValue())));
        windSpeedView.setText(windSpeed);
        cloudView.setVisibility(isCloudy ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNextFiveDaysDeviation(String deviation) {
        this.deviation = deviation;
        deviationView.setText("Deviation: " + deviation);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (windSpeed != null) {
            outState.putString("windSpeed", windSpeed);
            outState.putDouble("temperature", temperature);
            outState.putBoolean("isCloudy", isCloudy);
        }
        if (deviation != null) {
            outState.putString("deviation", deviation);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (mainPresenter != null) {
            mainPresenter.detachView();
            mainPresenter = null;
        }
        super.onDestroy();
    }
}
