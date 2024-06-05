package com.example.demo295;

import org.junit.jupiter.api.Test;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleControllerTests {
    private static final String SERVICE_URL
            = "http://localhost:3030/choesang/resources/api/modules";

    @Test
    public void getModules_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }
    @Test
    public void getModuleById_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL + "/3");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void createModule_whenCorrectRequest_thenResponseCodeSuccess() throws IOException {
        HttpPost request = new HttpPost(SERVICE_URL + "/create");
        request.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity("{\"name\": \"Test Module\", \"start\": \"2025-01-01T00:00:00\", \"end\": \"2025-12-31T23:59:59\", \"cost\": 100.0, \"active\": true}");
        request.setEntity(entity);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void getModuleById_whenNotFound_thenResponseCodeNotFound() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL + "/9999");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NO_CONTENT, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void editModule_whenNotFound_thenResponseCodeNotFound() throws IOException {
        HttpPut request = new HttpPut(SERVICE_URL + "/edit");
        request.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity("{\"id\": 9999, \"name\": \"Nonexistent Module\", \"start\": \"2025-01-01T00:00:00\", \"end\": \"2025-12-31T23:59:59\", \"cost\": 100.0, \"active\": true}");
        request.setEntity(entity);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void deleteModule_whenNotFound_thenResponseCodeNotFound() throws IOException {
        HttpDelete request = new HttpDelete(SERVICE_URL + "/delete/9999");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }
}
