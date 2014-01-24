package fr.univbrest.masterdosi.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import fr.univbrest.masterdosi.android.R;
import fr.univbrest.masterdosi.android.asynctask.HelloWorldRestAsyncTask;
import fr.univbrest.masterdosi.android.bean.Response;

/**
 * Cette Activity a juste pour but d'afficher un message à l'écran pour valider qu'on sait naviguer entre l'écran d'accueil et l'écran deux
 * L'écran deux comporte un bouton de retour
 * @author Pierre Le Roux
 *
 */
public class PageDeuxActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_deux);
		
		Button buttonRetour = (Button)findViewById(R.id.buttonRetour);
		
		buttonRetour.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// En appelant la méthode finish, on termine l'activity et on revient donc à l'écran précédent
				PageDeuxActivity.this.finish();
				
			}
		});
		/*
		 // TODO Décommenter la partie en dessous si vous voulez afficher une alerte indiquant "Hello Pierre" en asynchrone après appel au service REST
		  HelloWorldRestAsyncTask helloWorldRestAsyncTask = new HelloWorldRestAsyncTask() {
		 
			@Override
			protected void onPostExecute(Response<String> result) {
				super.onPostExecute(result);
				
				if(result.getException() != null) {
					
					Toast.makeText(getApplicationContext(), 
							"Une erreur s'est produite lors de la récupération du message helloworld :" + result.getException().getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e("CallHelloWorld", "Une erreur s'est produite lors de la récupération du message helloworld", result.getException());
					result.getException().printStackTrace();
				}
				else {
					Toast.makeText(getApplicationContext(), "Voilà la réponse : " + result.getResult(), Toast.LENGTH_LONG).show();
				}
				
				
			}
			
			
		};
		
		helloWorldRestAsyncTask.execute("Pierre");
		
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Lorsqu'on cliquera sur le menu en haut, on aura un menu correspondant au fichier res/menu/page_deux.xml
		getMenuInflater().inflate(R.menu.page_deux, menu);
		return true;
	}

}
