package br.com.condominioalerta.medicao.unidade.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ArquivoCargaUnidadeService {

	public static void main(String[] args) {
		BufferedReader br = null;

		try {
			String linha;
			
			br = new BufferedReader(new FileReader("/data/condominio/moradores-privilege.csv"));
			while ((linha = br.readLine()) != null) {
				String sql;
				StringTokenizer tokenizer = new StringTokenizer(linha, ";");
				while (tokenizer.hasMoreElements()) {
					String unidade = tokenizer.nextElement().toString();
					String nome = tokenizer.nextElement().toString();
					String email = tokenizer.nextElement().toString();
					
					sql = "UPDATE unidade SET nm_responsavel ='" + nome + "', cd_email_responsavel='" + email + "' WHERE cd_unidade='" + unidade + "';";
					System.out.println(sql);
				}
			}
			System.out.println("Tokenizer FINALIZADO");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}