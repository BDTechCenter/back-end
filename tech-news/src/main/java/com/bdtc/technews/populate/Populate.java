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
        String author1 = "Hermes Renan";
        String title1 = "The power of Spring Boot";
        String summary1 = "The reasons to learn SpringBoot to save your project";
        String body1 = NewsBody.BODY1.getTxt();
        List<String> tags1 = List.of("ETS", "apprentices", "bosch", "technical-course");
        File imageFile1 = new File("src/main/java/com/bdtc/technews/populate/images/new1-image.png");
        boolean isPublished1 = true;

        String author2 = "Jonatan";
        String title2 = "AI in Bosch";
        String summary2 = "How AI can be your best coworker";
        String body2 = NewsBody.BODY2.getTxt();
        List<String> tags2 = List.of("ai", "faster-working", "development", "machine-learning");
        File imageFile2 = new File("src/main/java/com/bdtc/technews/populate/images/new2-image.png");
        boolean isPublished2 = true;

        String author3 = "Astolfo Valeri";
        String title3 = "The advantages of home office";
        String summary3 = "Discover how home office can benefit companies and employees";
        String body3 = NewsBody.BODY3.getTxt();
        List<String> tags3 = List.of("home-office", "flexible");
        File imageFile3 = new File("src/main/java/com/bdtc/technews/populate/images/new3-image.png");
        boolean isPublished3 = true;

        createNews(baseUrl, author1, title1, summary1, body1, tags1, imageFile1, isPublished1);
        createNews(baseUrl, author2, title2, summary2, body2, tags2, imageFile2, isPublished2);
        createNews(baseUrl, author3, title3, summary3, body3, tags3, imageFile3, isPublished3);
    }


    private static void createNews(String baseUrl, String author, String title, String summary, String body, List<String> tags, File imageFile, boolean isPublished) throws IOException {
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
