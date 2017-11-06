package xin.j2yy.paperpass.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xin.j2yy.paperpass.dao.AuthorityDao;
import xin.j2yy.paperpass.dao.PaperDao;
import xin.j2yy.paperpass.dao.RoleDao;
import xin.j2yy.paperpass.dao.StudentDao;
import xin.j2yy.paperpass.dao.TeacherDao;
import xin.j2yy.paperpass.dao.UserDao;
import xin.j2yy.paperpass.entity.Authority;
import xin.j2yy.paperpass.entity.Paper;
import xin.j2yy.paperpass.entity.Role;
import xin.j2yy.paperpass.entity.Student;
import xin.j2yy.paperpass.entity.Teacher;
import xin.j2yy.paperpass.entity.User;

public class MybatisTest {
	private ApplicationContext app = null;
//	private UserService userService = null;
	@Before
	public void before() {
		app = new ClassPathXmlApplicationContext("spring.xml");
	}
	
	@Test
	public void test1() {
		//ApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		UserDao userDao = session.getMapper(UserDao.class);
		List<User> users = userDao.selectAll();
		for (User user : users) {
			System.out.println(user.getUsername() /*+ "<<=====>>" + user.getRole().getRolename()*/);
		}
		//User user = userDao.selectByPrimaryKey(3);
		//System.out.println(user.getUsername());
		//System.out.println(user.getUsername() + "=====>>>" + user.getRole().getRolename());
	}
	
	@Test
	public void test2() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		StudentDao studentDao = session.getMapper(StudentDao.class);
		Student student = studentDao.selectByPrimaryKey(2);
		System.out.println(student.getName() + "<====>" + student.getUser().getUsername());
		System.out.println(student.getPaper().getTitle());
		session.close();
	}
	
	@Test
	public void test3() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		PaperDao paperDao = session.getMapper(PaperDao.class);
		Paper paper = paperDao.selectByPrimaryKey(1);
		System.out.println(paper.getTitle());
		session.close();
	}
	
	@Test
	public void test4() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		TeacherDao teacherDao = session.getMapper(TeacherDao.class);
		Teacher teacher = teacherDao.selectByPrimaryKey(1);
		List<Paper> papers = teacher.getPapers();
		for (Paper paper : papers) {
			System.out.println(paper.getTitle());
		}
		session.close();
	}
	
	@Test
	public void test5() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		RoleDao roleDao = session.getMapper(RoleDao.class);
		Role role = roleDao.selectByPrimaryKey(3);
		List<Authority> authorities = role.getAuthorities();
		for (Authority authority : authorities) {
			System.out.println("权限url ==>" + authority.getUrl());
		}
		session.close();
	}
	@Test
	public void test6() {
		SqlSessionFactory ssf = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();
		AuthorityDao authorityDao = session.getMapper(AuthorityDao.class);
		Authority authority = authorityDao.selectByPrimaryKey(1);
		List<Role> roles = authority.getRoles();
		for (Role role : roles) {
			System.out.println(role.getRolename());
		}
		session.close();
	}
}