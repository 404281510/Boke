package edu.ahpu.boke.dao;

import org.junit.Test;
import org.springframework.stereotype.Repository;

import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.util.GenericClass;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	@Test
	public void syso() {
		
		System.out.println( GenericClass.getGenericClass(this.getClass()));
	}
}
