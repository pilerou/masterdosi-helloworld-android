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
 * Cette AsyncTask a pour but d'effectuer un appel � un serveur REST
 * Seule la m�thode <b>doInBackground</b> est impl�ment�e directement dans cette classe.
 * La m�thode <b>onPostExecute</b> doit �tre impl�ment�e en surchargeant cette classe dans chaque Activity qui utilise cette AsyncTask. Cela permet � cette t�che de fonctionner comme un callback.
 * La m�thode <b>onProgressUpdate</b> n'a pas d'int�r�t � �tre impl�ment�e car doInBackground ne publie jamais d'�tat d'avancement de la t�che
 * Cela aurait �t� n�cessaire si plusieurs traitements �taient effectu�s dans doInBackground.
 * La publication d'une mise � jour se fait via l'appel � la m�thode publishProgress de l'AsyncTask
 * @author Pierre Le Roux
 */
public class HelloWorldRestAsyncTask extends
		AsyncTask<String, String, Response<String>> {

	// Cette adresse est mise en dur ici mais aurait probablement vocation � �tre stock�e dans un fichier de resources
	// L'adresse 192.168.56.1 correspond � l'adresse du poste local h�bergeant un �mulateur
	// Il s'agit de l'adresse passerelle permettant d'acc�der au poste local
	// Le service REST doit �tre d�marr� sur le poste local du d�veloppeur (Cf : https://github.com/pilerou/masterdosi-helloworld-nodejs)
	private final String url = "http://192.168.56.1:3000/";
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected Response<String> doInBackground(String... nom) {

	        Response<String> result = new Response<String>();
	        
	        try {
	        	// On pr�pare les param�tres de la requ�te et on ajoute un param�tre nom
	        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        	nameValuePairs.add(new BasicNameValuePair("nom", nom[0]));
	        	HttpClient httpClient = new DefaultHttpClient();
	        	// On encode les param�tres avant ajout � l'url GET
	        	String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
	        	HttpGet httpGet = new HttpGet(url + "?" + paramsString);
	        	
	        	// On ex�cute la requ�te
	        	HttpResponse response = httpClient.execute(httpGet);
	        	
	        	// On v�rifie si le code retour est de 200
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                
	            	// Si l'appel a bien fonctionn�, alors on utilise la librairie GSON https://code.google.com/p/google-gson/ pour lire un flux JSON et le transformer en objet
	            	Gson gson = new Gson();
	            	ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                out.close();
	                
	                /* 
	                   fromJson transforme le flux en objet en effectuant le traitement suivant
	                   le flux retourn� �tant {result : 'hello Pierre'} la m�thode fromJson tente de trouver une m�thode setResult dans la classe Response<String> 
	                   et appelle cette m�thode en lui transmettant la valeur associ�e  � la propri�t� result dans le flux de retour : "hello Pierre"
	                   On obtient alors un objet Response aliment�, contenant donc sa variable d'instance result : "Hello Pierre"
	                  
	                */
	                result = gson.fromJson(out.toString(), result.getClass());
	                
	                /* en compl�ment, si le flux JSON retourn� avait �t� 
	                   {
	                   	result : {
	                   		nom : 'Le Roux',
	                   		prenom : 'Pierre',
	                   		age : 32
	                   	}
	                   }
	                   
	                   au lieu de retourner un objet Result<String>, nous aurions d� retourner un objet Result<Personne>
	                   et donc cr�er un bean Personne contenant les variables d'instances (getters setters associ�s) :
	                     - String nom;
	                     - String prenom;
	                     - int age;
	                 */
	                
	            } else{
	                // On ferme la connexion et l�ve une exception
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (Exception e) {
	        	// Si une exception est rencontr�e, alors on la stocke dans l'objet result de retour. On laissera alors chaque Activity d�terminer le traitement � effectuer en cas d'erreur
	        	// Cf PageTroisActivity.java
	            result.setException(e);
	        }
	        return result;
	}	

}
