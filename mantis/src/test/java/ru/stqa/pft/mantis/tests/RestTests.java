package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import java.io.IOException;


public class RestTests extends TestBase{

    @Test
    public void TestSkipped() {
        skipIfNotFixedREST(1025);
    }

    @Test
    public void TestNotSkipped() {
        skipIfNotFixedREST(1024);
        System.out.println("OK");
    }
}
