package fr.univbrest.masterdosi.android.activity;

import fr.univbrest.masterdosi.android.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Cette Activity délègue l'affichage au layout contenu dans res/layout/activity_accueil.xml
 * Le fichier XML comporte 
 * <ul>
 * <li>un TextView sans id car nous n'avons pas besoin d'interagir avec dans les Activity</li>
 * <li>un EditText ayant pour id <b>nomText</b></li>
 * <li>un bouton ayant pour id <b>buttonPageDeux</b> visant à naviguer vers l'écran deux (<b>PageDeuxActivity</b>)</li>
 * <li>un bouton ayant pour id <b>buttonPageTrois</b> visant à naviguer vers l'écran trois (<b>PageTroisActivity</b>) en transmettant la valeur du champ <b>nomText</b></li> 
 * </ul>
 * @author Pierre Le Roux
 *
 */
public class AccueilActivity extends Activity {

	private EditText nomText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        
        Button buttonVersPageDeux = (Button)findViewById(R.id.buttonPageDeux);
        Button buttonVersPageTrois = (Button)findViewById(R.id.buttonPageTrois);
       
        nomText = (EditText)findViewById(R.id.nomText);
        
        buttonVersPageDeux.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// On émet un Intent pour aller vers une fonctionnalité dont la référence est fr.univbrest.masterdosi.android.PAGETROIS
				// Cette référence est déclarée via un intent-filter dans AndroidManifest.xml
				// Cet intent-filter est associé à l'Activity PageDeuxActivity
				Intent pageDeuxIntent = new Intent("fr.univbrest.masterdosi.android.PAGEDEUX");
				startActivity(pageDeuxIntent);
			}
		});
        
        buttonVersPageTrois.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Comme pour le bouton précédent l'Intent fait référence à une déclaration dans AndroidManifest.xml
				Intent pageTroisIntent = new Intent("fr.univbrest.masterdosi.android.PAGETROIS");
				// On ajoute un paramètre dans l'Intent. Ce paramètre pourra être récupéré dans l'Activity qui récupèrera l'Intent... à savoir PageTroisActivity. 
				// Voir le code dans onCreate de PageTroisActivity.java
				pageTroisIntent.putExtra("nom", nomText.getText().toString());
				startActivity(pageTroisIntent);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Lorsqu'on cliquera sur le menu en haut, on aura un menu correspondant au fichier res/menu/accueil.xml
        getMenuInflater().inflate(R.menu.accueil, menu);
        return true;
    }
    
}
