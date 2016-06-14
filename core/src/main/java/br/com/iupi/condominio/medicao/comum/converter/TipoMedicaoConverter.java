package br.com.iupi.condominio.medicao.comum.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;

@Converter(autoApply = true)
public class TipoMedicaoConverter implements AttributeConverter<TipoMedicao, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TipoMedicao attribute) {
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
	public TipoMedicao convertToEntityAttribute(Integer value) {
		switch (value) {
		case 1:
			return TipoMedicao.AGUA_FRIA;
		case 2:
			return TipoMedicao.AGUA_QUENTE;
		case 3:
			return TipoMedicao.GAS;
		default:
			throw new IllegalArgumentException("Unknown" + value);
		}
	}
}