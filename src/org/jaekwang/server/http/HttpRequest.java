package org.jaekwang.server.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private String version;
    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> queryParameters = new HashMap<>();

    public HttpRequest(InputStream inputstream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

        String line;
        //요청 라인 파싱 및 객체의 멤버 변수에 저장
        line = reader.readLine();
        if (line == null || line.isEmpty()) {
            return;
        }
        String[] parts = line.split(" ");
        if (parts.length < 3) {
            return;
        }
        String method =  parts[0];
        String rawuri = parts[1];
        String version = parts[2];

        this.method = method;
        this.version = version;

        //쿼리 파라미터 파싱
        int questionIndex = rawuri.indexOf('?');

        if (questionIndex != -1) {
            this.uri = rawuri.substring(0, questionIndex);
            String queryString = rawuri.substring(questionIndex + 1);

            String[] queryStringParts = queryString.split("&");
            for (String queryStringPart : queryStringParts) {
                String[] keyValue = queryStringPart.split("=");

                if (keyValue.length == 2) {
                    this.queryParameters.put(keyValue[0], keyValue[1]);
                } else {
                    throw new IllegalArgumentException("Invalid query string parameter: " + queryStringPart);
                }
            }
        } else {
            this.uri = rawuri;
        }

        //헤더 Key-value 파싱 및 해시 맵에 저장
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] parts2 = line.split(": ", 2);
            if (parts2.length == 2) {
                headers.put(parts2[0], parts2[1]);
            }
        }

        //body파싱 : GET에서는 비어있고, POST/PUT에서 사용, json 데이터 형식으로 가정
        if ("POST".equals(this.method) || "PUT".equals(this.method)) {
            //로직은 추후에 다시 작성할 예정. 아직 JSON 포맷에 대해 정확히 모름.
        }

    }

    public String getMethod() {
        return method;
    }
    public String getUri() {
        return uri;
    }

    public String getQueryParameter(String key) {
        return queryParameters.get(key);
    }
}
