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
 * Cette Activity a juste pour but d'afficher un message � l'�cran pour valider qu'on sait naviguer entre l'�cran d'accueil et l'�cran deux
 * L'�cran deux comporte un bouton de retour
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
				// En appelant la m�thode finish, on termine l'activity et on revient donc � l'�cran pr�c�dent
				PageDeuxActivity.this.finish();
				
			}
		});
		/*
		 // TODO D�commenter la partie en dessous si vous voulez afficher une alerte indiquant "Hello Pierre" en asynchrone apr�s appel au service REST
		  HelloWorldRestAsyncTask helloWorldRestAsyncTask = new HelloWorldRestAsyncTask() {
		 
			@Override
			protected void onPostExecute(Response<String> result) {
				super.onPostExecute(result);
				
				if(result.getException() != null) {
					
					Toast.makeText(getApplicationContext(), 
							"Une erreur s'est produite lors de la r�cup�ration du message helloworld :" + result.getException().getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e("CallHelloWorld", "Une erreur s'est produite lors de la r�cup�ration du message helloworld", result.getException());
					result.getException().printStackTrace();
				}
				else {
					Toast.makeText(getApplicationContext(), "Voil� la r�ponse : " + result.getResult(), Toast.LENGTH_LONG).show();
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
