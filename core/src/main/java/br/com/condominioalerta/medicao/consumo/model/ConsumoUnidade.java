package br.com.condominioalerta.medicao.consumo.model;

import br.com.condominioalerta.medicao.leitura.model.Leitura;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;

public class ConsumoUnidade {

	private Leitura leituraAnterior;
	private Leitura leituraAtual;
	private Double valorM3;

	public ConsumoUnidade(Leitura leituraAnterior, Leitura leituraAtual, Double valorM3) {
		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
		this.valorM3 = valorM3;
	}

	public UnidadeConsumidora getUnidade() {
		return leituraAtual.getMedidor().getUnidadeConsumidora();
	}

	public Leitura getLeituraAnterior() {
		return leituraAnterior;
	}

	public Leitura getLeituraAtual() {
		return leituraAtual;
	}

	public TipoMedicao getTipoMedicao() {
		if (leituraAtual != null && leituraAtual.getMedidor() != null) {
			return leituraAtual.getMedidor().getTipo();
		} else {
			return null;
		}
	}

	public Double getValorM3() {
		return valorM3;
	}

	public void setValorM3(Double valorM3) {
		this.valorM3 = valorM3;
	}

	public Integer getMedido() {
		if (leituraAtual != null && leituraAnterior != null) {
			return (leituraAtual.getMedido() - leituraAnterior.getMedido());
		}

		return 0;
	}

	public Double getValor() {
		return getMedido() * valorM3 / getFator();
	}

	public Integer getFator() {
		if (leituraAtual != null && leituraAtual.getMedidor() != null) {
			return leituraAtual.getMedidor().getFator();
		} else {
			return 1;
		}
	}
}