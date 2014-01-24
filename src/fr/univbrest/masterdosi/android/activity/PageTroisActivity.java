package fr.univbrest.masterdosi.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import fr.univbrest.masterdosi.android.R;
import fr.univbrest.masterdosi.android.asynctask.HelloWorldRestAsyncTask;
import fr.univbrest.masterdosi.android.bean.Response;

/**
 * Cette Activity affiche un layout res/activity_page_trois.xml qui contient un TextView vide dont l'id est <b>pageTroisLabel</b>.
 * Au démarrage de l'Activity, une AsyncTask est créée our faire un appel à un serveur REST.
 * En retour de cet appel au serveur REST, on affiche le résultat (en cas de succès) dans le TextView.
 * 
 * @author Pierre Le Roux
 */
public class PageTroisActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_trois);
		
		Intent intent = getIntent();
		String nom = intent.getStringExtra("nom");
		final TextView pageTroisLabel = (TextView)findViewById(R.id.pageTroisLabel);
		
		
		
		// TODO Décommenter cette ligne et commenter l'AsyncTask ci-dessous si vous souhaitez simplement afficher le "Hello <le nom saisi sur l'écran d'accueil>" 
		// pageTroisLabel.setText("Hello " + nom);
		
		HelloWorldRestAsyncTask helloWorldRestAsyncTask = new HelloWorldRestAsyncTask() {
			
			// On surcharge onPostExecute dans cette classe pour deux raisons :
			// - Cela nous permet d'accéder aux fonctions de l'activity getApplicationContext() et pageTroisLabel
			// - Cette fonction agit comme un Callback de la méthode doInBackground déclarée dans HelloWorldRestAsyncTask et 
			//   peut donc être implémentée différemment selon le traitement souhaité par l'Activity qui l'englobe
			@Override
			protected void onPostExecute(Response<String> result) {
				super.onPostExecute(result);
				
				// On vérifie si une exception a été rencontrée lors du traitement asynchrone. Si c'est le cas, on ouvre une alerte à l'écran (Toast)
				if(result.getException() != null) {
					
					Toast.makeText(getApplicationContext(), 
							"Une erreur s'est produite lors de la récupération du message helloworld :" + result.getException().getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e("CallHelloWorld", "Une erreur s'est produite lors de la récupération du message helloworld", result.getException());
					result.getException().printStackTrace();
				}
				// Sinon, on met à jour le contenu du champ avec le contenu du retour du service REST
				else {
					pageTroisLabel.setText("Voici la réponse obtenue du serveur : " + result.getResult());
				}
				
				
			}
			
			
		};
		
		helloWorldRestAsyncTask.execute(nom);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.page_trois, menu);
	
		return true;
		
	}

}
