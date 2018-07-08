package com.research.databaseObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Relationship {
	
	@Id @GeneratedValue @Column(unique=true)
	private int RelationshipId;
	private String RelationshipName;
	@ManyToOne
	private Advertisement BelongingAdvertisement;
	
	public Advertisement getBelongingAdvertisement() {
		return BelongingAdvertisement;
	}
	public void setBelongingAdvertisement(Advertisement belongingAdvertisement) {
		BelongingAdvertisement = belongingAdvertisement;
	}
	public int getRelationshipId() {
		return RelationshipId;
	}
	public void setRelationshipId(int relationshipId) {
		RelationshipId = relationshipId;
	}
	public String getRelationshipName() {
		return RelationshipName;
	}
	public void setRelationshipName(String relationshipName) {
		RelationshipName = relationshipName;
	}

}
