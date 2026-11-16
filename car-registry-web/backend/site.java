import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class site {

    private static final int PORT = 8080;
    private static final Path FRONTEND_DIR = resolveFrontendDir();
    private static final List<Car> CARS = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/", exchange -> handleStatic(exchange, "index.html", "text/html; charset=UTF-8"));
        server.createContext("/style.css", exchange -> handleStatic(exchange, "style.css", "text/css; charset=UTF-8"));
        server.createContext("/api/cars", site::handleCarsApi);

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor rodando em http://localhost:" + PORT);
    }

    private static void handleStatic(HttpExchange exchange, String fileName, String contentType) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "text/plain; charset=UTF-8", "Método não permitido");
            return;
        }

        Path filePath = FRONTEND_DIR.resolve(fileName).normalize();
        if (!filePath.startsWith(FRONTEND_DIR) || !Files.exists(filePath)) {
            sendResponse(exchange, 404, "text/plain; charset=UTF-8", "Arquivo não encontrado");
            return;
        }

        byte[] content = Files.readAllBytes(filePath);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, content.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(content);
        }
    }

    private static void handleCarsApi(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod().toUpperCase();

        switch (method) {
            case "GET" -> sendResponse(exchange, 200, "application/json; charset=UTF-8", carsToJson());
            case "POST" -> handleCreateCar(exchange);
            default -> sendResponse(exchange, 405, "application/json; charset=UTF-8",
                    "{\"error\":\"Método não permitido.\"}");
        }
    }

    private static void handleCreateCar(HttpExchange exchange) throws IOException {
        String body = readBody(exchange.getRequestBody());

        try {
            String brand = extractJsonString(body, "brand");
            String model = extractJsonString(body, "model");
            String color = extractJsonString(body, "color");
            int year = extractJsonInt(body, "year");

            Car car = brand.equalsIgnoreCase("Toyota")
                    ? new Toyota(model, color, year)
                    : new Car(brand, model, color, year);

            CARS.add(car);
            sendResponse(exchange, 201, "application/json; charset=UTF-8", carToJson(car));
        } catch (IllegalArgumentException e) {
            sendResponse(exchange, 400, "application/json; charset=UTF-8",
                    "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private static String carsToJson() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < CARS.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(carToJson(CARS.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    private static String carToJson(Car car) {
        return String.format(
                "{\"brand\":\"%s\",\"model\":\"%s\",\"color\":\"%s\",\"year\":%d,\"isToyota\":%b}",
                escapeJson(car.getBrand()),
                escapeJson(car.getModel()),
                escapeJson(car.getColor()),
                car.getYear(),
                car instanceof Toyota);
    }

    private static String readBody(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static String extractJsonString(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            return "";
        }
        return matcher.group(1)
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    private static int extractJsonInt(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(-?\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Ano inválido. Digite um número inteiro.");
        }
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ano inválido. Digite um número inteiro.");
        }
    }

    private static String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private static void sendResponse(HttpExchange exchange, int status, String contentType, String body)
            throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static Path resolveFrontendDir() {
        Path local = Path.of("frontend").toAbsolutePath().normalize();
        if (Files.isDirectory(local)) {
            return local;
        }
        Path parent = Path.of("..", "frontend").toAbsolutePath().normalize();
        if (Files.isDirectory(parent)) {
            return parent;
        }
        return Path.of("c:\\vscode\\SiteCarJava\\frontend").toAbsolutePath().normalize();
    }
}
