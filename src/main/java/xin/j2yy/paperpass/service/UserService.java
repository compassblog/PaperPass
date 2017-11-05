package xin.j2yy.paperpass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xin.j2yy.paperpass.dao.UserDao;
import xin.j2yy.paperpass.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	public User findById(Integer id) {
		return userDao.findById(id);
	}
}
