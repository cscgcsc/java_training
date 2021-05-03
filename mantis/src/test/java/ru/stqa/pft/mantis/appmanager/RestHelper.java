package ru.stqa.pft.mantis.appmanager;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.stqa.pft.mantis.model.Issue;

import java.io.IOException;
import java.util.List;

public class RestHelper {
    private ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Issue getIssue(int id) throws IOException {
        Request get = Request.Get(String.format("%s/issues/%s.json", app.properties.getProperty("bugifyREST"), id));
        String result = auth().execute(get)
                .returnContent()
                .toString();
        JsonElement parsedJson = JsonParser.parseString(result);
        JsonElement issuesJson = parsedJson.getAsJsonObject().get("issues");
        Gson gson = new Gson();
        List<Issue> issues = gson.fromJson(issuesJson, new TypeToken<List<Issue>>(){}.getType()); //List<Issue>.class
        return issues.iterator().next();
    }

    public List<Issue> getIssues() throws IOException {
        String result = auth().execute(Request.Get(String.format("%s/issues.json?page=1&limit=20", app.properties.getProperty("bugifyREST"))))
                .returnContent()
                .toString();
        JsonElement parsedJson = JsonParser.parseString(result);
        JsonElement issuesJson = parsedJson.getAsJsonObject().get("issues");
        Gson gson = new Gson();
        List<Issue> issues = gson.fromJson(issuesJson, new TypeToken<List<Issue>>(){}.getType()); //List<Issue>.class
        return issues;
    }

    public void updateIssue(int id, int state) throws IOException {
        Request post = Request.Post(String.format("%s/issues/%s.json", app.properties.getProperty("bugifyREST"), id));
        post.bodyForm(  new BasicNameValuePair("method", "update"),
                        new BasicNameValuePair("issue[state]", Integer.toString(state)));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        String result = auth().execute(post)
                .returnContent()
                .toString();
        JsonElement parsedJson = JsonParser.parseString(result);
        String messageJson = parsedJson.getAsJsonObject().get("message").toString();
    }

    private Executor auth() {
        return Executor.newInstance().auth(app.properties.getProperty("APIKey"), "");
    }

    public boolean isIssueFixed(int id) {
        try {
            Issue issue = getIssue(id);
            return issue.getState().equals("2") || issue.getState().equals("3");
        }
        catch (Exception ex) {
            System.out.println("REST exception: " + ex.getMessage());
        }
        return false;
    }
}
