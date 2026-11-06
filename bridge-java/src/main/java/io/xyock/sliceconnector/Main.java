package io.xyock.sliceconnector;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public final class Main {
    private static final String VERSION = "0.1.0";
    private static final String ALLOWED_ORIGIN = "https://xyock.github.io";
    private static final String XYOCK_URL = "https://xyock.github.io/beta-xyock/";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8765), 0);
        server.createContext("/health", Main::health);
        server.createContext("/launch", Main::launch);
        server.setExecutor(Executors.newFixedThreadPool(2));
        server.start();
        System.out.println("Slice Connector listening on http://127.0.0.1:8765");
    }

    private static void health(HttpExchange exchange) throws IOException {
        if (!isAllowedOrigin(exchange)) { reply(exchange, 403, "{\"error\":\"origin_not_allowed\"}"); return; }
        if (!"GET".equals(exchange.getRequestMethod())) {
            reply(exchange, 405, "{\"error\":\"method_not_allowed\"}");
            return;
        }
        reply(exchange, 200, "{\"status\":\"ok\",\"service\":\"slice-connector\",\"version\":\"" + VERSION + "\"}");
    }

    private static void launch(HttpExchange exchange) throws IOException {
        if (!isAllowedOrigin(exchange)) { reply(exchange, 403, "{\"error\":\"origin_not_allowed\"}"); return; }
        if (!"POST".equals(exchange.getRequestMethod())) {
            reply(exchange, 405, "{\"error\":\"method_not_allowed\"}");
            return;
        }
        // Only a fixed destination is supported. No user-supplied URL or shell command.
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(XYOCK_URL));
            reply(exchange, 200, "{\"status\":\"launch_requested\"}");
        } catch (Exception exception) {
            reply(exchange, 500, "{\"error\":\"launch_failed\"}");
        }
    }

    private static boolean isAllowedOrigin(HttpExchange exchange) {
        String origin = exchange.getRequestHeaders().getFirst("Origin");
        return origin == null || ALLOWED_ORIGIN.equals(origin);
    }

    private static void reply(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.getResponseHeaders().set("Vary", "Origin");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        exchange.sendResponseHeaders(status, bytes.length);
        try (var output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
