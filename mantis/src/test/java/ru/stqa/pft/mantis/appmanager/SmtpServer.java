package ru.stqa.pft.mantis.appmanager;

import org.apache.tools.ant.taskdefs.Sleep;
import org.apache.tools.mail.MailMessage;
import org.openqa.selenium.By;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmtpServer {
    private Wiser smtp;

    public SmtpServer() {
        smtp = new Wiser();
    }

    public void start() {
        smtp.start();
    }

    public void stop() {
        smtp.stop();
    }

    public List<WiserMessage> getMessages() {
        return smtp.getMessages();
    }

    public String readMessage(WiserMessage message) {
        try {
            MimeMessage mm = message.getMimeMessage();
            return mm.getContent().toString();
        }
        catch(Exception ex) {
            System.out.println("Read mail error: " + ex.getMessage());
        }
        return "";
    }

    public List<String> findURL(String text) {
        List<String> urlList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(http|https)://[.a-zA-Z0-9]+/*([.a-zA-Z0-9-_/?=&]*/*)");
        Matcher matcher = pattern.matcher(text);
        while(matcher.find())
            urlList.add(matcher.group());
        return urlList;
    }

    public List<WiserMessage> waitForMail(Integer count, Integer timeout) {
        Sleep element = new Sleep();
        for (int second = 0;; second++) {
            if (second >= timeout) {
                break;
            }
            if (smtp.getMessages().size() > count)
                break;
            element.doSleep(1000);
        }
        return smtp.getMessages();
    }
}
