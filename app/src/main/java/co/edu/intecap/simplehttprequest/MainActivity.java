package co.edu.intecap.simplehttprequest;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements HttpResponseListener{
    private final String TAG = MainActivity.class.getSimpleName();
    Button btnSendRequest;
    private TextView tvResponse;
    private FetchWeatherData fetchWeatherData;
    private static final String ENDPOINT = "https://samples.openweathermap.org/data/2.5/history/city?q=London,UK&appid=b1b15e88fa797225412429c1c50c122a1";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnSendRequest = findViewById(R.id.btn_send_request);
        tvResponse = findViewById(R.id.tv_response);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(MainActivity.this, "Cargando", "Esperando respuesta...");
                fetchWeatherData = new FetchWeatherData(MainActivity.this);
                fetchWeatherData.execute(ENDPOINT);
            }
        });
    }

    @Override
    public void onHttpResponse(String response) {
        progressDialog.dismiss();
        tvResponse.setText(response);
    }
}
