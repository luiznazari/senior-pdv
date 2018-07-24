package desafio.senior.pdv;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Abstração com as configurações para testes de integração com banco de dados.
 * Sempre limpa a base antes da execução do teste.
 * 
 * @author Luiz Felipe Nazari
 */
@SpringBootTest(classes = { SeniorPdvApplication.class })
public abstract class TesteIntegracao extends TesteUnitario {
	
	@Autowired
	private Flyway flyway;
	
	@Before
	public final void initTesteIntegracaoBd() {
		flyway.clean();
		flyway.migrate();
	}
	
}
