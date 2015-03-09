package com.supinfo.entity;

import java.util.Collection;
import java.util.Date;

public class Client {
	
	private Integer clientID;
	private String nom;
	private String prenom;
    private String email;
    private String mdp;
    private Date dateOffreDebut;
    private Date dateOffreFin;
    private int bandePassante;
    private int quotas;
    private int espace;    
	private Plan planID;
    
	public Integer getClientID() {
		return clientID;
	}
	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public Date getDateOffreDebut() {
		return dateOffreDebut;
	}
	public void setDateOffreDebut(Date dateOffreDebut) {
		this.dateOffreDebut = dateOffreDebut;
	}
	public Date getDateOffreFin() {
		return dateOffreFin;
	}
	public void setDateOffreFin(Date dateOffreFin) {
		this.dateOffreFin = dateOffreFin;
	}
	public int getBandePassante() {
		return bandePassante;
	}
	public void setBandePassante(int bandePassante) {
		this.bandePassante = bandePassante;
	}
	public int getQuotas() {
		return quotas;
	}
	public void setQuotas(int quotas) {
		this.quotas = quotas;
	}
	public int getEspace() {
		return espace;
	}
	public void setEspace(int espace) {
		this.espace = espace;
	}
	public Plan getPlanID() {
		return planID;
	}
	public void setPlanID(Plan planID) {
		this.planID = planID;
	}
}
