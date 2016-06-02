package br.com.iupi.condominio.medicao.comum.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO Genérico que pode ser extendido pelos demais DAOs específicos para cada
 * entidade persistente. Sua implementação é baseado em {@HashMap}
 * 
 * @param <PK>
 *            - Chave primária da entidade
 * @param <T>
 *            - Entidade persistente
 * 
 * @author Abril/2014: Flávio Roberto Cruz Silva
 *         <DD>
 */
public class GenericMapDAO<PK extends Serializable, T extends Entidade<PK>> {

	protected Map<PK, T> hashMap = new HashMap<PK, T>();

	/**
	 * Insere uma entidade ainda não persistida, <tt>entidade</tt>, no banco de
	 * dados.
	 * 
	 * @param entity
	 *            Objeto a ser inserido.
	 */
	public void insere(T entity) {
		hashMap.put(entity.getId(), entity);
	}

	/**
	 * Atualiza a <tt>entidade</tt> no banco de dados.
	 * 
	 * @param entity
	 *            Objeto ser atualizado.
	 */
	public void atualiza(T entity) {
		hashMap.put(entity.getId(), entity);
	}

	/**
	 * Apaga fisicamente um determinado objeto do banco de dados.
	 * 
	 * @param entity
	 *            Objeto a ser excluído.
	 */
	public void remove(T entity) {
		hashMap.remove(entity.getId());
	}

	/**
	 * Apaga fisicamente todos os objetos do banco de dados.
	 * 
	 * @return Quantidade de registros excluídos.
	 */
	public int removeTodos() {
		int size = hashMap.size();
		hashMap.clear();

		return size;
	}

	/**
	 * Consulta todos os objetos desta entidade na base de dados.
	 * 
	 * @return Lista de objetos persistidos em banco.
	 */
	public List<T> consultaTodos() {
		return new ArrayList<T>(hashMap.values());
	}

	/**
	 * Consulta um determinado objeto por sua chave primária.
	 * 
	 * @param id
	 *            Chave primária do objeto a ser consultado.
	 * @return Objeto persistido.
	 */
	public T consultaPorId(PK id) {
		if (hashMap.containsKey(id)) {
			return hashMap.get(id);
		}

		return null;
	}
}
