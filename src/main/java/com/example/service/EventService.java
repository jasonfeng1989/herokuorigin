package com.example.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EventService {
	public void FetchEvent(String token) throws IOException{
		//OAuthConsumer consumer = new DefaultOAuthConsumer("test-7940", "UlhALaaoJ20e6caa");
		URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/"+token);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		//consumer.sign(request);
		request.connect();
	}
}
