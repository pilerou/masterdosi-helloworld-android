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
 * Au d�marrage de l'Activity, une AsyncTask est cr��e our faire un appel � un serveur REST.
 * En retour de cet appel au serveur REST, on affiche le r�sultat (en cas de succ�s) dans le TextView.
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
		
		
		
		// TODO D�commenter cette ligne et commenter l'AsyncTask ci-dessous si vous souhaitez simplement afficher le "Hello <le nom saisi sur l'�cran d'accueil>" 
		// pageTroisLabel.setText("Hello " + nom);
		
		HelloWorldRestAsyncTask helloWorldRestAsyncTask = new HelloWorldRestAsyncTask() {
			
			// On surcharge onPostExecute dans cette classe pour deux raisons :
			// - Cela nous permet d'acc�der aux fonctions de l'activity getApplicationContext() et pageTroisLabel
			// - Cette fonction agit comme un Callback de la m�thode doInBackground d�clar�e dans HelloWorldRestAsyncTask et 
			//   peut donc �tre impl�ment�e diff�remment selon le traitement souhait� par l'Activity qui l'englobe
			@Override
			protected void onPostExecute(Response<String> result) {
				super.onPostExecute(result);
				
				// On v�rifie si une exception a �t� rencontr�e lors du traitement asynchrone. Si c'est le cas, on ouvre une alerte � l'�cran (Toast)
				if(result.getException() != null) {
					
					Toast.makeText(getApplicationContext(), 
							"Une erreur s'est produite lors de la r�cup�ration du message helloworld :" + result.getException().getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e("CallHelloWorld", "Une erreur s'est produite lors de la r�cup�ration du message helloworld", result.getException());
					result.getException().printStackTrace();
				}
				// Sinon, on met � jour le contenu du champ avec le contenu du retour du service REST
				else {
					pageTroisLabel.setText("Voici la r�ponse obtenue du serveur : " + result.getResult());
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
