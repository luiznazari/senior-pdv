package desafio.senior.pdv.component;

import java.util.Set;
import java.util.function.Consumer;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import desafio.senior.pdv.utils.Alertas;

@Component
public class Validador {
	
	@Autowired
	private Validator validator;
	
	public ResultadoValidacao validate(Object object) {
		Set<ConstraintViolation<Object>> errosValidacao = validator.validate(object);
		return new ResultadoValidacao(errosValidacao);
	}
	
	public static class ResultadoValidacao {
		
		Set<ConstraintViolation<Object>> errosValidacao;
		
		private ResultadoValidacao(Set<ConstraintViolation<Object>> errosValidacao) {
			this.errosValidacao = errosValidacao;
		}
		
		public Set<ConstraintViolation<Object>> getErrosValidacao() {
			return this.errosValidacao;
		}
		
		public boolean isPossuiErros() {
			return !CollectionUtils.isEmpty(this.errosValidacao);
		}
		
		public void quandoErro(Consumer<Set<ConstraintViolation<Object>>> consumer) {
			if (this.isPossuiErros()) {
				consumer.accept(this.errosValidacao);
			}
		}
		
		public void quandoErroMostraAlerta() {
			this.quandoErro(erros -> {
				StringBuilder sb = new StringBuilder();
				erros.forEach(erro -> sb.append(erro.getMessage()).append('\n'));
				Alertas.erro(sb.toString());
				
				throw ValidacaoException.paraErroTratado();
			});
		}
		
	}
	
}
