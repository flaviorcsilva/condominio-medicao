package br.com.iupi.condominio.medicao.leitura.modelo;

public class Consumo {

	private Leitura leituraAnterior;
	private Leitura leituraAtual;
	private Double preco;

	public Consumo(Leitura leituraAnterior, Leitura leituraAtual) {
		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
	}

	public Integer getMedido() {
		return leituraAtual.getLeitura() - leituraAnterior.getLeitura();
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getTotalAPagar() {
		return getMedido() * preco;
	}
}
