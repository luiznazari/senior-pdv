package desafio.senior.pdv.config;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.component.ValidacaoException;
import desafio.senior.pdv.service.SeniorValidacaoException;
import desafio.senior.pdv.utils.Alertas;

public class ExceptionHandlerPadrao implements UncaughtExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(ExceptionHandlerPadrao.class);
	
	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		if (throwable instanceof ValidacaoException) {
			this.tratarErroValidacao(( ValidacaoException ) throwable);
			
		} else if (throwable instanceof SeniorValidacaoException) {
			this.tratarErroValidacao(( SeniorValidacaoException ) throwable);
			
		} else {
			this.tratarErroPadrao(throwable);
		}
	}
	
	private void tratarErroValidacao(ValidacaoException validacaoException) {
		if (validacaoException.isErroTratado()) {
			log.info("Exceção suprimida: {}\nMensagem: {}", validacaoException, validacaoException.getMessage());
		} else {
			Alertas.erro(validacaoException.getMessage());
		}
	}
	
	private void tratarErroValidacao(SeniorValidacaoException seniorValidacaoException) {
		String erro = NullSafeResourceBundle.getInstance().getString(seniorValidacaoException.getMessage());
		Alertas.erro(erro);
	}
	
	private void tratarErroPadrao(Throwable throwable) {
		log.error(throwable.getMessage(), throwable);
		Alertas.erro(Constantes.ERRO_INESPERADO);
	}
	
}
