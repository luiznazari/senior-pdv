package desafio.senior.pdv;

public abstract class Constantes {
	
	public static final String TITULO_APLICACAO = "Senior PDV";
	public static final String EDITAR_PRODUTO = "editar.produto";
	public static final String CANCELAR_VENDA = "cancelar.venda";
	public static final String SIM = "sim";
	public static final String NAO = "nao";
	
	public static final String ERRO_INESPERADO = "erro.inesperado";
	public static final String ERRO_SENHA_INCORRETA = "erro.senha.incorreta";
	public static final String ERRO_LOGIN_OU_SENHA_VAZIO = "erro.login.senha.vazio";
	public static final String ERRO_USUARIO_NAO_ENCONTRADO = "erro.usuario.nao.encontrado";
	public static final String ERRO_VENDA_JA_CONTEM_PRODUTO = "erro.venda.ja.contem.produto";
	public static final String ERRO_FINALIZAR_VENDA_NAO_CRIADA = "erro.finalizar.venda.nao.criada";
	public static final String ERRO_NENHUM_PRODUTO_ENCONTRADO_CODIGO = "erro.produto.nao.encontrado.codigo";
	
	private Constantes() {}
	
	public abstract static class FXML {
		
		private FXML() {}
		
		private static final String TEMPLATE_CAMINHO = "/fxml/%s.fxml";
		
		public static String getCaminho(String nomeArquivoFxml) {
			return String.format(TEMPLATE_CAMINHO, nomeArquivoFxml);
		}
		
	}
	
	public abstract static class CSS {
		
		private CSS() {}
		
		private static final String CAMINHO = "/css/";
		public static final String FEXTILE = CAMINHO + "fextile.css";
		public static final String PRINCIPAL = CAMINHO + "senior.css";
		
	}
	
	public abstract static class IMG {
		
		private IMG() {}
		
		private static final String CAMINHO = "/img/";
		public static final String ICONE = CAMINHO + "favicon.ico";
		public static final String LOGO_BRANCO = CAMINHO + "logo-branco.png";
		
	}
	
}
