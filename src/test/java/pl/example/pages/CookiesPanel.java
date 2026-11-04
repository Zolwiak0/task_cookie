package pl.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CookiesPanel {

    private final Locator customizeButton;
    private final Locator analyticsSwitch;
    private final Locator acceptSelectedButton;

    public CookiesPanel(Page page) {
        customizeButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Dostosuj").setExact(true));

        analyticsSwitch = page.getByRole(
                AriaRole.SWITCH,
                new Page.GetByRoleOptions().setName("Cookies analityczne").setExact(true));

        acceptSelectedButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Zaakceptuj zaznaczone").setExact(true));
    }

    public void customize() {
        customizeButton.click();
    }

    public void enableAnalytics() {
        analyticsSwitch.setChecked(true);
    }

    public void acceptSelected() { acceptSelectedButton.click();
    }
}