package evoter.server.dao;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * @author btdiem
 *
 */
public class BeanDAOFactory {

	private static BeanFactory beanFactory = null;

	// private static String contextPath = null;

	// public void setContextPath(String path){
	// contextPath = path;
	// }

	public static BeanFactory getApplicationContext() {

		if (beanFactory == null) {

			File f = new File(".\\resources\\applicationContext.xml");
			System.out.println("read application-context");
			try {
				String OS_NAME = System.getProperty("os.name").toLowerCase();
				if (OS_NAME.indexOf("mac") >= 0) {
					beanFactory = (BeanFactory) new FileSystemXmlApplicationContext(
							"resources/applicationContext.xml");
				} else {
					beanFactory = (BeanFactory) new FileSystemXmlApplicationContext(
							f.getCanonicalPath());
				}
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return beanFactory;
	}

	public static Object getBean(String beanName) {

		return getApplicationContext().getBean(beanName);

	}



}
