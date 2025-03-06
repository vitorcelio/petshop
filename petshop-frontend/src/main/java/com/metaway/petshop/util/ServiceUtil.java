package com.metaway.petshop.util;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ServiceUtil {

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();


    @SneakyThrows
    public static String get(String url, boolean auth, String token) {
        var uri = new URI(url);

        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET();

        if (auth && (token != null && !token.isEmpty())) {
            request.header("Authorization", String.format("Bearer %s", token));
        }


        HttpRequest requestBuild = request.build();
        var response = client.send(requestBuild, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @SneakyThrows
    public static String post(String url, String json, boolean auth, String token) {
        var uri = new URI(url);

        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json != null ? json : "{}", StandardCharsets.UTF_8));

        if (auth && (token != null && !token.isEmpty())) {
            request.header("Authorization", String.format("Bearer %s", token));
        }

        HttpRequest requestBuild = request.build();
        var response = client.send(requestBuild, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @SneakyThrows
    public static String put(String url, String json, boolean auth, String token) {
        var uri = new URI(url);

        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json != null ? json : "{}", StandardCharsets.UTF_8));

        if (auth && (token != null && !token.isEmpty())) {
            request.header("Authorization", String.format("Bearer %s", token));
        }

        HttpRequest requestBuild = request.build();
        var response = client.send(requestBuild, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @SneakyThrows
    public static String patch(String url, String json, boolean auth, String token) {
        var uri = new URI(url);

        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json != null ? json : "",
                        StandardCharsets.UTF_8));

        if (auth && (token != null && !token.isEmpty())) {
            request.header("Authorization", String.format("Bearer %s", token));
        }

        HttpRequest requestBuild = request.build();
        var response = client.send(requestBuild, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @SneakyThrows
    public static String delete(String url, boolean auth, String token) {
        var uri = new URI(url);

        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .DELETE();

        if (auth && (token != null && !token.isEmpty())) {
            request.header("Authorization", String.format("Bearer %s", token));
        }

        HttpRequest requestBuild = request.build();
        var response = client.send(requestBuild, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
