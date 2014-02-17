package evoter.server.dao.impl;


import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * This class is a singletone works as a factory that produces spring beans defined in applicationContext.xml </br>
 * @author btdiem </br>
 *
 */

public class BeanDAOFactory {

	private static BeanFactory beanFactory = null;

	/**
	 * 
	 * @param beanName
	 * @return Spring beans defined in applicationContext 
	 * and loaded when server is started </br> 
	 */
	public static Object getBean(String beanName) {

		if (beanFactory == null) {
			beanFactory = new ClassPathXmlApplicationContext(
					new String[]{"applicationContext.xml"});
		}
		return beanFactory.getBean(beanName);

	}



}
