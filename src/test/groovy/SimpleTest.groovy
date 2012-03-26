import geb.*
import geb.junit4.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

// To run from in the IDE add -Dgeb.build.reportsDir=/tmp to the run profile vm options

@RunWith(JUnit4)
class SimpleTest extends GebReportingTest {

    @Test
    void testSimple() {
        go 'http://localhost:8080/s/login'

        assert $('title').text() == 'Taco Login'

        $('input[name=username]').value('bob')
        $('input[name=password]').value('taco4me')
        $('input[type=submit]').click()

        assert title == 'Welcome back'
        assert $('img').size() == 2
    }

}