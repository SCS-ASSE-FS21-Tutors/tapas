package ch.unisg.tapasexecutorpool.integration;

import ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb.ExecutorPersistenceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TapasExecutorPoolApplicationTests {

	@MockBean
	ExecutorPersistenceAdapter persistenceAdapter;

	/**
	 * This test tests if the Spring application actually starts up.
	 * Yes, it empty. The test will fail if for any reason Spring cannot
	 * initialize the application context. (e.g. Bean misconfiguration)
	 */
	@Test
	void contextLoads() {
	}

}
