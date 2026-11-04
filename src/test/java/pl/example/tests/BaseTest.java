package pl.example.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseTest {
    private Playwright playwright;
    private Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected void startBrowser(String browserName) {
        playwright = Playwright.create();

        BrowserType browserType = switch (browserName) {
            case "chromium" -> playwright.chromium();
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> throw new IllegalArgumentException(browserName);
        };

        browser = browserType.launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(
                        System.getProperty("headless", "false"))));

        context = browser.newContext(new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(browserName.equals("webkit")));
        page = context.newPage();
        page.setDefaultTimeout(5_000);
        page.setDefaultNavigationTimeout(5_000);
    }

    @AfterEach
    void closeBrowser() {
        try {
            if (context != null) context.close();
        } finally {
            try {
                if (browser != null) browser.close();
            } finally {
                if (playwright != null) playwright.close();
            }
        }
    }
}
