package com.research.databaseAccess;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.research.databaseObjects.AdminUser;
import com.research.databaseObjects.User;

public class AdminDAO {
	
	private Session hibernateSession = null;
	
	private Session GetHibernateSession()
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
    	return session;
	}
	
	public void createNewAdminAccount(String username, String password)
	{
		AdminUser admin = new AdminUser();
		
		admin.setAdminUsername(username.trim());
		admin.setAdminPassword(password.trim());
		
		Session session = GetHibernateSession();
		session.beginTransaction();
		
		try {
			
			session.save(admin);
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	public Boolean adminCredentialCorrect(String username, String password)
	{
		Boolean credentialStatus = false;
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(AdminUser.class);
			criteria.add(Restrictions.eq("adminUsername", username.trim()));

			List<AdminUser> adminList = criteria.list();
			
			for (AdminUser adminUser : adminList) {
				
				if(adminUser.getAdminUsername().equals(username) && adminUser.getAdminPassword().equals(password))
				{
					credentialStatus = true;
				}
			}
				
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return credentialStatus;
	}

}
