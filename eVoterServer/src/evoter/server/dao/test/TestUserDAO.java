package evoter.server.dao.test;


import evoter.server.dao.BeanDAOFactory;
import evoter.share.dao.UserDAO;

public class TestUserDAO {

	
	public static void main (String[] args){
		
	   	 UserDAO userDao = (UserDAO)BeanDAOFactory.getBean("userDAO");
	   	 System.out.println(userDao.findById(1));
	   	 //userDao.deleteByProperty(UserDAO.ID, 9);
	   	 
	   	// userDao.deleteByEmail("test@telecom-sudparis.eu");
	   	 System.out.println(userDao.findAll());
	   	 //
/**	
	File f = new File(".\\resources\\Application-Context.xml");
	try {
		System.out.println(f.getCanonicalPath());
		
		FileSystemXmlApplicationContext fileCtx = new FileSystemXmlApplicationContext(f.getCanonicalPath());
		
//		ApplicationContext context =
//			    new ClassPathXmlApplicationContext("classpath*:resources/Spring-Module.xml");	
		
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
		
	}

	
}
