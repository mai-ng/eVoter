

import java.io.File;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import evoter.server.dao.BeanDAOFactory;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	

//  	   	 UserDAO userDao = (UserDAO)BeanDAOFactory.getBean("userDAO");
//  	   	 User user = userDao.findById(1);
//  	   	 System.out.println("user.id " + user.getId());
//  	   	 System.out.println("user.email " + user.getEmail());
  	   	 //userDao.deleteByProperty(UserDAO.ID, 9);
  	   	 
 // 	   	 userDao.deleteByEmail("test@telecom-sudparis.eu");

	/**	
		File f = new File(".\\resources\\Application-Context.xml");
		try {
			System.out.println(f.getCanonicalPath());
			
			FileSystemXmlApplicationContext fileCtx = new FileSystemXmlApplicationContext(f.getCanonicalPath());
			
//			ApplicationContext context =
//				    new ClassPathXmlApplicationContext("classpath*:resources/Spring-Module.xml");	
			
		   	   	 UserDAO userDao = (UserDAO)fileCtx.getBean("userDAO");
		   	   	 User user = userDao.findById(1);
		   	   	 System.out.println("user.id " + user.getId());
		   	   	 System.out.println("user.email " + user.getEmail());
		   	   	 //insert user
		   	   	 user.setEmail("test@telecom-sudparis.eu");
		   	   	 user.setPassWord("lalala");
		   	   	 user.setUserTypeId(3);
		   	   	 user.setUserName("tuilatui");
		   	   	 userDao.insert(user);
		   	   	 
		   	   	 System.out.println(user.toString());
		   	   	 //find all 
		   	   	 System.out.println(userDao.findAll().size());
		   	   	 System.out.println(userDao.findByEmail("paul.gibson@telecom-sudparis.eu"));
		   	   	 System.out.println(userDao.findById(4));
		   	   	 System.out.println(userDao.findByUserName("nvluong"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   	 */
		
//		UserDAO userDao = (UserDAO) BeanDAOFactory.getInstance().getBean("userDAO");
//		System.out.println(userDao.findById(1));

	}

}
