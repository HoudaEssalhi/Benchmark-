package com.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://0.0.0.0:8080/api/";

    public static HttpServer startServer() {
        // Charger toutes les classes annotées @Path
        final ResourceConfig rc = new ResourceConfig().packages("com.example.ressource");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("✅ Serveur JAX-RS démarré sur " + BASE_URI);
        System.out.println("➡️  Appuyez sur CTRL+C pour arrêter le serveur...");
        System.in.read();
        server.shutdownNow();
    }
}
