package com.supinfo.entity;

public class Contact {
	
	private int contactID;
    private int clientContact;
    private Client clientID;
    
	public Contact(int contactID, int clientContact, Client clientID) {
		super();
		this.contactID = contactID;
		this.clientContact = clientContact;
		this.clientID = clientID;
	}
	public int getContactID() {
		return contactID;
	}
	public void setContactID(int contactID) {
		this.contactID = contactID;
	}
	public int getClientContact() {
		return clientContact;
	}
	public void setClientContact(int clientContact) {
		this.clientContact = clientContact;
	}
	public Client getClientID() {
		return clientID;
	}
	public void setClientID(Client clientID) {
		this.clientID = clientID;
	}
}
