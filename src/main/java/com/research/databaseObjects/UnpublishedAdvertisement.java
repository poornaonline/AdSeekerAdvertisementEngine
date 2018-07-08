package com.research.databaseObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class UnpublishedAdvertisement {
	
	@Id @GeneratedValue @Column(unique=true)
	private int ProductId;
	private String ProductTitle;
	@Lob
	private String ProductDescription;
	private String ProductImageURL;
	private Double ProductPrice; 
	private String BelongingUsername;
	@Lob
	private String UserComment;
	private Boolean isResolved;
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
	public void setProductDescription(String productDescription) {
		ProductDescription = productDescription;
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
	public String getBelongingUsername() {
		return BelongingUsername;
	}
	public void setBelongingUsername(String belongingUsername) {
		BelongingUsername = belongingUsername;
	}
	public String getUserComment() {
		return UserComment;
	}
	public void setUserComment(String userComment) {
		UserComment = userComment;
	}
	public Boolean getIsResolved() {
		return isResolved;
	}
	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}
}
