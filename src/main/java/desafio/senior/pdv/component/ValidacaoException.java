package desafio.senior.pdv.component;

public class ValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = -2743859802840604405L;
	
	private final boolean erroTratado;
	
	private ValidacaoException(String mensagem, boolean erroTratado) {
		super(mensagem);
		this.erroTratado = erroTratado;
	}
	
	static ValidacaoException paraErroTratado() {
		return new ValidacaoException("erro.validacao.tratado", true);
	}
	
	public boolean isErroTratado() {
		return this.erroTratado;
	}
	
}
