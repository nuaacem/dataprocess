package rivers.yeah.research.datacollect.weibo.service;

import rivers.yeah.research.datacollect.weibo.dao.UserDao;
import rivers.yeah.research.datacollect.weibo.model.User;


public class UserService {
	
	private static final UserDao userDao = new UserDao();
	
	public static int addUser(User	user) {
		return userDao.save(user);
	}
}
