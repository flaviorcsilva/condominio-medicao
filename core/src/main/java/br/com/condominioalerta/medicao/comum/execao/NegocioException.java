package br.com.condominioalerta.medicao.comum.execao;

/**
 * Exceção de negócio do sistema.
 */
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Mensagem mensagem;

	/**
	 * Constrói uma nova exceção com uma mensagem de erro.
	 * 
	 * @param mensagem
	 *            Mensagem de erro.
	 */
	public NegocioException(Mensagem mensagem) {
		super(mensagem.getMensagem());
		this.mensagem = mensagem;
	}

	/**
	 * Constrói uma nova exceção com uma mensagem de erro substituída com os
	 * valores de <code>argumentos</code>.
	 * 
	 * @param mensagem
	 *            Mensagem de erro
	 * @param argumentos
	 *            Valores para substituir na mensagem de erro
	 */
	public NegocioException(Mensagem mensagem, Object... argumentos) {
		super(mensagem.getMensagem(argumentos));
		this.mensagem = mensagem;
	}

	/**
	 * Constrói uma nova exceção indicando uma mensagem de erro personalizada e
	 * a causa do erro.
	 * 
	 * @param mensagem
	 *            Mensagem de erro.
	 * @param causa
	 *            Causa do erro.
	 */
	public NegocioException(Mensagem mensagem, Throwable causa) {
		super(mensagem.getMensagem(), causa);
		this.mensagem = mensagem;
	}

	/**
	 * Método para retorna o objeto {@link Mensagem} retornado pela Exception.
	 * 
	 * @return {@link Mensagem} lançada.
	 */
	public Mensagem getMensagem() {
		return this.mensagem;
	}
}
