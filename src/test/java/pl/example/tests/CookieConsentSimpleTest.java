package pl.example.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.example.pages.CookiesPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieConsentSimpleTest extends BaseTest{
    @BeforeEach
    void setUp() {
        startBrowser(System.getProperty("browser", "chromium"));
    }

    @Test
    void shouldSaveAnalyticsCookiesAfterConsent()  {
        CookiesPanel cookiesPanel = new CookiesPanel(page);
        page.navigate("https://www.ing.pl/");

        cookiesPanel.customize();
        cookiesPanel.enableAnalytics();
        cookiesPanel.acceptSelected();

        page.waitForCondition(() -> context.cookies().stream()
                        .anyMatch(cookie ->
                                cookie.name.equals("cookiePolicyGDPR")
                                        && cookie.domain.equals(".ing.pl")
                                        && cookie.value.equals("3")));

        var consentCookie = context.cookies().stream()
                .filter(cookie -> cookie.name.equals("cookiePolicyGDPR")
                        && cookie.domain.equals(".ing.pl"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Brak cookie zgody"));

        assertEquals("3", consentCookie.value);
    }
}