package com.supinfo.entity;

import java.util.Collection;

public class Permission {

	private Integer permissionID;
    private String titre;
    private Collection<Stockageclient> stockageclientCollection;
    
	public Integer getPermissionID() {
		return permissionID;
	}
	public void setPermissionID(Integer permissionID) {
		this.permissionID = permissionID;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public Collection<Stockageclient> getStockageclientCollection() {
		return stockageclientCollection;
	}
	public void setStockageclientCollection(
			Collection<Stockageclient> stockageclientCollection) {
		this.stockageclientCollection = stockageclientCollection;
	}
}
