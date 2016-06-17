package br.com.iupi.condominio.medicao.consumo.modelo;

import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;

public class ConsumoUnidade {

	private Leitura leituraAnterior;
	private Leitura leituraAtual;
	private Double valorM3;

	public ConsumoUnidade(Leitura leituraAnterior, Leitura leituraAtual, Double valorM3) {
		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
		this.valorM3 = valorM3;
	}

	public Leitura getLeituraAnterior() {
		return leituraAnterior;
	}

	public Leitura getLeituraAtual() {
		return leituraAtual;
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