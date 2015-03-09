package com.supinfo.entity;

import java.util.Collection;
import java.util.Date;

public class Stockage {

	private Integer stockageID;
    private String nom;
    private String adresse;
    private Date date;
    
    public Integer getStockageID() {
		return stockageID;
	}
	public void setStockageID(Integer stockageID) {
		this.stockageID = stockageID;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Collection<Stockageclient> getStockageclientCollection() {
		return stockageclientCollection;
	}
	public void setStockageclientCollection(
			Collection<Stockageclient> stockageclientCollection) {
		this.stockageclientCollection = stockageclientCollection;
	}
	private Collection<Stockageclient> stockageclientCollection;
}
