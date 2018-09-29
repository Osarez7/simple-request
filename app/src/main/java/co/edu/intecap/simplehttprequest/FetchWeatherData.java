package co.edu.intecap.simplehttprequest;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FetchWeatherData extends AsyncTask<String, Void, String> {

    private HttpResponseListener httpResponseListener;
    private final static String TAG = FetchWeatherData.class.getSimpleName();

    public FetchWeatherData(@NonNull  HttpResponseListener httpResponseListener) {
        this.httpResponseListener = httpResponseListener;
    }

    @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            String response = null;
 
            try {

                URL url = new URL(params[0]);

                // Creamos el request y abrimos la conexión.
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Leemos el  input stream y lo cargamos en un string
                InputStream inputStream = urlConnection.getInputStream();

                return readInput(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
                return  null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

    private String readInput(InputStream inputStream) throws IOException {

        BufferedReader reader = null;

        String responseStr = null;

        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            return null;
        }
        responseStr = buffer.toString();

        if (reader != null) {
            try {
                reader.close();
            } catch (final IOException e) {
                Log.e(TAG, "Error closing stream", e);
            }
        }

        return responseStr;
    }

    @Override
        protected void onPostExecute(String response) {
            httpResponseListener.onHttpResponse(response);
        }
}