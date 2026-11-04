package pl.example.tests;

import com.microsoft.playwright.Page;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.example.pages.CookiesPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieConsentTest extends BaseTest {
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    void shouldSaveAnalyticsCookiesAfterConsent(String browserName) {
//        System.out.printf("START %s | %s | %s%n",
//                browserName,
//                Thread.currentThread().getName(),
//                java.time.LocalTime.now());
        startBrowser(browserName);
        CookiesPanel cookiesPanel = new CookiesPanel(page);
        page.navigate("https://www.ing.pl/");

        cookiesPanel.customize();
        cookiesPanel.enableAnalytics();
        cookiesPanel.acceptSelected();

        page.waitForCondition(() -> context.cookies().stream()
                        .anyMatch(cookie ->
                                cookie.name.equals("cookiePolicyGDPR")
                                        && cookie.domain.equals(".ing.pl")
                                        && cookie.value.equals("3")),
               
        );

        var consentCookie = context.cookies().stream()
                .filter(cookie -> cookie.name.equals("cookiePolicyGDPR")
                        && cookie.domain.equals(".ing.pl"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Brak cookie zgody"));

        assertEquals("3", consentCookie.value);
    }
}
