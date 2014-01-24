package fr.univbrest.masterdosi.android.bean;

/**
 * Cette classe peut être utilisée pour encapsuler toute réponse d'une AsyncTask.
 * Elle peut alors renseigner :
 * <ul>
 * <li>soit <code>result</code> (selon le type configuré) si le traitement contenu dans doInBackground est OK</li>
 * <li>soit <code>exception</code> si le traitement contenu dans doInBackground a levé une exception</li>
 * </ul>
 * 
 * @author Pierre Le Roux
 *
 * @param <T> le type de contenu à renvoyer par une AsyncTask en cas de succès. Cela peut être n'importe quel objet.
 */
public class Response<T> {

	Exception exception;
	T result;
	
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}


}
