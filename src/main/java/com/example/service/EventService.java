package com.example.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Service
public class EventService {
	public String FetchEvent(String token) throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException{
		OAuthConsumer consumer = new DefaultOAuthConsumer("test-7940", "UlhALaaoJ20e6caa");
		URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/"+token);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		consumer.sign(request);
		request.connect();
		int ResponseCode = request.getResponseCode();
		String ResponseMessage = request.getResponseMessage();
		return ResponseMessage;
	}
}
