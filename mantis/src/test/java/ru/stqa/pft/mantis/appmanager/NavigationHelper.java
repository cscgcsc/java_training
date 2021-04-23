package ru.stqa.pft.mantis.appmanager;

public class NavigationHelper extends HelperBase{

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void goToLoginPage() {
        driver.get(String.format("%s/login_page.php", baseURL));
    }
}
