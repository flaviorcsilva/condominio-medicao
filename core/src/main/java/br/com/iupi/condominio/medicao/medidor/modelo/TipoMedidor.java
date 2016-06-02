package br.com.iupi.condominio.medicao.medidor.modelo;

public enum TipoMedidor {
	
	AGUA_FRIA(1), AGUA_QUENTE(2), GAS(3);
	
	private Integer id;
	
	private TipoMedidor(Integer id) {
		this.id = id;
	}
	
	public static TipoMedidor get (Integer id) {
		TipoMedidor tipo = null;
		for (TipoMedidor tipoMedidor : TipoMedidor.values()) {
			if(tipoMedidor.getId().equals(id)){
				tipo = tipoMedidor;
				
				break;
			}
		}
		
		return tipo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}