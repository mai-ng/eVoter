package evoter.server.dao.impl;


//import java.io.IOException;

//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.core.io.InputStreamResource;

/**
 * 
 * @author btdiem
 *
 */

public class BeanDAOFactory {

	private static BeanFactory beanFactory = null;

	public static Object getBean(String beanName) {

		if (beanFactory == null) {
			beanFactory = new ClassPathXmlApplicationContext(
					new String[]{"applicationContext.xml"});
		}
		return beanFactory.getBean(beanName);

	}



}
