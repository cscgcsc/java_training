package ru.stqa.pft.mantis.tests;
import org.testng.annotations.Test;

public class SoapClientTests extends TestBase{

    @Test
    public void TestSkipped() {
        skipIfNotFixed(0000001);
    }

    @Test
    public void TestNotSkipped() {
        skipIfNotFixed(0000002);
        System.out.println("OK");
    }
}
