# Geb Talk 


- Intro and getting started
    - Me: Bob Herrmann, Web Developer @ Innocentive.com
    - What you are going to get out of this talk
         - What is geb, how to code it, how to run it

- Whats the problem?
    - Testing web applications is hard
    - Web browsers are complicated engines (HTML, CSS, images, Javascript)
    - A web page is a horrid scary mess

- Whats the solution?  
    - talking with the browser (aka like the javascript console)
        - reading / writting to the DOM  
        - fire events (click, key)
    - what if you telneted into the javascript console and wrote tests?

- Picture (Browser w/console)
- Picture (window.title)
 
- WebDriver
   http://dvcs.w3.org/hg/webdriver/raw-file/tip/webdriver-spec.html

- Dangers of driving a browser (can make Integration tests look reliable)
    - browsers behave stragely/inconsistantly
    - response times can vary
    - easy to sink lots of time into automating web browser
    - wobbly tempermental tests

- What is Geb?
    - docs http://gebish.org
    - testing in groovy
    - cross browser
    - jquery like API
    - page objects
    - testing support: Spock, JUnit, TestNG, EasyB and Cucumber
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
