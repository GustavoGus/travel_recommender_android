package adm.Repository;

/**
 * Clase para declarar las variables globales a toda la aplicacion.
 * 
 * @author Gustavo
 */
public class GlobalParameters {
	public static final double MAX_LATITUDE = 90;
	public static final double MAX_LONGITUDE = 180;
	/*
	 * Los datos para el concurso solo son de la provincia de Valencia. Limites
	 * no exactos.
	 */
	public static final double PROVINCIA_VAL_SOUTH = 38.840777;
	public static final double PROVINCIA_VAL_NORTH = 39.858628;
	public static final double PROVINCIA_VAL_EAST = -0.129089;
	public static final double PROVINCIA_VAL_WEST = -1.444702;

	public static final String BASE_URL_JSON = "beta.troovel.com/services/api/rest/json/";
	public static final String API_KEY = "ac9d02d1cd5a9e5fc427e9122a1d6da20e784658";
	public static final int COD_DIALOG_ALERTS = 1;

	public static final int ERROR_GENERAL = -1;
	public static final int QUOTA_EXCEEDED = 1;
	public static final int ERROR_REGISTRATION = 2;
	public static final int ERROR_DUPLICATE_ACTION = 3;
	public static final int ERROR_PLACE_EXISTS = 4;
	public static final int ERROR_INVALID_TYPE = 5;
	public static final int ERROR_NOT_FILE_UPLOADED = 6;
	public static final int ERROR_UPLOADED_NOT_IMAGE = 7;
	public static final int ERROR_WRONG_ARGUMENTS = 8;

	public static Double my_longitude = 0.0;
	public static Double my_latitude = 0.0;

	
	public static final int ENGLISH = 100;
	public static final int SPANISH = 101;
	public static final int DEFAULT = 102;
	public static String TWITTER_HASHTAG = "#BV_AndroIT";
	
	/* Tags para las busquedas */
	public static final String[] TAGS_BUSQUEDAS = {"CITY", "REGION", "COUNTRY",
			"CONTINENT", "NATURAL", "CULTURAL", "POI", "INFRASTRUCTURE",
			"HOTEL", "RESTAURANT", "PUB", "DISCO"};

	/* Traduccion de los tags */

	public static String traduceTagTipoSitio(String _tagTipoSitio) {
		if (_tagTipoSitio.equals("CITY"))
			return "Ciudad";
		else if (_tagTipoSitio.equals("REGION"))
			return "Regi�n";
		else if (_tagTipoSitio.equals("COUNTRY"))
			return "Pa�s";
		else if (_tagTipoSitio.equals("CONTINENT"))
			return "Continente";
		else if (_tagTipoSitio.equals("NATURAL"))
			return "Espacio natural";
		else if (_tagTipoSitio.equals("CULTURAL"))
			return "Espacio cultural";
		else if (_tagTipoSitio.equals("POI"))
			return "Punto de interes";
		else if (_tagTipoSitio.equals("INFRASTRUCTURE"))
			return "Infraestructura";
		else if (_tagTipoSitio.equals("HOTEL"))
			return "Hotel";
		else if (_tagTipoSitio.equals("RESTAURANT"))
			return "Restaurante";
		else if (_tagTipoSitio.equals("PUB"))
			return "Pub";
		else if (_tagTipoSitio.equals("DISCO"))
			return "Discoteca";
		else
			return "";
	}
}
