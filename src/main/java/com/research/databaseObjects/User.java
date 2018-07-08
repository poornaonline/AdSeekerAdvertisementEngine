package com.research.databaseObjects;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id @Column(unique=true)
	private String userName;
	private Long userProfileCreateDateTime;
	@Lob
	private String userCharacterProfile;
	private Long userCharacterProfileUpdatedTime;
	@Lob
	private String clearedCharacterProfiles;
	@Lob
	private String userSearchedKeyWords;
	@Lob
	private String clearedUserSearchedKeyWords;
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Advertisement> userInsertedAdvertisements = new ArrayList<Advertisement>();
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserProfileCreateDateTime() {
		return userProfileCreateDateTime;
	}
	public void setUserProfileCreateDateTime(Long userProfileCreateDateTime) {
		this.userProfileCreateDateTime = userProfileCreateDateTime;
	}
	public String getUserCharacterProfile() {
		return userCharacterProfile;
	}
	public void setUserCharacterProfile(String userCharacterProfile) {
		this.userCharacterProfile = userCharacterProfile;
	}
	public Long getUserCharacterProfileUpdatedTime() {
		return userCharacterProfileUpdatedTime;
	}
	public void setUserCharacterProfileUpdatedTime(
			Long userCharacterProfileUpdatedTime) {
		this.userCharacterProfileUpdatedTime = userCharacterProfileUpdatedTime;
	}
	public String getClearedCharacterProfiles() {
		return clearedCharacterProfiles;
	}
	public void setClearedCharacterProfiles(String clearedCharacterProfiles) {
		this.clearedCharacterProfiles = clearedCharacterProfiles;
	}
	public String getUserSearchedKeyWords() {
		return userSearchedKeyWords;
	}
	public void setUserSearchedKeyWords(String userSearchedKeyWords) {
		this.userSearchedKeyWords = userSearchedKeyWords;
	}
	public String getClearedUserSearchedKeyWords() {
		return clearedUserSearchedKeyWords;
	}
	public void setClearedUserSearchedKeyWords(String clearedUserSearchedKeyWords) {
		this.clearedUserSearchedKeyWords = clearedUserSearchedKeyWords;
	}
	public Collection<Advertisement> getUserInsertedAdvertisements() {
		return userInsertedAdvertisements;
	}
	public void setUserInsertedAdvertisements(
			Collection<Advertisement> userInsertedAdvertisements) {
		this.userInsertedAdvertisements = userInsertedAdvertisements;
	}

}
