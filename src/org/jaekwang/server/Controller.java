package org.jaekwang.server;

import java.io.IOException;
import java.net.Socket;

public interface Controller {
    public  void process(Socket socket) throws IOException;
}
