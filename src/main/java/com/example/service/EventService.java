package com.example.service;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public interface EventService {
	public String FetchEvent(String token) 
			throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException;
}
