package com.supinfo.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supinfo.cubbyholeapp.R;
import com.supinfo.entity.Contact;

public class ChoixContactAdaper extends BaseAdapter{
	// Une liste
	private List<Contact> mListC;
	    	
	//Le contexte dans lequel est présent notre adapter
	private Context mContext;
	    	
	//Un mécanisme pour gérer l'affichage graphique depuis un layout XML
	private LayoutInflater mInflater;
	
	public ChoixContactAdaper(Context context, List<Contact> aListC) {
		  mContext = context;
		  mListC = aListC;
		  mInflater = LayoutInflater.from(mContext);
		}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListC.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListC.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	  LinearLayout layoutItem;
	  //(1) : Réutilisation des layouts
	  if (convertView == null) {
	  	//Initialisation de notre item à partir du  layout XML "personne_layout.xml"
	    layoutItem = (LinearLayout) mInflater.inflate(R.layout.choixcontact_layout, parent, false);
	  } else {
	  	layoutItem = (LinearLayout) convertView;
	  }
	  
	  //(2) : Récupération des TextView de notre layout      
	  TextView choixContactId = (TextView)layoutItem.findViewById(R.id.choixContactId);
	  TextView choixContactNom = (TextView)layoutItem.findViewById(R.id.choixContactNom);
	        
	  //(3) : Renseignement des valeurs       
	  choixContactId.setText(Integer.toString(mListC.get(position).getClientID().getClientID()));
	  choixContactNom.setText(mListC.get(position).getClientID().getPrenom()+" "+mListC.get(position).getClientID().getNom());
	  
	//On mémorise la position de la "Personne" dans le composant textview
	  choixContactId.setTag(position);
	  //On ajoute un listener
	  choixContactId.setOnClickListener(new OnClickListener() {

	  	@Override
	  	public void onClick(View v) {
	  		//Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
	  		Integer position = (Integer)v.getTag();
	  				
	  		//On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
	  		sendListener(mListC.get(position), position);
	  				
	  	}
	          	
	  });
	
	  //On retourne l'item créé.
	  return layoutItem;
	}
	
	public interface ChoixContactAdapterListener {
	    public void onClickNom(Contact item, int position);
	}
	
	private ArrayList<ChoixContactAdapterListener> mListListener = new ArrayList<ChoixContactAdapterListener>();
	/**
	 * Pour ajouter un listener sur notre adapter
	 */
	public void addListener(ChoixContactAdapterListener aListener) {
	    mListListener.add(aListener);
	}
	
	private void sendListener(Contact item, int position) {
	    for(int i = mListListener.size()-1; i >= 0; i--) {
	    	mListListener.get(i).onClickNom(item, position);
	    }
	}

}
