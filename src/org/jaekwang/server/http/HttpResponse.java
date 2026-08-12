package org.jaekwang.server.http;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpResponse {
    private OutputStream out;
    public HttpResponse(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void sendOk(String content) throws IOException {
        PrintWriter writer = new PrintWriter(this.out, true);
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html");

        writer.println();

        writer.println(content);
    }
    public void sendNotFound() throws IOException {
        PrintWriter writer = new PrintWriter(this.out, true);
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/html");
        writer.println();
        writer.println("<h1>404 Not Found</h1>");
    }
    public void sendBadRequest() throws IOException {
        PrintWriter writer = new PrintWriter(this.out, true);
        writer.println("HTTP/1.1 400 Bad Request");
        writer.println("Content-Type: text/html");
        writer.println();
        writer.println("<h1>400 Bad Request</h1>");
    }
}
