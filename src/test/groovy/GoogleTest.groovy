import geb.*
import geb.junit4.*
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4)
class GoogleTest extends GebReportingTest {
	
	@Test 
	void helloGoogle() {
        go 'http://google.com'
		

	}
	
}