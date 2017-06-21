package br.com.condominioalerta.medicao.comum.ws;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.openejb.OpenEjbContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ContainerTest {

	protected static Context context;

	@BeforeClass
	public static void start() throws NamingException {
		Properties properties = new Properties();

		properties.setProperty(OpenEjbContainer.OPENEJB_EMBEDDED_REMOTABLE, "true");
		properties.put("condominioDS", "new://Resource?type=DataSource");
		properties.put("condominioDS.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("condominioDS.JdbcUrl", "jdbc:hsqldb:mem:webservicedb");
		properties.put("condominioDS.UserName", "sa");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");

		context = EJBContainer.createEJBContainer(properties).getContext();
	}

	@AfterClass
	public static void shutdown() throws NamingException {
		if (context != null) {
			context.close();
		}
	}
}
