package edu.ahpu.boke.util;

import java.lang.reflect.ParameterizedType;

import org.junit.Test;

import edu.ahpu.boke.dao.UserDao;
import edu.ahpu.boke.dao.UserDaoImpl;

public class GenericClass {
/*@Test
public void ss(){
	UserDao us = new UserDaoImpl();
	System.out.println(GenericClass.getGenericClass(us.getClass()));
}*/
    public static Class<?> getGenericClass(Class<?> clazz) {
        ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
        
        Class<?> entityClass = (Class<?>) type.getActualTypeArguments()[0];
        return entityClass;
    }


}
