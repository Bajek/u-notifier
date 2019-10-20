package pl.ubytes.unotifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UNotifierApplicationTests {

	@Test
	public void contextLoads() {
		Assert.assertTrue("Context should be loaded", true);
	}

}
