English version below
===
PL
===
Słowem wstępu
Podrzucam dwa alternatywne sposoby rozwiązania w osobnych klasach:

CookieConsentSimpleTest- odpala test na jednej przeglądarce.
W pipeline ten sam test uruchamia się równolegle dzięki macierzy GitHub Actions.

'CookieConsentTest'
Playwright pozwala uruchomić niezależne sesje obok siebie, więc tutaj JUnit odpala ten sam scenariusz równolegle na trzech przeglądarkach — jednym poleceniem:
(W kodzie masz zakomentowany print pokazujący start przeglądarek jeśli chcesz się upewnić że startują jednocześnie)
Jak uruchomić
Potrzebujesz Javy 17 i Mavena 3.9+. Wszystkie polecenia odpalaj w katalogu z pom.xml.
- Zainstaluj przeglądarki używane przez Playwright:
  mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium firefox webkit"

Lokalnie

CookieConsentSimpleTest
mvn test "-Dtest=CookieConsentSimpleTest" "-Dbrowser=chromium"

Możesz też podać firefox albo webkit.

'CookieConsentTest'
mvn test "-Dtest=CookieConsentTest"

Jeśli chcesz widzieć okna przeglądarek, dopisz "-Dheadless=false".
Raporty i screenshoty błędów znajdziesz w target/surefire-reports/.

GitHub Actions

Tutaj będziesz mieć trochę problemu chyba że dodasz swoje self-hosted runnery.
Uruchomienie przez chmurowy runner GitHuba wywala hCaptcha po Waszej stronie (z racji że w zadaniu nie było nic o tym nie kombinowałem)

Jak już uruchomisz 3 self-hosted runnery u siebie, wejdź w Actions → Cookie consent tests → Run workflow

Pipeline używa trzech runnerów self-hosted, Windows, X64.
Każdy obsługuje jedną przeglądarkę, więc wszystkie trzy zadania mogą działać jednocześnie. Runnery muszą być włączone i mieć Gita oraz Mavena w PATH.
Jeśli będziesz mieć tylko jeden runner testy uruchomią się sekwencyjnie.

Raporty z pipeline’u są do pobrania w sekcji "Artifacts".

=====
ENG
=====
A few words to start: I’m sharing two alternative approaches in separate classes:

CookieConsentSimpleTest — runs the test in one browser.
In the pipeline, the same test runs in parallel using a GitHub Actions matrix.

CookieConsentTest
Playwright allows independent sessions to run side by side, so here JUnit runs the same scenario in parallel across three browsers — with a single command.
(There’s a commented-out print statement in the code showing when the browsers start, if you want to check that they start at the same time.)
How to run
You need Java 17 and Maven 3.9+. Run all commands from the directory containing pom.xml.
- Install the browsers used by Playwright:
  mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium firefox webkit"

Locally

CookieConsentSimpleTest
mvn test "-Dtest=CookieConsentSimpleTest" "-Dbrowser=chromium"

You can also use firefox or webkit.

CookieConsentTest
mvn test "-Dtest=CookieConsentTest"

If you want to see the browser windows, add "-Dheadless=false".
Reports and failure screenshots can be found in target/surefire-reports/.

GitHub Actions

You’ll run into a bit of trouble here unless you add your own self-hosted runners.
Running through a GitHub-hosted runner triggers hCaptcha on your side (since the task didn’t mention this, I didn’t try to work around it).

Once you have 3 self-hosted runners running on your machine, go to Actions → Cookie consent tests → Run workflow.

The pipeline uses three runners with the labels self-hosted, Windows, X64.
Each handles one browser, so all three jobs can run at the same time. The runners need to be running and have Git and Maven in their PATH.
If you only have one runner, the tests will run sequentially.

Pipeline reports are available to download in the “Artifacts” section.