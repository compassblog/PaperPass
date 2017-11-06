package xin.j2yy.paperpass.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xin.j2yy.paperpass.dao.RoleAuthorityDao;
import xin.j2yy.paperpass.dao.TeacherDao;
import xin.j2yy.paperpass.dao.UserDao;
import xin.j2yy.paperpass.entity.RoleAuthority;
import xin.j2yy.paperpass.entity.Teacher;
import xin.j2yy.paperpass.entity.User;

public class InsertTest {
	ApplicationContext app;
	@Before
	public void before() {
		app = new ClassPathXmlApplicationContext("spring.xml");
	}
	@Test
	public void test1() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		TeacherDao teacherDao = session.getMapper(TeacherDao.class);
		teacherDao.deleteByPrimaryKey(3);
		session.commit();
		
		Teacher teacher = new Teacher();
		teacher.setName("马云");
		teacher.setGender("男");
		teacher.setPost("CEO");
		teacher.setPhone("13195147591");
		teacher.setEmail("mayun@alibaba.com");
		
		UserDao userDao = (UserDao) session.getMapper(UserDao.class);
		User user = userDao.selectByPrimaryKey(5);
		
		teacher.setUser(user);
		teacherDao.insert(teacher);
		session.commit();
		session.close();
	}
	@Test
	public void test2() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		UserDao userDao = session.getMapper(UserDao.class);
		User user = new User();
		user.setUsername("mayun");
		user.setPassword("123");
		user.setStatus(1);
		userDao.insert(user);
		session.commit();
		session.close();
	}
	@Test
	public void test3() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		TeacherDao teacherDao = session.getMapper(TeacherDao.class);
		teacherDao.deleteByPrimaryKey(4);
		session.commit();
		session.close();
	}
	
	@Test
	public void test4() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		
		TeacherDao teacherDao = session.getMapper(TeacherDao.class);
		Teacher teacher = teacherDao.selectByPrimaryKey(2);
		
		System.out.println(teacher.getName());
		UserDao userDao = (UserDao) session.getMapper(UserDao.class);
		User user = userDao.selectByPrimaryKey(5);
		teacher.setPost("校长");
		teacher.setUser(user);
		
		teacherDao.updateByPrimaryKey(teacher);
		
		session.commit();
		session.close();
	}
	
	@Test
	public void test5() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		RoleAuthorityDao rad = session.getMapper(RoleAuthorityDao.class);
		List<RoleAuthority> RoleAuthority = rad.selectAll();
		for (RoleAuthority roleAuthority2 : RoleAuthority) {
			System.out.println(roleAuthority2.getAid() + "<===>" + roleAuthority2.getRid());
		}
		session.close();
	}
}
