package evoter.server.dao;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BeanDAOFactory {

	
	private static BeanFactory beanFactory = null;
//	private  static String contextPath = null;
	
//	public void setContextPath(String path){
//		contextPath = path;
//	}
	
	public static BeanFactory getApplicationContext(){
		//TODO : Luongnv89: Test connection on Mac
		if (beanFactory == null){
			
			File f = new File("resources/Application-Context.xml");
			try {
//				beanFactory = (BeanFactory)new FileSystemXmlApplicationContext(f.getCanonicalPath());
				beanFactory = (BeanFactory)new FileSystemXmlApplicationContext("resources/Application-Context.xml");
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return beanFactory;
	}
	
	
	public static Object getBean(String beanName){
		
		return getApplicationContext().getBean(beanName);
		
	}
	
//	private BeanDAOFactory() {
//		
//		File f = new File(".\\resources\\Application-Context.xml");
//		try {
//			FileSystemXmlApplicationContext fileCtx = 
//					new FileSystemXmlApplicationContext(f.getCanonicalPath());
//			org.springframework.beans.factory.BeanFactory factory = (org.springframework.beans.factory.BeanFactory) fileCtx;
//			
//		} catch (BeansException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	 
	  /**
	   * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	   * or the first access to SingletonHolder.INSTANCE, not before.
	   */
	  private static class SingletonHolder { 
	    
		  private static final BeanDAOFactory INSTANCE = new BeanDAOFactory();
	    
	  }

	  public static BeanDAOFactory getInstance() {
	    return SingletonHolder.INSTANCE;
	  }

//	@Override
//	public boolean containsBean(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public String[] getAliases(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object getBean(String arg0) throws BeansException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object getBean(String arg0, Class arg1) throws BeansException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object getBean(String arg0, Object[] arg1) throws BeansException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Class getType(String arg0) throws NoSuchBeanDefinitionException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isPrototype(String arg0)
//			throws NoSuchBeanDefinitionException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isSingleton(String arg0)
//			throws NoSuchBeanDefinitionException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isTypeMatch(String arg0, Class arg1)
//			throws NoSuchBeanDefinitionException {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	  
	  
}
