package com.supinfo.entity;

import java.util.Collection;

public class Plan {

	private Integer planID;
    private String titre;
    private String description;
    private int prix;
    private int duree;
    private int espaceStockage;
    private int quotasQuotidient;
    private int bandePassante;
    
    public Integer getPlanID() {
		return planID;
	}

	public void setPlanID(Integer planID) {
		this.planID = planID;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getEspaceStockage() {
		return espaceStockage;
	}

	public void setEspaceStockage(int espaceStockage) {
		this.espaceStockage = espaceStockage;
	}

	public int getQuotasQuotidient() {
		return quotasQuotidient;
	}

	public void setQuotasQuotidient(int quotasQuotidient) {
		this.quotasQuotidient = quotasQuotidient;
	}

	public int getBandePassante() {
		return bandePassante;
	}

	public void setBandePassante(int bandePassante) {
		this.bandePassante = bandePassante;
	}

	public Collection<Client> getClientCollection() {
		return clientCollection;
	}

	public void setClientCollection(Collection<Client> clientCollection) {
		this.clientCollection = clientCollection;
	}

	private Collection<Client> clientCollection;
}
