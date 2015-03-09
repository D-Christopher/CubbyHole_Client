package com.supinfo.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supinfo.cubbyholeapp.R;
import com.supinfo.entity.Contact;
import com.supinfo.entity.Stockageclient;

public class ContactAdapter extends BaseAdapter {
	private List<Contact> mListSc;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public ContactAdapter(Context context, List<Contact> aListSc) {
		  mContext = context;
		  mListSc = aListSc;
		  mInflater = LayoutInflater.from(mContext);
		}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListSc.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListSc.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public interface ContactAdapterListener {
	    public void onClickNom(Contact item, int position);
	}
	
	private ArrayList<ContactAdapterListener> mListListener = new ArrayList<ContactAdapterListener>();
	/**
	 * Pour ajouter un listener sur notre adapter
	 */
	public void addListener(ContactAdapterListener aListener) {
	    mListListener.add(aListener);
	}
	
	private void sendListener(Contact item, int position) {
	    for(int i = mListListener.size()-1; i >= 0; i--) {
	    	mListListener.get(i).onClickNom(item, position);
	    }
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
		  //(1) : Réutilisation des layouts
		  if (convertView == null) {
		  	//Initialisation de notre item à partir du  layout XML "personne_layout.xml"
		    layoutItem = (LinearLayout) mInflater.inflate(R.layout.contact_layout, parent, false);
		  } else {
		  	layoutItem = (LinearLayout) convertView;
		  }
		  
		  //(2) : Récupération des TextView de notre layout      
		  TextView fichierNom = (TextView)layoutItem.findViewById(R.id.contactNom);
		  TextView fichierMail = (TextView)layoutItem.findViewById(R.id.contactMail);
		  
		  //(3) : Renseignement des valeurs
		  fichierNom.setText(mListSc.get(position).getClientID().getPrenom() +" "+ mListSc.get(position).getClientID().getNom());
		  fichierMail.setText(mListSc.get(position).getClientID().getEmail());
		  
		//On mémorise la position de la "Personne" dans le composant textview
		  fichierNom.setTag(position);
		  fichierMail.setTag(position);
		  
		  //On ajoute un listener
		  fichierNom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
		  		Integer position = (Integer)v.getTag();
		  				
		  		//On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
		  		sendListener(mListSc.get(position), position);				
			}
		});
		  fichierNom.setOnClickListener(new OnClickListener() {

		  	@Override
		  	public void onClick(View v) {
		  		//Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
		  		Integer position = (Integer)v.getTag();
		  				
		  		//On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
		  		sendListener(mListSc.get(position), position);
		  				
		  	}
		          	
		  });
		  
		  if (position % 2 == 0) {
			    layoutItem.setBackgroundColor(Color.rgb(187,	210,	225	));  
			}
		
		  //On retourne l'item créé.
		  return layoutItem;
	}

}
