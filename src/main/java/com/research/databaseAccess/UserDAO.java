package com.research.databaseAccess;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.research.databaseObjects.User;

public class UserDAO {
	
	private Session hibernateSession = null;
	
	private Session GetHibernateSession()
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
    	return session;
	}
	
	public void PushNewUserToTheDatabase(String newUserName, JSONObject newUserProfileObj)
	{
		User newUser = new User();
		
		newUser.setUserName(newUserName.trim());
		newUser.setUserCharacterProfile(newUserProfileObj.toJSONString());
		newUser.setUserProfileCreateDateTime(System.currentTimeMillis());
		newUser.setUserCharacterProfileUpdatedTime(System.currentTimeMillis());
		newUser.setUserSearchedKeyWords(new JSONArray().toJSONString());
		newUser.setClearedCharacterProfiles(new JSONObject().toJSONString());
		newUser.setClearedUserSearchedKeyWords(new JSONObject().toJSONString());
		
		Session session = GetHibernateSession();
		session.beginTransaction();
		
		try {
			
			session.save(newUser);
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		session.getTransaction().commit();
		session.close();
	
	}
	
	public User GetUser(String userName)
	{
		
		User user = null;
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", userName.trim()));

			user = (User)criteria.list().get(0);
			
			session.getTransaction().commit();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return user;
	}
	
	public void updateUserCharacterProfileAndKeyWordProfile(String userName, JSONObject newCharacterProfile)
	{
	
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", userName.trim()));

			User user = (User)criteria.list().get(0);
			
			JSONObject oldCharacterProfile = (JSONObject) new JSONParser().parse(user.getUserCharacterProfile());
			JSONObject previousCharacterProfiles = (JSONObject) new JSONParser().parse(user.getClearedCharacterProfiles());
			previousCharacterProfiles.put(System.currentTimeMillis(), oldCharacterProfile);
			
			JSONArray userSearchedKeyWordsArray = (JSONArray) new JSONParser().parse(user.getUserSearchedKeyWords());
			JSONObject previousSearchedKeyWords = (JSONObject) new JSONParser().parse(user.getClearedUserSearchedKeyWords());
			
			if(userSearchedKeyWordsArray.size() > 0)
			{
				previousSearchedKeyWords.put(System.currentTimeMillis(), userSearchedKeyWordsArray);

			}
			
			user.setUserCharacterProfile(newCharacterProfile.toJSONString());
			user.setClearedCharacterProfiles(previousCharacterProfiles.toJSONString());
			user.setUserCharacterProfileUpdatedTime(System.currentTimeMillis());
			user.setUserSearchedKeyWords(new JSONArray().toJSONString());
			user.setClearedUserSearchedKeyWords(previousSearchedKeyWords.toJSONString());
			
			session.update(user);			
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void checkAndUpdateUserKeyWordProfile(String userName)
	{
	
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", userName.trim()));

			User user = (User)criteria.list().get(0);
			
			JSONArray userSearchedKeyWordsArray = (JSONArray) new JSONParser().parse(user.getUserSearchedKeyWords());
			JSONObject previousSearchedKeyWords = (JSONObject) new JSONParser().parse(user.getClearedUserSearchedKeyWords());
			
			if(userSearchedKeyWordsArray.size() > 5)
			{
				previousSearchedKeyWords.put(System.currentTimeMillis(), userSearchedKeyWordsArray);

			}
			
			user.setUserCharacterProfileUpdatedTime(System.currentTimeMillis());
			user.setUserSearchedKeyWords(new JSONArray().toJSONString());
			user.setClearedUserSearchedKeyWords(previousSearchedKeyWords.toJSONString());
			
			session.update(user);			
			session.getTransaction().commit();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void addUserSearchedKeyWord(String userName, String keyword)
	{
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", userName.trim()));

			User user = (User)criteria.list().get(0);

			JSONArray userSearchedKeyWordsArray = (JSONArray) new JSONParser().parse(user.getUserSearchedKeyWords());
			userSearchedKeyWordsArray.add(keyword);

			user.setUserSearchedKeyWords(userSearchedKeyWordsArray.toJSONString());

			session.update(user);			
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList<User> getAllUsers()
	{
		ArrayList<User> allUsers = new ArrayList<User>();
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);
			allUsers = (ArrayList<User>) criteria.list();		
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return allUsers;
	}

}
