package fr.univbrest.masterdosi.android.asynctask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.google.gson.Gson;

import fr.univbrest.masterdosi.android.bean.Response;

/**
 * Cette AsyncTask a pour but d'effectuer un appel à un serveur REST
 * Seule la méthode <b>doInBackground</b> est implémentée directement dans cette classe.
 * La méthode <b>onPostExecute</b> doit être implémentée en surchargeant cette classe dans chaque Activity qui utilise cette AsyncTask. Cela permet à cette tâche de fonctionner comme un callback.
 * La méthode <b>onProgressUpdate</b> n'a pas d'intérêt à être implémentée car doInBackground ne publie jamais d'état d'avancement de la tâche
 * Cela aurait été nécessaire si plusieurs traitements étaient effectués dans doInBackground.
 * La publication d'une mise à jour se fait via l'appel à la méthode publishProgress de l'AsyncTask
 * @author Pierre Le Roux
 */
public class HelloWorldRestAsyncTask extends
		AsyncTask<String, String, Response<String>> {

	// Cette adresse est mise en dur ici mais aurait probablement vocation à être stockée dans un fichier de resources
	// L'adresse 192.168.56.1 correspond à l'adresse du poste local hébergeant un émulateur
	// Il s'agit de l'adresse passerelle permettant d'accéder au poste local
	// Le service REST doit être démarré sur le poste local du développeur (Cf : https://github.com/pilerou/masterdosi-helloworld-nodejs)
	private final String url = "http://192.168.56.1:3000/";
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected Response<String> doInBackground(String... nom) {

	        Response<String> result = new Response<String>();
	        
	        try {
	        	// On prépare les paramètres de la requête et on ajoute un paramètre nom
	        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        	nameValuePairs.add(new BasicNameValuePair("nom", nom[0]));
	        	HttpClient httpClient = new DefaultHttpClient();
	        	// On encode les paramètres avant ajout à l'url GET
	        	String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
	        	HttpGet httpGet = new HttpGet(url + "?" + paramsString);
	        	
	        	// On exécute la requête
	        	HttpResponse response = httpClient.execute(httpGet);
	        	
	        	// On vérifie si le code retour est de 200
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                
	            	// Si l'appel a bien fonctionné, alors on utilise la librairie GSON https://code.google.com/p/google-gson/ pour lire un flux JSON et le transformer en objet
	            	Gson gson = new Gson();
	            	ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                out.close();
	                
	                /* 
	                   fromJson transforme le flux en objet en effectuant le traitement suivant
	                   le flux retourné étant {result : 'hello Pierre'} la méthode fromJson tente de trouver une méthode setResult dans la classe Response<String> 
	                   et appelle cette méthode en lui transmettant la valeur associée  à la propriété result dans le flux de retour : "hello Pierre"
	                   On obtient alors un objet Response alimenté, contenant donc sa variable d'instance result : "Hello Pierre"
	                  
	                */
	                result = gson.fromJson(out.toString(), result.getClass());
	                
	                /* en complément, si le flux JSON retourné avait été 
	                   {
	                   	result : {
	                   		nom : 'Le Roux',
	                   		prenom : 'Pierre',
	                   		age : 32
	                   	}
	                   }
	                   
	                   au lieu de retourner un objet Result<String>, nous aurions dû retourner un objet Result<Personne>
	                   et donc créer un bean Personne contenant les variables d'instances (getters setters associés) :
	                     - String nom;
	                     - String prenom;
	                     - int age;
	                 */
	                
	            } else{
	                // On ferme la connexion et lève une exception
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (Exception e) {
	        	// Si une exception est rencontrée, alors on la stocke dans l'objet result de retour. On laissera alors chaque Activity déterminer le traitement à effectuer en cas d'erreur
	        	// Cf PageTroisActivity.java
	            result.setException(e);
	        }
	        return result;
	}	

}
