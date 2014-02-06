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

	// private static String contextPath = null;

	// public void setContextPath(String path){
	// contextPath = path;
	// }

	/**	public static BeanFactory getApplicationContext() {

		if (beanFactory == null) {

			beanFactory = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
			
			File f = new File("./resources/applicationContext.xml");
			try {
				System.out.println("read application-context" + f.getCanonicalPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String OS_NAME = System.getProperty("os.name").toLowerCase();
				if (OS_NAME.indexOf("mac") >= 0) {
					beanFactory = (BeanFactory) new FileSystemXmlApplicationContext(
							"resources/applicationContext.xml");
				} else {
					//beanFactory = (BeanFactory) new FileSystemXmlApplicationContext(f.getCanonicalPath());
					beanFactory = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
					
//					InputStream is = new FileInputStream(f.getCanonicalPath());
//					
//					 beanFactory = new XmlBeanFactory(
//	                        new InputStreamResource(is));
				}
			} catch (Exception e) {
			
				e.printStackTrace();
			}

		}
		
		
		return beanFactory;
	}*/

	public static Object getBean(String beanName) {

		if (beanFactory == null) {
			beanFactory = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		}
		return beanFactory.getBean(beanName);
		//return getApplicationContext().getBean(beanName);

	}



}
