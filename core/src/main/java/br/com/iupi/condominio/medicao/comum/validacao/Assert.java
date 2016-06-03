package br.com.iupi.condominio.medicao.comum.validacao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;

/**
 * Classe utilitária para auxiliar validação de parâmetros, baseado no conceito
 * de programação por contrato. É útil para identificar erros de programação
 * mais rapidamente em tempo de execução.
 * 
 * <p>
 * Por exeplo, se o contrato de um método público diz que ele não permite
 * parâmetros igual a <code>null</code>, Assert pode ser usado para validar este
 * contrato. Se isto acontecer, há uma violação do contrato e, assim, protege a
 * classe.
 * 
 * <p>
 * Se um valor de parâmetro is dito inválido, um
 * {@link IllegalArgumentException} é lançada. Por exemplo:
 * 
 * <pre>
 * Assert.notNull(obj, &quot;O parâmetro não pode ser null&quot;);
 * </pre>
 * 
 * <p>
 * A implementação dos métodos desta classe são baseados na classe
 * <code>org.springframework.util.Assert</code>.
 * 
 */
public class Assert {

	protected Assert() {
		// utilitário que não deve ser instanciado.
	}

	/**
	 * Verifica que um objeto é diferente de <code>null</code>.
	 * 
	 * <pre>
	 * Assert.notNull(obj);
	 * </pre>
	 * 
	 * @param objeto
	 *            o objeto a ser verificado
	 * @throws IllegalArgumentException
	 *             se o objeto for <code>null</code>
	 */
	public static void notNull(Object objeto) {
		notNull(objeto, Mensagem.PARAMETRO_OBRIGATORIO);
	}

	/**
	 * Verifica que um objeto é diferente de <code>null</code>.
	 * 
	 * <pre>
	 * Assert.notNull(obj, &quot;Este objeto não pode ser null&quot;);
	 * </pre>
	 * 
	 * @param objeto
	 *            o objeto a ser verificado
	 * @param mensagem
	 *            a mensagem da exceção caso a validação falhe.
	 * @throws NegocioException
	 *             se o objeto for <code>null</code>
	 */
	public static void notNull(Object objeto, Mensagem mensagem) {
		if (objeto == null) {
			throw new NegocioException(mensagem);
		}
	}

	/**
	 * Verifica que todos os objetos de uma coleção de objetos é diferente de
	 * <code>null</code>.
	 * 
	 * <pre>
	 * Assert.notNull(obj1, obj2, obj3);
	 * </pre>
	 * 
	 * @param objetos
	 *            coleção de objetos a ser verificados
	 * @throws IllegalArgumentException
	 *             se pelo menos um dos objetos da coleção for <code>null</code>
	 */
	public static void notNull(Object... objetos) {
		for (Object objeto : objetos) {
			notNull(objeto);
		}
	}

	/**
	 * Verifica que a string preenchido não é <code>null</code> ou vazia.
	 * 
	 * @param objeto
	 *            string a ser verificada
	 * @param mensagem
	 *            mensagem de erro se a string não atender às condições
	 * @throws NegocioException
	 *             quando string inválida de acordo com os critérios.
	 */
	public static void notBlank(String objeto, Mensagem mensagem) {
		if (StringUtils.isBlank(objeto)) {
			throw new NegocioException(mensagem);
		}
	}

	/**
	 * Verifica se a data passada como pametro é maior ou igual a data de hoje.
	 * 
	 * @param dataParaValidacao
	 *            {@link Date}
	 * @param mensagem
	 *            mensagem de erro se a data não atender às condições
	 * @throws NegocioException
	 *             quando a data for inválida.
	 */
	public static void dateAfter(Date dataParaValidacao, Mensagem mensagem) {

		Integer dataAtual = Integer.parseInt(new SimpleDateFormat("yyyyMMdd")
				.format(new Date()));
		Integer dataAvaliada = Integer
				.parseInt(new SimpleDateFormat("yyyyMMdd")
						.format(dataParaValidacao));

		if (dataAtual > dataAvaliada) {
			throw new NegocioException(mensagem);
		}
	}

	/**
	 * Verifica se a lista está vazia
	 * 
	 * @param lista
	 *            Lista a ser verificada
	 * @param mensagem
	 *            Mensagem de erro se a lista estiver vazia
	 * @throws NegocioException
	 *             Se a lista for vazia
	 */
	public static void notEmpty(List<?> lista, Mensagem mensagem) {
		if (lista.isEmpty()) {
			throw new NegocioException(mensagem);
		}
	}

	/**
	 * Verifica se a expressão é verdadeira.
	 * 
	 * @param expressao
	 *            Expressão a ser verificada
	 * @param mensagem
	 *            Mensagem de erro se a expressão for falsa
	 * @throws NegocioException
	 *             Se a expressão for falsa
	 */
	public static void isTrue(Boolean expressao, Mensagem mensagem) {
		if (!expressao) {
			throw new NegocioException(mensagem);
		}
	}

	/**
	 * Verifica se o objeto é <code>null</code>.
	 * 
	 * @param objeto
	 *            Objeto a ser verificado
	 * @param mensagem
	 *            Mensagem de erro se objeto não for null.
	 * @throws NegocioException
	 *             Se objeto não for null.
	 */
	public static void isNull(Object objeto, Mensagem mensagem) {
		if (objeto != null) {
			throw new NegocioException(mensagem);
		}
	}
}
