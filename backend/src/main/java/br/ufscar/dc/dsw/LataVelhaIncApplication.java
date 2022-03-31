package br.ufscar.dc.dsw;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class LataVelhaIncApplication {

	private static final int TAM = 13;

	public static void main(String[] args) {
		SpringApplication.run(LataVelhaIncApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, IClienteDAO clienteDAO, ILojaDAO lojaDAO,
			IVeiculoDAO veiculoDAO, IPropostaDAO propostaDAO, BCryptPasswordEncoder encoder) {
		return (args) -> {

			Usuario u1 = new Usuario();
			u1.setUsername("admin");
			u1.setPassword(encoder.encode("admin"));
			u1.setRole("ROLE_ADMIN");
			u1.setEnabled(true);
			usuarioDAO.save(u1);

			Cliente c1 = new Cliente();
			c1.setUsername("jorjao@gmail.com");
			c1.setPassword(encoder.encode("123"));
			c1.setNome("jorge");
			c1.setCPF("012.345.678-90");
			c1.setTelefone("8678-2462");
			c1.setSexo("M");
			c1.setNascimento("13/03/1995");
			c1.setRole("ROLE_USER");
			c1.setEnabled(true);
			clienteDAO.save(c1);

			Cliente c2 = new Cliente();
			c2.setUsername("irto@gmail.com");
			c2.setPassword(encoder.encode("123"));
			c2.setNome("Airton Cena");
			c2.setCPF("985.849.614-10");
			c2.setTelefone("3566-6456");
			c2.setSexo("M");
			c2.setNascimento("23/08/2001");
			c2.setRole("ROLE_USER");
			c2.setEnabled(true);
			clienteDAO.save(c2);

			Cliente c3 = new Cliente();
			c3.setUsername("mw@gmail.com");
			c3.setPassword(encoder.encode("123"));
			c3.setNome("Michael Wazowski");
			c3.setCPF("367.318.380-04");
			c3.setTelefone("7647-1354");
			c3.setSexo("M");
			c3.setNascimento("28/01/1975");
			c3.setRole("ROLE_USER");
			c3.setEnabled(true);
			clienteDAO.save(c3);

			Loja l1 = new Loja();
			l1.setUsername("carros@gmail.com");
			l1.setPassword(encoder.encode("123"));
			l1.setCNPJ("55.789.390/0008-99");
			l1.setNome("Carros e Ventiladores SA");
			l1.setDescricao("Sim, carros e ventiladores");
			l1.setRole("ROLE_LOJA");
			l1.setEnabled(true);
			lojaDAO.save(l1);

			Loja l2 = new Loja();
			l2.setUsername("ferro@gmail.com");
			l2.setPassword(encoder.encode("123"));
			l2.setCNPJ("71.150.470/0001-40");
			l2.setNome("Ferro velho do Maicao");
			l2.setDescricao("Desde 1934 fazendo história");
			l2.setRole("ROLE_LOJA");
			l2.setEnabled(true);
			lojaDAO.save(l2);

			Loja l3 = new Loja();
			l3.setUsername("teslabr@gmail.com");
			l3.setPassword(encoder.encode("123"));
			l3.setCNPJ("32.106.536/0001-82");
			l3.setNome("Tesla saocarlense");
			l3.setDescricao("Não vendemos carros elétricos nem foguetes");
			l3.setRole("ROLE_LOJA");
			l3.setEnabled(true);
			lojaDAO.save(l3);

			Veiculo v[] = new Veiculo[TAM];

			String placa[] = { "SADF5645", "JRGB6246", "ERBB8768", "WGBW3525", "YMRM7457", "SFDC2466", "MTMT8568",
					"WERG4677", "QAVE2656", "ITRY2555", "23QF2245", "MYRM6567", "RBWT7473" };

			String modelo[] = { "Corsa", "Fusca", "Uno", "Palio", "Monza", "Kombi", "Corolla", "Ka", "Focus", "F50",
					"R8", "RX-7", "Camaro" };

			String chassi[] = { "28187273484628616", "68909064904628616", "28735926214628616", "69545597444628616",
					"98928467054628616", "46228703864628616", "73411000224628616", "85925899194628616",
					"75151258384628616", "51098812944628616", "00064526462861645", "63462076624628616",
					"96435284874628616", "93910031984628616", };

			Integer ano[] = { 2005, 1997, 2007, 2006, 2001, 2000, 1988, 1987, 1999, 1999, 1996, 2007, 2006 };

			Integer quilometragem[] = { 546762456, 7254727, 2457724, 25472475, 2547457, 2547457, 8586, 2575247, 856858,
					1346164, 89598, 24575, 9856789 };

			String descricao = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consequatur quas est, nesciunt sit nisi non repudiandae nostrum provident maxime itaque dignissimos nihil saepe temporibus consequuntur rerum laudantium sint praesentium ullam.";

			Double preco[] = { 5999.99, 7999.99, 9999.99, 3999.99, 5999.99, 9999.99, 62999.99, 8999.99, 9999.99,
					5999.99, 3999.99, 58999.99, 54999.99 };

			for (int i = 0; i < TAM; i++) {
				v[i] = new Veiculo();
				v[i].setPlaca(placa[i]);
				v[i].setModelo(modelo[i]);
				v[i].setChassi(chassi[i]);
				v[i].setAno(ano[i]);
				v[i].setDescricao(descricao);
				v[i].setQuilometragem(quilometragem[i]);
				v[i].setPreco(BigDecimal.valueOf(preco[i]));
				if (i % 3 == 0)
					v[i].setLoja(l3);
				else if (i % 2 == 0)
					v[i].setLoja(l2);
				else
					v[i].setLoja(l1);
				veiculoDAO.save(v[i]);
			}

			Proposta p1 = new Proposta();
			p1.setValor(BigDecimal.valueOf(4000));
			p1.setParcelamento("12 vezes com juros");
			p1.setData("20/09/21");
			p1.setEstado("ABERTO");
			p1.setLoja(l1);
			p1.setCliente(c1);
			p1.setVeiculo(v[1]);
			propostaDAO.save(p1);

			Proposta p2 = new Proposta();
			p2.setValor(BigDecimal.valueOf(10000));
			p2.setParcelamento("a vista");
			p2.setData("23/10/21");
			p2.setEstado("ACEITO");
			p2.setLoja(l2);
			p2.setCliente(c1);
			p2.setVeiculo(v[2]);
			propostaDAO.save(p1);

			Proposta p3 = new Proposta();
			p3.setValor(BigDecimal.valueOf(2000));
			p3.setParcelamento("30 vezes sem juros");
			p3.setData("30/05/21");
			p3.setEstado("NEGADO");
			p3.setLoja(l1);
			p3.setCliente(c1);
			p3.setVeiculo(v[1]);
			propostaDAO.save(p3);
		};
	}
}
