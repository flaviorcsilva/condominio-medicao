package br.com.iupi.condominio.medicao.comum.execao;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Conjunto de mensagens de erro do sistema. Elas devem ser usadas nas exceções
 * específicas do sistema. Por exemplo,
 * 
 * <pre>
 * throw new NegocioException(AbstractMensagem.LOGIN_SENHA_INVALIDOS);
 * throw new UsuarioNaoAutorizadoException(AbstractMensagem.ACESSO_NAO_AUTORIZADO);
 * </pre>
 * 
 * <p>
 * Os elementos da enumeração mapeiam para chaves do arquivo de propriedades
 * localizado em <b>mensagens/mensagensDeErro.properties</b>. Sempre que um novo
 * elemento foi incluído neste <code>enum</code>, a mensagem correspondente deve
 * ser incluída naquele arquivo.
 */
public enum Mensagem {

	/* Mensagens Genéricas */
	DATA_ERRO_CONVERSAO_FORMATO_DATA("data.erro_conversao_formato_data"), //
	DATA_ERRO_CONVERSAO_FORMATO_DATA_HORA("data.erro_conversao_formato_data_hora"), //
	DATA_ERRO_CONVERSAO_FORMATO_HORA("data.erro_conversao_formato_hora"), //
	DATA_ERRO_CONVERSAO_MES_ANO("data.erro_conversao_mes_ano"), //
	CAMPOS_ID_OBRIGATORIO("campos.id_obrigatorio"), //
	CAMPOS_OBRIGATORIOS_NAO_PREENCHIDOS("campos.obrigatorios_nao_preenchidos"), //
	PARAMETRO_OBRIGATORIO("infra.parametro_obrigatorio"), //
	ARQUIVO_ERRO_AO_ACESSAR("arquivo.erro_ao_acessar"), //
	TESTE_CHAVE_INEXISTENTE("nao_existe_essa_chave"),

	/* Leitura */
	LEITURA_UNIDADE_OBRIGATORIA("leitura.unidade_obrigatoria"), //
	LEITURA_TIPO_INVALIDO("leitura.tipo_invalido"), //
	LEITURA_DATA_OBRIGATORIA("leitura.data_obrigatoria"), //
	LEITURA_VALOR_MEDIDO_OBRIGATORIO("leitura.valor_medido_obrigatorio"), //
	LEITURA_DATA_JA_EXISTE_MEDICAO("leitura.data_ja_existe_medicao"), //
	LEITURA_NAO_EXISTENTE("leitura.nao_existente"), //

	/* Condominio */
	CONDOMINIO_NAO_EXISTENTE("condominio.nao_existente"), //

	/* Consumo Condomínio */
	CONSUMO_NAO_EXISTENTE("consumo.nao_existente"), //

	/* UnidadeConsumidora */
	UNIDADE_NAO_EXISTENTE("unidade.nao_existente"), //
	UNIDADE_NAO_EXISTE_UNIDADES("unidade.nao_existe_unidades"), //

	/* Medidor */
	MEDIDOR_NAO_EXISTENTE("medidor.nao_existente");

	private static final String NAO_EXISTE_CHAVE = "Acrescente a chave '%s' no arquivo src/main/resources/mensagens/"
			+ "mensagensDeErro.properties";

	private ResourceBundle resourceBundle;
	private String chave;

	private Mensagem(String chave) {
		this.resourceBundle = ResourceBundle.getBundle("mensagens/mensagensDeErro");
		this.chave = chave;
	}

	/**
	 * Obtém mensagem correspondente ao elemento da enumeração.
	 * 
	 * @return AbstractMensagem do elemento da enumeração.
	 */
	public String getMensagem() {
		try {
			return resourceBundle.getString(chave);
		} catch (MissingResourceException e) {
			return String.format(NAO_EXISTE_CHAVE, chave);
		}
	}

	/**
	 * Obtém mensagem correspondente ao elemento da enumeração substituindo os
	 * valores dos <code>argumentos</code>.
	 * 
	 * @param argumentos
	 *            Informações para serem substituídas na mensagem
	 * @return AbstractMensagem com os valores substituídos
	 */
	public String getMensagem(Object... argumentos) {
		try {
			return String.format(resourceBundle.getString(chave), argumentos);
		} catch (MissingResourceException e) {
			return String.format(NAO_EXISTE_CHAVE, chave);
		}
	}
}