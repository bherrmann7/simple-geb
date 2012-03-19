

# Geb Talk 

- me slide

- Intro and getting started
    - What you are going to get out of this talk
    - dangers - unpredictable / unrepeatable (network/cpu/complexity)

- Whats the problem?
    - Web browsers are complicated engines.
    - HTML, CSS, Javascript, Image Async, file caching
   
- Browsers have Console
    - read from DOM
    - fire events (click, key)
    - what if you telneted into the console and wrote tests

- Picture (Browser w/console)
- Picture (window.title)
 
- WebDriver
   http://dvcs.w3.org/hg/webdriver/raw-file/tip/webdriver-spec.html

- What is Geb?
    - docs http://gebish.org
    - testing in groovy
    - cross browser
    - jquery like API
    - page objects
    - testing support: Spock, JUnit, TestNG, EasyB and Cucumber (via Cuke4Duke)
    - build support: gradle, grails, maven

- simple demo (no page objects)
    - git clone
    - gradlew test
    - gradlew intellij  (or gradlew eclipse)
    - add -Dgeb.build.reportsDir=/tmp to run unit test in IDE
    - js.exec('alert("Hi mom")')
    - print js.'document.title'
    - print js.'window.location.href'
    - print js.someVar = 7

- jquery like
    - select elements
        -  $(selector, index, map )
        -  $('a')
        -  $('td a')
        -  $('div.foo a')
        -  $('div', class: 'foo')
        -  $('a', text: 'click me please')
    - then do what with element?
        -  println $('input.username').value
        -  ... .click()
        - ... .text()
    - gotcha   http://cnn.com $('a') -> 10 minutes
    - waitFor


- Page Objects

         import geb.Browser

         Browser.drive {
             go "http://google.com"
             $("input[name=q]").value() = "Chuck Norris"
             $("input[value=Google Search]").click()
             assert $("li.g", 0).get("a.l").text() ==~ /Chuck/
         }

    -vs-

        import geb.*

        class GoogleHomePage extends Page {
            static url = "http://google.com"
            static at = { title == "Google" }
            static content = {
                searchField { $("input[name=q]") }
                searchButton(to: GoogleResultsPage) { $("input[value=Google Search]") }
            }
        }

        class GoogleResultsPage extends Page {
            static at = { title.endsWith("Google Search") }
            static content = {
                results { $("li.g") }
                result { index -> results[index] }
                resultLink { index -> result(index).find("a.l") }
            }
        }

        // Now the script
        Browser.drive {
            to GoogleHomePage
            searchField.value = "Chuck Norris"
            searchButton.click()
            assert at(GoogleResultsPage)
            assert resultLink(0).text() ==~ /Chuck/
        }