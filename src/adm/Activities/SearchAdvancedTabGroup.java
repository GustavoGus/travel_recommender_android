/**
 * 
 */
package adm.Activities;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author Usuario
 *
 */
public class SearchAdvancedTabGroup extends TabGroupActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("SearchAdvancedActivity", new Intent(this,SearchAdvancedActivity.class));
    }
}
