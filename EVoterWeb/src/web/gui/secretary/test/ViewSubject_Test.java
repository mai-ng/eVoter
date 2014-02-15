package web.gui.secretary.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import evoter.share.model.Subject;

import web.gui.secretary.ViewSubject;

public class ViewSubject_Test {

	
	public static void main(String[] args) throws IOException
	{
		Subject sub = new Subject();
		ViewSubject subject = new ViewSubject(sub);
	}
}
