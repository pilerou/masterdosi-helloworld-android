package fr.univbrest.masterdosi.android.bean;

/**
 * Cette classe peut �tre utilis�e pour encapsuler toute r�ponse d'une AsyncTask.
 * Elle peut alors renseigner :
 * <ul>
 * <li>soit <code>result</code> (selon le type configur�) si le traitement contenu dans doInBackground est OK</li>
 * <li>soit <code>exception</code> si le traitement contenu dans doInBackground a lev� une exception</li>
 * </ul>
 * 
 * @author Pierre Le Roux
 *
 * @param <T> le type de contenu � renvoyer par une AsyncTask en cas de succ�s. Cela peut �tre n'importe quel objet.
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
