package org.jaekwang.server;

import org.jaekwang.server.http.HttpRequest;
import org.jaekwang.server.http.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WebServer {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    static final Map<String, Controller> controlMap = new HashMap<>();

    static {
        controlMap.put("/user", new UserController());
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = acceptConnection(serverSocket);


            threadPool.execute(() -> {
                try {

                    HttpResponse response = new HttpResponse(socket.getOutputStream());

                    try {
                        HttpRequest request = new HttpRequest(socket.getInputStream());

                        Controller controller = controlMap.get(request.getUri());

                        if (controller != null) {
                            controller.process(request, response);
                        } else {
                            //에러처리
                            response.sendNotFound();
                            System.out.println("No such controller " + request.getUri());
                        }
                    }  catch (IllegalArgumentException e) {
                        System.out.println("Bad Request 400 Error " + e.getMessage());
                        response.sendBadRequest();
                    }

                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static Socket acceptConnection(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection from " + socket.getInetAddress().getHostName());
        return socket;
    }
}
    /*
    public static void dispatchRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line;

        line = reader.readLine();
        if (line == null || line.isEmpty()) {
            return;
        }
        String[] parts = line.split(" ");
        if (parts.length < 3) {
            return;
        }
        String method =  parts[0];
        String uri = parts[1];
        String version = parts[2];

        Controller controller = controlMap.get(uri);
        if (controller == null) {
            System.out.println("Invalid URI: " + uri);
            return;
        }
        controller.process(socket);

    }
}*/
