package au.com.quik.quiklite.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import au.com.quik.quiklite.R
import au.com.quik.quiklite.model.Shift
import au.com.quik.quiklite.ui.people.PeopleFragment
import au.com.quik.quiklite.ui.shift.ShiftFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val shiftList: MutableList<Shift> = mutableListOf()
    var currentFragmentId = R.id.navigation_people

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener())
        changeFragment(PeopleFragment(), PeopleFragment.TAG)
    }

    private fun onNavigationItemSelectedListener(): BottomNavigationView.OnNavigationItemSelectedListener? {


        return BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (currentFragmentId == item.itemId){
                return@OnNavigationItemSelectedListener false
            } else {
                currentFragmentId = item.itemId
            }

            when(item.itemId){
                R.id.navigation_people-> {
                    changeFragment(PeopleFragment(), PeopleFragment.TAG)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_shifts-> {
                    changeFragment(ShiftFragment(), ShiftFragment.TAG)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun changeFragment(frag: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, frag, tag)
        transaction.commit()
    }
}