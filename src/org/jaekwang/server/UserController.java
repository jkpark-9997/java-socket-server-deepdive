package org.jaekwang.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UserController implements Controller  {
    public void process(Socket socket) throws IOException {
        OutputStream out = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(out, true);

        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html");

        writer.println();

        writer.println("<html><body><h1>This is user page!</h1></body></html>");
    }
}
