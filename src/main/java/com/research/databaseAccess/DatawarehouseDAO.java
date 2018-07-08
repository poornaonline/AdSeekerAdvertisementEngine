package com.research.databaseAccess;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONObject;
import com.research.databaseObjects.Advertisement;
import com.research.ontology.OntologyElement;

public class DatawarehouseDAO {
	
	private Session GetHibernateSession()
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
    	return session;
	}
	
	
	public ArrayList<Advertisement> getAdvertisementsForTheGuestUser(JSONObject guestUserAdCountObj)
	{
		ArrayList<Advertisement> ads = new ArrayList<Advertisement>();
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("bike");
		topics.add("car");
		topics.add("computer");
		topics.add("mobile");

		String searchedQuery = "";
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
	    	for (String query : topics) {
	    		
	    	int AmountOfAds = (int)guestUserAdCountObj.get(query);
			
	    	Query execQuery = session.createSQLQuery(
	    			"CALL getLatestAdvertisementsViaRelationship(:relationship, :amount)")
	    			.addEntity(Advertisement.class)
	    			.setParameter("relationship", query)
	    			.setParameter("amount", AmountOfAds);

			ArrayList<Advertisement> AdvertisementList = (ArrayList<Advertisement>) execQuery.list();
			searchedQuery = query;
			
			if (AdvertisementList.size() == 0)
			{
				ArrayList<String> similarQueryNames = OntologyElement.getEquivalentClassesFromOntology(query);
				for (String similarName : similarQueryNames) {
					if (AdvertisementList.size() == 0)
					{
						Query execQuery2 = session.createSQLQuery(
				    			"CALL getLatestAdvertisementsViaRelationship(:relationship, :amount)")
				    			.addEntity(Advertisement.class)
				    			.setParameter("relationship", similarName.trim())
				    			.setParameter("amount", AmountOfAds);
						
						AdvertisementList = (ArrayList<Advertisement>) execQuery2.list();
						searchedQuery = similarName;
					}
				}
				
			}
			
			
			for (Advertisement advertisement : AdvertisementList) {
				ads.add(advertisement);
			}
			
			System.err.println("Fetched Advertisements for query -> " + searchedQuery +" And Amount ->" + AdvertisementList.size());
	    	}
			
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return ads;
	}

}
