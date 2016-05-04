package nuaa.ggx.pos.dataprocess.service.impl;

import java.security.NoSuchAlgorithmException;
import nuaa.ggx.pos.dataprocess.dao.impl.UserDao;
import nuaa.ggx.pos.dataprocess.model.TUser;
import nuaa.ggx.pos.dataprocess.service.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService implements IUserService{

	@Autowired
	private UserDao userdao;
	
	@Override
	public TUser getById(Integer id) {
		return userdao.getById(id);
	}
	
	@Override
	public TUser loadById(Integer id) {
		return userdao.loadById(id);
	}

	@Override
	public boolean accountExist(String username) {
		return userdao.findIfExists(username);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		//System.out.println(StringHelper.md5("yc12345678"));
	}

	@Override
	public TUser login(String username, String password)
			throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveRegister(TUser user) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		
	}
}
