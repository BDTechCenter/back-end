package com.bdtc.technews.populate;

import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.client5.http.classic.methods.HttpPost;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Populate {

    public static void main(String[] args) throws IOException {
        String baseUrl = System.getenv("BASE_URL");
        System.out.println(baseUrl);

        // Creating variables
        String author = "Raphael";
        String title = "Title Test API";
        String summary = "Sumary test api";
        String body = "Here you can read the body";
        List<String> tags = List.of("rest-api", "java", "springboot", "microservices");
        File imageFile = new File("src/main/java/com/bdtc/technews/populate/images/spring.png");
        boolean isPublished = true;

        // Setting up multipart/form-data body
        final FileBody fileBody = new FileBody(imageFile, ContentType.DEFAULT_BINARY);
        final StringBody authorBody = new StringBody(author, ContentType.MULTIPART_FORM_DATA);
        final StringBody titleBody = new StringBody(title, ContentType.MULTIPART_FORM_DATA);
        final StringBody summaryBody = new StringBody(summary, ContentType.MULTIPART_FORM_DATA);
        final StringBody bodyBody = new StringBody(body, ContentType.MULTIPART_FORM_DATA);
        final StringBody tagsBody = new StringBody(String.join(",", tags), ContentType.MULTIPART_FORM_DATA);
        final StringBody isPublishedBody = new StringBody(String.valueOf(isPublished), ContentType.MULTIPART_FORM_DATA);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("image", fileBody);
        builder.addPart("author", authorBody);
        builder.addPart("title", titleBody);
        builder.addPart("summary", summaryBody);
        builder.addPart("body", bodyBody);
        builder.addPart("tags", tagsBody);
        builder.addPart("isPublished", isPublishedBody);
        final HttpEntity entity = builder.build();

        HttpPost post = new HttpPost(baseUrl);

        post.setEntity(entity);
        try(CloseableHttpClient client = HttpClientBuilder.create().build()) {
            client.execute((ClassicHttpRequest) post, response -> {
                System.out.println(response);
                return null;
            });
        }
    }
}