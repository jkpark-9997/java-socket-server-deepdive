package org.jaekwang.server;

import org.jaekwang.server.http.HttpRequest;
import org.jaekwang.server.http.HttpResponse;

import java.io.IOException;

public class UserController implements Controller  {
    public void process(HttpRequest request, HttpResponse response) throws IOException {
            System.out.println(request.getMethod()+"------"+request.getUri());
            response.sendOk("<h1>sendOK method perfectly running</h1>");

            //쿼리 파라미터 파싱 테스트 로직

            System.out.println("User ID : " +request.getQueryParameter("id"));
            System.out.println("User Name : " +request.getQueryParameter("name"));
            System.out.println("User Email : " +request.getQueryParameter("email"));
            System.out.println("User Role : " +request.getQueryParameter("role"));

    }
}
