package com.research.databaseAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.databaseObjects.Advertisement;
import com.research.databaseObjects.Relationship;
import com.research.databaseObjects.User;
import com.research.ontology.OntologyElement;

public class AdvertisementDAO {
	
	private Session hibernateSession = null;
	
	// Return Hibernate session
	private Session GetHibernateSession()
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
    	return session;
	}
	
	// Push new advertisement to the database
	public void PushNewAdvertisementToTheDatabase(JSONObject advertisementDataJSONObject, User belongingUser)
	{
		Advertisement newAdvertisement = new Advertisement();

		newAdvertisement.setProductTitle(advertisementDataJSONObject.get("title").toString().trim());
		newAdvertisement.setProductDescription(advertisementDataJSONObject.get("description").toString().trim());
		newAdvertisement.setProductImageURL(advertisementDataJSONObject.get("imageurl").toString().trim());
		newAdvertisement.setProductPrice(Double.parseDouble(advertisementDataJSONObject.get("price").toString().trim()));
		newAdvertisement.setBelongingUser(belongingUser);
		newAdvertisement.setAdvertisementCreatedDateTime(System.currentTimeMillis());
		JSONArray relationshipJSONArray = (JSONArray)advertisementDataJSONObject.get("relationship");
		
		ArrayList<Relationship> RelationshipList = new ArrayList<Relationship>();
		
		for (int i = 0; i < relationshipJSONArray.size(); i++) {
			Relationship newRelationship = new Relationship();
			newRelationship.setRelationshipName(relationshipJSONArray.get(i).toString());
			newRelationship.setBelongingAdvertisement(newAdvertisement);
			RelationshipList.add(newRelationship);
		}
		
		newAdvertisement.setProductRelationshipList(RelationshipList);
		
		Session session = GetHibernateSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("userName", belongingUser.getUserName()));
		User user = (User)criteria.list().get(0);
		
		Collection<Advertisement> collectionOfAds = user.getUserInsertedAdvertisements();
		collectionOfAds.add(newAdvertisement);
		user.setUserInsertedAdvertisements(collectionOfAds);
		
		try {

			session.save(newAdvertisement);
			for (Relationship relationship : RelationshipList) {
				session.save(relationship);
			}
			session.save(user);
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	
	public void PushBulkAdvertisementToTheDatabase(JSONArray savingAdsArray, User belongingUser)
	{
		
		Session session = GetHibernateSession();
		session.beginTransaction();
		
		for (int ii = 0; ii < savingAdsArray.size(); ii++)
		{
			JSONObject advertisementDataJSONObject = (JSONObject) savingAdsArray.get(ii);
			Advertisement newAdvertisement = new Advertisement();
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", belongingUser.getUserName()));
			User user = (User)criteria.list().get(0);

			newAdvertisement.setProductTitle(advertisementDataJSONObject.get("title").toString().trim());
			newAdvertisement.setProductDescription(advertisementDataJSONObject.get("description").toString().trim());
			newAdvertisement.setProductImageURL(advertisementDataJSONObject.get("imageurl").toString().trim());
			newAdvertisement.setProductPrice(Double.parseDouble(advertisementDataJSONObject.get("price").toString().trim()));
			newAdvertisement.setBelongingUser(user);
			newAdvertisement.setAdvertisementCreatedDateTime(System.currentTimeMillis());
			JSONArray relationshipJSONArray = (JSONArray)advertisementDataJSONObject.get("relationship");
			
			ArrayList<Relationship> RelationshipList = new ArrayList<Relationship>();
			
			for (int i = 0; i < relationshipJSONArray.size(); i++) {
				Relationship newRelationship = new Relationship();
				newRelationship.setRelationshipName(relationshipJSONArray.get(i).toString());
				newRelationship.setBelongingAdvertisement(newAdvertisement);
				RelationshipList.add(newRelationship);
			}
			
			newAdvertisement.setProductRelationshipList(RelationshipList);
	
			Collection<Advertisement> collectionOfAds = user.getUserInsertedAdvertisements();
			collectionOfAds.add(newAdvertisement);
			user.setUserInsertedAdvertisements(collectionOfAds);
			
			try {

				session.save(newAdvertisement);
				for (Relationship relationship : RelationshipList) {
					session.save(relationship);
				}
				session.save(user);
				
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		}

		session.getTransaction().commit();
		session.close();

	}
	
	
	public ArrayList<Relationship> GetAdvertisementArrayFromDatabase(String query, int AmountOfAds)
	{
		ArrayList<Relationship> AdvertisementList = new ArrayList<Relationship>();
		String searchedQuery = query;
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
	    	Query execQuery = session.createSQLQuery(
	    			"CALL getLatestRelationship(:relationship, :amount)")
	    			.addEntity(Relationship.class)
	    			.setParameter("relationship", query)
	    			.setParameter("amount", AmountOfAds);

			AdvertisementList = (ArrayList<Relationship>) execQuery.list();
			
			if (AdvertisementList.size() == 0)
			{
				ArrayList<String> similarQueryNames = OntologyElement.getEquivalentClassesFromOntology(query);
				for (String similarName : similarQueryNames) {
					if (AdvertisementList.size() == 0)
					{
						Query execQuery2 = session.createSQLQuery(
				    			"CALL getLatestRelationship(:relationship, :amount)")
				    			.addEntity(Relationship.class)
				    			.setParameter("relationship", similarName.trim())
				    			.setParameter("amount", AmountOfAds);
						
						AdvertisementList = (ArrayList<Relationship>) execQuery2.list();
						searchedQuery = similarName;
					}
				}
				
			}
			
			System.err.println("Fetched Advertisements for query -> " + searchedQuery +" And Amount ->" + AdvertisementList.size());
			
			session.getTransaction().commit();
//	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return AdvertisementList;
	}
	
	// Search Ads via Title
	public ArrayList<Advertisement> SearchAdsByTitle(String userName, String query, int AmountOfAds)
	{
		ArrayList<Advertisement> AdvertisementList = new ArrayList<Advertisement>();
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(Advertisement.class);
			criteria.add(Restrictions.like("ProductTitle", "%" + query.trim() + "%"));
			criteria.setMaxResults(AmountOfAds);
			
			System.out.println("Query ->" + query +" : Amount ->" + AmountOfAds);
			
			AdvertisementList = (ArrayList<Advertisement>) criteria.list();
			
			System.err.println("Fetched Advertisements for query {TITLE} -> " +query +" And Amount ->" + AdvertisementList.size());
			
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return AdvertisementList;
	}
	
	
	// Search Ads via Description
	public ArrayList<Advertisement> SearchAdsByDescription(String userName, String query, int AmountOfAds)
	{
		ArrayList<Advertisement> AdvertisementList = new ArrayList<Advertisement>();
			
		try {
				
		    Session session = GetHibernateSession();
		    session.beginTransaction();
		    
			Criteria criteria = session.createCriteria(Advertisement.class);
			criteria.add(Restrictions.like("ProductDescription", "%" + query.trim() + "%"));
			criteria.setMaxResults(AmountOfAds);
				
			System.out.println("Query ->" + query +" : Amount ->" + AmountOfAds);
				
			AdvertisementList = (ArrayList<Advertisement>) criteria.list();
				
			System.err.println("Fetched Advertisements for query {DESCRIPTION} -> " +query +" And Amount ->" + AdvertisementList.size());
				
			session.getTransaction().commit();
		    session.close();

			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
		return AdvertisementList;
	}
	
	
	
	// Delete advertisement using productId and userName
	public void deleteAdvertisementById(String userName, String productId)
	{
		try {
			
		    Session session = GetHibernateSession();
		    session.beginTransaction();
		    
			Criteria criteria = session.createCriteria(Advertisement.class);
			criteria.add(Restrictions.eq("ProductId", Integer.parseInt(productId)));
				
			ArrayList<Advertisement> advertisementArrayList = (ArrayList<Advertisement>) criteria.list();
			
			for (Advertisement advertisement : advertisementArrayList) {
				if ( advertisement.getBelongingUser().getUserName().equals(userName.trim()) ){
					
					Criteria userCriteria = session.createCriteria(User.class);
					userCriteria.add(Restrictions.eq("userName", userName.trim()));
					User user = (User) userCriteria.list().get(0);
					
					Collection<Advertisement> modifyingAdsCollection = user.getUserInsertedAdvertisements();
					modifyingAdsCollection.remove(advertisement);
					
					session.update(user);
					session.delete(advertisement);
				}
			}
			
			session	.flush();
			session.getTransaction().commit();
		    session.close();

			}catch(Exception e){
				System.out.println(e.getMessage());
			}
	}
	
	// get advertisement by id
	public Advertisement getAdvertisementById(String productID)
	{
		Advertisement advertisement = null;
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(Advertisement.class);
			criteria.add(Restrictions.eq("ProductId", Integer.parseInt(productID)));

			advertisement = (Advertisement)criteria.list().get(0);
			
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return advertisement;
	}
	
	
	public Collection<Advertisement> getUserEnteredAdvertisementList(String username)
	{
		Collection<Advertisement> usersAdvertisements = new ArrayList<Advertisement>();
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", username.trim()));

			User user = (User) criteria.list().get(0);
			System.out.println("Fetched User -> " + user.getUserName());
			Hibernate.initialize(user.getUserInsertedAdvertisements());
			usersAdvertisements = user.getUserInsertedAdvertisements();
			
			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return usersAdvertisements;
	}
	
	
	public JSONObject GetAdvertisementsJSONObject(JSONObject relationshipJO, int AmountOfAds)
	{
		AdvertisementFetcher af = new AdvertisementFetcher();
		JSONObject adAmountJO = af.getAdvertisementAmount(relationshipJO, AmountOfAds);

		JSONArray advertisementList = new JSONArray();

		ArrayList<String> adRelationshipNameAL = new ArrayList<String>();
		
		Iterator<String> adRelationshipName = adAmountJO.keySet().iterator();
		
		while(adRelationshipName.hasNext())
		{
			adRelationshipNameAL.add(adRelationshipName.next().toString());
		}
		
		ArrayList<Integer> productIds = new ArrayList<Integer>(); 
		
		
		for (String relationship : adRelationshipNameAL) {
			
			if ((int)adAmountJO.get(relationship) == 0)
			{
				continue;
			}
			
			ArrayList<Relationship> adArr = GetAdvertisementArrayFromDatabase(relationship,(int)adAmountJO.get(relationship));
			
			for (Relationship relationship2 : adArr) {
				
				int productId = relationship2.getBelongingAdvertisement().getProductId();
				
				if (!productIds.contains(productId)) {
					advertisementList.add(relationship2.getBelongingAdvertisement());
					productIds.add(productId);
				}
			}	
		}
		
		return advertisementJSONmapper(advertisementList);
	}
	
	
	private JSONObject advertisementJSONmapper(JSONArray advertisements)
	{
    	JSONObject AllProductsJSON = new JSONObject();
  
    	for (int i = 0; i < advertisements.size(); i++) {
    		
    		JSONObject productJSON = new JSONObject();
    		Advertisement currentAd = (Advertisement) advertisements.get(i);
			
    		productJSON.put("ProductId", currentAd.getProductId());
    		productJSON.put("ProductTitle",currentAd.getProductTitle());
    		productJSON.put("ProductDescription", currentAd.getProductDescription());
    		productJSON.put("ProductImageURL", currentAd.getProductImageURL());
    		productJSON.put("ProductPrice", currentAd.getProductPrice());
    		
    		AllProductsJSON.put(i, productJSON);
		}

		return AllProductsJSON;
	}

}


