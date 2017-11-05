package xin.j2yy.paperpass.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xin.j2yy.paperpass.dao.UserDao;
import xin.j2yy.paperpass.entity.User;
import xin.j2yy.paperpass.service.UserService;

public class MybatisTest {
	private ApplicationContext app = null;
	private UserService userService = null;
	@Before
	public void before() {
		app = new ClassPathXmlApplicationContext("spring.xml");
		userService = (UserService) app.getBean("userService");
	}
	
	@Test
	public void test1() {
		//ApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		UserDao userDao = session.getMapper(UserDao.class);
		User user = userDao.findById(1);
		System.out.println(user);
	}
	@Test
	public void test2() {
		User user = userService.findById(1);
		System.out.println(user);
	}
}
