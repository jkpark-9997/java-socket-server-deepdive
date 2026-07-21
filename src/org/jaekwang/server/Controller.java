package org.jaekwang.server;

import org.jaekwang.server.http.HttpRequest;
import org.jaekwang.server.http.HttpResponse;

import java.io.IOException;

public interface Controller {
    public  void process(HttpRequest request, HttpResponse response) throws IOException;
}
