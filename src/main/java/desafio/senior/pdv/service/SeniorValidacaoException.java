package desafio.senior.pdv.service;

public class SeniorValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = -4743103012913015105L;
	
	public SeniorValidacaoException(String chave) {
		super(chave);
	}
	
	public SeniorValidacaoException(String chave, Throwable causa) {
		super(chave, causa);
	}
	
}
