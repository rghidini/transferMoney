package com.ghidini.tm.config.application;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import com.ghidini.tm.exceptions.DBCommitException;
import com.ghidini.tm.exceptions.IdNotFoundException;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/";

	static HttpServer startServer() {

		ResourceConfig config = new ResourceConfig()
				.register(new DBCommitException())
				.register(new IdNotFoundException())
				.register(JacksonJsonProvider.class);

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
	}

	/**
	 * Main method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		startServer();
	}

}