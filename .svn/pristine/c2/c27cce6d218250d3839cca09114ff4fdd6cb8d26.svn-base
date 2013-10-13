/**
 * 
 */
package adm.Activities;

import adm.Repository.GlobalParameters;
import adm.User.User;
import android.content.Intent;
import android.os.Bundle;

public class DisfrutarTabGroup extends TabGroupActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		User user = new User(GlobalParameters.BASE_URL_JSON, this);

		if (user.hasAuthKey()) {
			startChildActivity("DisfrutarActivity", new Intent(this,
					DisfrutarActivity.class));
		} else
			startChildActivity("UserLoginActivity", new Intent(this,
					UserLoginActivity.class));
	}


}
