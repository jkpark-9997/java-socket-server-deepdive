package org.jaekwang.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WebServer {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = acceptConnection(serverSocket);

            threadPool.execute(() -> {
                try {
                    readRequest(socket);

                    // 이것은 사용자가 1MB짜리 사진을 업로드하거나 다운로드하는 상황을 시뮬레이션합니다.
                    byte[] heavyPayload = new byte[1024 * 1024];

                    System.out.println("Processing request...(5 seconds)");
                    Thread.sleep(5000);

                    sendResponse(socket);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public static Socket acceptConnection(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection from " + socket.getInetAddress().getHostName());
        return socket;
    }

    public static void readRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            System.out.println(line);
        }
    }
    public static void sendResponse(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html");

        writer.println();

        writer.println("<html><body><h1>200 OK!</h1></body></html>");

    }
}
