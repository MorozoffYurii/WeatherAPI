package com.example.a01022021;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class HTTPRequest implements Runnable{

    private Handler handler;
    private URL url;

    HTTPRequest(Handler h){
        try {
            this.url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=78f95c79f6feac6bf70f79bc09d47bab");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.handler = h;
    }
    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            while(scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            connection.disconnect();
            Log.d("RRR",response.toString());
            Message m = Message.obtain();
            m.obj = response.toString();
            handler.sendMessage(m);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
