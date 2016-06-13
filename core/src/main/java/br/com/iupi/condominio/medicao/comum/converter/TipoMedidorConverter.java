package br.com.iupi.condominio.medicao.comum.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;

@Converter(autoApply = true)
public class TipoMedidorConverter implements AttributeConverter<TipoMedidor, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TipoMedidor attribute) {
		switch (attribute) {
		case AGUA_FRIA:
			return 1;
		case AGUA_QUENTE:
			return 2;
		case GAS:
			return 3;
		default:
			throw new IllegalArgumentException("Unknown" + attribute);
		}
	}

	@Override
	public TipoMedidor convertToEntityAttribute(Integer value) {
		switch (value) {
		case 1:
			return TipoMedidor.AGUA_FRIA;
		case 2:
			return TipoMedidor.AGUA_QUENTE;
		case 3:
			return TipoMedidor.GAS;
		default:
			throw new IllegalArgumentException("Unknown" + value);
		}
	}
}