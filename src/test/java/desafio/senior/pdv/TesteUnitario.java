package desafio.senior.pdv;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class TesteUnitario {
	
	@Before
	public void inicializarMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
}
