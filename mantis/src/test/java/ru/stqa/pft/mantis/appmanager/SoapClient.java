package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;
import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapClient {
    private MantisConnectPortType connect;
    private ApplicationManager app;

    public SoapClient(ApplicationManager app) {
        this.app = app;
        try {
            connect = new MantisConnectLocator().getMantisConnectPort(new URL(app.properties.getProperty("mantisSOAP")));
        }
        catch (Exception ex) {
            System.out.println("SOAP exception: " + ex.getMessage());
        }
    }

    public Set<Project> getProjects() throws RemoteException {
        ProjectData[] projects = connect.mc_projects_get_user_accessible(app.properties.getProperty("adminLogin"), app.properties.getProperty("adminPassword"));
        return Arrays.stream(projects)
                .map(p->new Project()
                        .setId(p.getId())
                        .setName(p.getName()))
                .collect(Collectors.toSet());
    }

    public Set<Issue> getIssues(int projectId) throws RemoteException {
        IssueData[] issues = connect.mc_project_get_issues(app.properties.getProperty("adminLogin"), app.properties.getProperty("adminPassword"), BigInteger.valueOf(projectId), BigInteger.valueOf(1), BigInteger.valueOf(-1));
        return Arrays.stream(issues)
                .map(p->new Issue()
                        .setId(p.getId())
                        .setSummary(p.getSummary())
                        .setDescription(p.getDescription())
                        .setStatus(p.getStatus().getId())
                        .setStatusName(p.getStatus().getName()))
                .collect(Collectors.toSet());
    }

    public Issue getIssue(int id) throws RemoteException {
        IssueData issueData = connect.mc_issue_get(app.properties.getProperty("adminLogin"), app.properties.getProperty("adminPassword"), BigInteger.valueOf(id));
        return new Issue()
                .setId(issueData.getId())
                .setSummary(issueData.getSummary())
                .setDescription(issueData.getDescription())
                .setStatus(issueData.getStatus().getId())
                .setStatusName(issueData.getStatus().getName());
    }

    public boolean isIssueFixed(int id) {
        try {
            Issue issue = getIssue(id);
            return issue.getStatus().equals(BigInteger.valueOf(80)) || issue.getStatus().equals(BigInteger.valueOf(90));
        }
        catch (Exception ex) {
            System.out.println("SOAP exception: " + ex.getMessage());
        }
        return false;
    }
}
