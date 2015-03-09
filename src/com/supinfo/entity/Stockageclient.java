package com.supinfo.entity;

public class Stockageclient {

	private Integer stockageClientID;
    private Client clientID;
    private Client partagerID;
    private Stockage stockageID;
    private Permission permissionID;
    private Boolean auteur;
	public Integer getStockageClientID() {
		return stockageClientID;
	}
	public void setStockageClientID(Integer stockageClientID) {
		this.stockageClientID = stockageClientID;
	}
	public Client getClientID() {
		return clientID;
	}
	public void setClientID(Client clientID) {
		this.clientID = clientID;
	}
	public Client getPartagerID() {
		return partagerID;
	}
	public void setPartagerID(Client partagerID) {
		this.partagerID = partagerID;
	}
	public Stockage getStockageID() {
		return stockageID;
	}
	public void setStockageID(Stockage stockageID) {
		this.stockageID = stockageID;
	}
	public Permission getPermissionID() {
		return permissionID;
	}
	public void setPermissionID(Permission permissionID) {
		this.permissionID = permissionID;
	}
	public Boolean getAuteur() {
		return auteur;
	}
	public void setAuteur(Boolean auteur) {
		this.auteur = auteur;
	}
}
