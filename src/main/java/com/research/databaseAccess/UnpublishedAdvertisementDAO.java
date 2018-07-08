package com.research.databaseAccess;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.IO.AdSeekerOntology;
import com.research.databaseObjects.UnpublishedAdvertisement;
import com.research.databaseObjects.User;
import com.research.ontology.OntologyRelationships;
import com.research.validation.TweetMapper;

public class UnpublishedAdvertisementDAO {
	
private Session hibernateSession = null;
	
	// Return Hibernate session
	private Session GetHibernateSession()
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
    	return session;
	}
	
	// Push unpublished advertisement to the database
	public void PushNewUnpublishedAdvertisementToTheDatabase(JSONObject UnpublishedAdDataObject)
	{
		UnpublishedAdvertisement unpublishedAdvertisement = new UnpublishedAdvertisement();

		unpublishedAdvertisement.setProductTitle(UnpublishedAdDataObject.get("title").toString().trim());
		unpublishedAdvertisement.setProductDescription(UnpublishedAdDataObject.get("description").toString().trim());
		unpublishedAdvertisement.setProductImageURL(UnpublishedAdDataObject.get("imageurl").toString().trim());
		unpublishedAdvertisement.setProductPrice(Double.parseDouble(UnpublishedAdDataObject.get("price").toString().trim()));
		unpublishedAdvertisement.setBelongingUsername(UnpublishedAdDataObject.get("username").toString().trim());
		unpublishedAdvertisement.setUserComment(UnpublishedAdDataObject.get("comment").toString().trim());
		unpublishedAdvertisement.setIsResolved(false);

		Session session = GetHibernateSession();
		session.beginTransaction();
		
		session.save(unpublishedAdvertisement);
			
		session.getTransaction().commit();
		session.close();
	}
	
	// get all unresolved advertisements
	public ArrayList<UnpublishedAdvertisement> getAllUnresolvedAdvertisements()
	{
		ArrayList<UnpublishedAdvertisement> unresolvedAds = new ArrayList<UnpublishedAdvertisement>();
		
		Session session = GetHibernateSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(UnpublishedAdvertisement.class);
		criteria.add(Restrictions.eq("isResolved", false));
		unresolvedAds = (ArrayList<UnpublishedAdvertisement>) criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return unresolvedAds;
	}
	
	// get unplublished Advertisement by Id
	public UnpublishedAdvertisement getUnpublishedAdvertisementById(String productId)
	{
		UnpublishedAdvertisement unpublishedAdvertisement = null;
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(UnpublishedAdvertisement.class);
			criteria.add(Restrictions.eq("ProductId", Integer.parseInt(productId)));

			unpublishedAdvertisement = (UnpublishedAdvertisement) criteria.list().get(0);

			session.getTransaction().commit();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return unpublishedAdvertisement;
	}
	
	// move unpublished advertisement to the user database
	public void moveUnpublishedAdToTheUserAdvertisementDb(String productId, String ontologyRelatedIndividualName)
	{
		
		UnpublishedAdvertisement ua = getUnpublishedAdvertisementById(productId);
		JSONObject uaJO = new JSONObject();
		uaJO.put("title", ua.getProductTitle());
		uaJO.put("description", ua.getProductDescription());
		uaJO.put("imageurl", ua.getProductImageURL());
		uaJO.put("price", ua.getProductPrice());
		
		ArrayList<String> relationshipArrayList = new OntologyRelationships().listOntologyRelationshipInArrayList(ontologyRelatedIndividualName, AdSeekerOntology.getModel(), AdSeekerOntology.getOntologyURI());
    	JSONArray relationshipJSONArray = new TweetMapper().mapArrayListToJsonArray(relationshipArrayList);
    	uaJO.put("relationship", relationshipJSONArray);
    	
    	User adBelongingUser = new UserDAO().GetUser(ua.getBelongingUsername());
    	new AdvertisementDAO().PushNewAdvertisementToTheDatabase(uaJO, adBelongingUser);
    	
    	deleteUnpublishedAdvertisementById(productId);
		
	}
	
	// delete unpublished advertisement by Id
	public void deleteUnpublishedAdvertisementById(String productId)
	{
		UnpublishedAdvertisement unpublishedAdvertisement = null;
		
		try {
			
	    	Session session = GetHibernateSession();
	    	session.beginTransaction();
	    	
			Criteria criteria = session.createCriteria(UnpublishedAdvertisement.class);
			criteria.add(Restrictions.eq("ProductId", Integer.parseInt(productId)));

			unpublishedAdvertisement = (UnpublishedAdvertisement) criteria.list().get(0);
			session.delete(unpublishedAdvertisement);
			
			session.getTransaction().commit();
			session.flush();
	    	session.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

}
