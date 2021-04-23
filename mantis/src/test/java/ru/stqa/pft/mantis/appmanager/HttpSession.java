package ru.stqa.pft.mantis.appmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.stqa.pft.mantis.model.Account;

public class HttpSession {
    private ApplicationManager app;
    private CloseableHttpClient httpClient;

    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpClient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setConnectionTimeToLive(10, TimeUnit.MINUTES)
                .build();
    }

    public boolean isLoggedInAs(Account account) throws Exception {
        HttpGet request = new HttpGet(String.format("%s/index.php", app.properties.getProperty("baseUrl")));

        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        CloseableHttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
        String body = responseByText(response.getEntity());
        //Header headers = entity.getContentType();

        return body.contains("<span class=\"user-info\">" + account.getLogin() + "</span>");
    }

    public void login(Account account) throws Exception {
        HttpPost post = new HttpPost(String.format("%s/login.php", app.properties.getProperty("baseUrl")));

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", account.getLogin()));
        urlParameters.add(new BasicNameValuePair("password", account.getPassword()));
        urlParameters.add(new BasicNameValuePair("return", "index.php"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        CloseableHttpResponse response = httpClient.execute(post);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    private String responseByText(HttpEntity entity) throws IOException {
        String result = "";
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    public void close() {
       try {
           httpClient.close();
       }
       catch (IOException ex) {
           System.out.println(ex.getMessage());
       }
    }
}
