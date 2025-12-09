package com.joseantonio.historialproducto;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Tools {
    private static final String TAG = "Herramientas";

    /**
     * Define una interfaz para manejar la respuesta de la llamada HTTP en el hilo principal.
     */
    public interface HttpCallback {
        void onSuccess(String response) throws JSONException;
        void onError(Exception e);
    }

    public static void httpRequest(final String urlString, final HttpCallback callback) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    // 1. Configuración de la Conexión
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(10000); // 10 segundos
                    urlConnection.setReadTimeout(10000); // 10 segundos
                    urlConnection.connect();

                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(urlConnection.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();

                        final String response = content.toString();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    callback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                    } else {
                        throw new Exception("HTTP Response Code: " + responseCode);
                    }

                } catch (Exception e) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        }).start();
    }
}
