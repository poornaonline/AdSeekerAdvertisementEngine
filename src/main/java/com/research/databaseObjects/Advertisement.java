package com.research.databaseObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Advertisement {
	
	@Id @GeneratedValue @Column(unique=true)
	private int ProductId;
	private String ProductTitle;
	@Lob
	private String ProductDescription;
	private String ProductImageURL;
	private Double ProductPrice; 
	private Long AdvertisementCreatedDateTime;
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Relationship> ProductRelationshipList = new ArrayList<Relationship>();
	@ManyToOne
	private User belongingUser;
	
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public String getProductTitle() {
		return ProductTitle;
	}
	public void setProductTitle(String productTitle) {
		ProductTitle = productTitle;
	}
	public String getProductDescription() {
		return ProductDescription;
	}
	public void setProductDescription(String productDiscription) {
		ProductDescription = productDiscription;
	}
	public String getProductImageURL() {
		return ProductImageURL;
	}
	public void setProductImageURL(String productImageURL) {
		ProductImageURL = productImageURL;
	}
	public Double getProductPrice() {
		return ProductPrice;
	}
	public void setProductPrice(Double productPrice) {
		ProductPrice = productPrice;
	}
	public Collection<Relationship> getProductRelationshipList() {
		return ProductRelationshipList;
	}
	public void setProductRelationshipList(Collection<Relationship> productRelationshipList) {
		ProductRelationshipList = productRelationshipList;
	}
	public Long getAdvertisementCreatedDateTime() {
		return AdvertisementCreatedDateTime;
	}
	public void setAdvertisementCreatedDateTime(Long advertisementCreatedDateTime) {
		AdvertisementCreatedDateTime = advertisementCreatedDateTime;
	}
	public User getBelongingUser() {
		return belongingUser;
	}
	public void setBelongingUser(User belongingUser) {
		this.belongingUser = belongingUser;
	}
}
