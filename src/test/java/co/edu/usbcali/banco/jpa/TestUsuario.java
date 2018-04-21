package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.modelo.Usuario;
import co.edu.usbcali.banco.modelo.TipoUsuario;

class TestUsuario {
	
	private final static Logger log=LoggerFactory.getLogger(TestUsuario.class);
	
	static EntityManagerFactory entityManagerFactory=null;
	static EntityManager entityManager=null;
	
	BigDecimal identificacionU=new BigDecimal(1102);
	String usuarioUsu= new String("ElJhonny");
	
	
	@Test
	@DisplayName("ConsultarUsuario")
	void etest() {
		
		assertNotNull(entityManager, "El entitymanager es nulo");
		
		String jpql="SELECT usu FROM Usuario usu";
		
		List<Usuario> losUsuarios= entityManager.createQuery(jpql).getResultList();
		
		losUsuarios.forEach(usuario->{
			log.info("IDENTIFICACION:"+usuario.getIdentificacion());
			log.info("NOMBRE:"+usuario.getNombre());
		});
		
		/*
		for (Usuario usuario : losUsuarios) {
			log.info("IDENTIFICACION:"+usuario.getIdentificacion());
			log.info("NOMBRE:"+usuario.getNombre());
		}
		*/
		
		
	}
	
	@Test
	@DisplayName("BorrarUsuario")
	void dtest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, usuarioUsu);
		assertNotNull(usuario, "El usuario no existe");
		
		entityManager.getTransaction().begin();
			entityManager.remove(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ModificarUsuario")
	void ctest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, usuarioUsu);
		assertNotNull(usuario, "El usuario no existe");
		
		usuario.setActivo('N');
		usuario.setClave("d526dcp");
		usuario.setNombre("John Doe");
		usuario.setIdentificacion(identificacionU);
		/*
		cliente.setDireccion("Avenida Siempre Viva 123 C");
		cliente.setEmail("hsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 5555");*/
		
		//TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, new Long(2));
		TipoUsuario tipoUsuario=entityManager.find(TipoUsuario.class, new Long(2));
		assertNotNull(tipoUsuario,"El tipo de usuario no existe");
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
			entityManager.merge(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ConsultarUsuarioPorUsuario")
	void btest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, usuarioUsu);
		assertNotNull(usuario, "El usuario ya existe");
		
		log.info("IDENTIFICACION:"+usuario.getIdentificacion());
		log.info("NOMBRE:"+usuario.getNombre());
	}
	
	
	
	@Test
	@DisplayName("CreaUsuario")
	void atest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, usuarioUsu);
		assertNull(usuario, "El usuario ya existe");
		usuario=new Usuario();
		
		usuario.setActivo('N');
		usuario.setClave("d526dcp");
		usuario.setNombre("John Doe");
		usuario.setUsuUsuario(usuarioUsu);
		usuario.setIdentificacion(identificacionU);
		/*cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Avenida Siempre Viva 123");
		cliente.setEmail("hsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 5555");*/
		
		TipoUsuario tipoUsuario=entityManager.find(TipoUsuario.class, new Long(2));
		assertNotNull(tipoUsuario,"El tipo de usuario no existe");
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
			entityManager.persist(usuario);
		entityManager.getTransaction().commit();
	}
	
	@BeforeAll
	public static void iniciar() {
		log.info("Ejectuo el @BeforeAll");
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-logica");
		entityManager=entityManagerFactory.createEntityManager();
	}
	
	@AfterAll
	public static void finalizar() {
		log.info("Ejectuo el @AfterAll");
		entityManager.close();
		entityManagerFactory.close();
	}

	

	
	@BeforeEach
	public  void antes() {
		log.info("Antes de la prueba");
	}
	
	@AfterEach
	public  void despues() {
		log.info("Despues de la prueba");
	}
	
	
	

}
