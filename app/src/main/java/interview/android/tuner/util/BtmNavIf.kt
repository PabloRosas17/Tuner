package interview.android.tuner.util

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

/* @desc this class will enforce the bottom navigation menu expectations
* @return menu views associated with the btm nav bar
 *  @BtmNamView  menu view
 *  @ComingSoon  menu item
 *  @HotTracks   menu item
 *  @NewReleases menu item
 *  @TopAlbums   menu item
 *  @TopSongs    menu item*/
interface BtmNavIf: BottomNavigationView.OnNavigationItemSelectedListener, IntentCapableIf {

    /* the bottom navigation view associated with this */
    var mBtmNavView: BottomNavigationView

    /* generalized action for when nav item is selected */
    override fun onNavigationItemSelected(item: MenuItem): Boolean
}