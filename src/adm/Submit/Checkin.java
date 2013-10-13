package adm.Submit;

import adm.Repository.GlobalParameters;
import adm.User.User;
import android.content.Context;

public class Checkin {

	public Checkin() {
		super();
	}

	/**
	 * Make checkin in a _id_place with no _rate
	 * @param _context
	 * @param _id_place
	 * @throws Exception
	 */
	public SimpleResponse make_checkin(Context _context, String _id_place)
			throws Exception {
		Submit sb = new Submit(GlobalParameters.BASE_URL_JSON);
		User user = new User(GlobalParameters.BASE_URL_JSON, _context);
		if (user.hasAuthKey()) {
			return sb.checkin(_id_place, GlobalParameters.API_KEY, user.getAuthKey());
		} else {
			throw new Exception("No estas logueado");
		}

	}

	/**
	 * Make checkin in a _id_place with a _rate
	 * @param _context
	 * @param _id_place
	 * @param _rate
	 * @throws Exception
	 */
	public SimpleResponse make_checkin(Context _context, String _id_place, Double _rate)
			throws Exception {
		Submit sb = new Submit(GlobalParameters.BASE_URL_JSON);
		User user = new User(GlobalParameters.BASE_URL_JSON, _context);
		if (user.hasAuthKey()) {
			return sb.checkin(_id_place, _rate, GlobalParameters.API_KEY,
					user.getAuthKey());
		} else {
			throw new Exception("No estas logueado");
		}
		
	}

}
