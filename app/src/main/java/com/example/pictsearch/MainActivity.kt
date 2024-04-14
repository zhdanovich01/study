package com.example.pictsearch

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaSession2Service.MediaNotification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pictsearch.auth.LoginFragment
import com.example.pictsearch.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var SplashScreenFragment : SplashFragment
    private lateinit var LoginFragment: LoginFragment
    private lateinit var MainScreenFragment: MainScreenFragment
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var personFragment: PersonFragment
    private lateinit var forumFragment: ForumFragment
    private lateinit var courseCountFragment: CourseCountFragment
    private lateinit var connectStepikFragment: ConnectStepikFragment
    private lateinit var subjectsFragment: SubjectsFragment
    private lateinit var itmoFragment: ItmoFragment
    private lateinit var universityCoursesFragment: UniversityCoursesFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var pmIFragment: PMIFragment
    private lateinit var favFragment: FavFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SplashScreenFragment = SplashFragment()
        LoginFragment = LoginFragment()
        MainScreenFragment = MainScreenFragment()
        personFragment = PersonFragment()
        forumFragment = ForumFragment()
        courseCountFragment = CourseCountFragment()
        connectStepikFragment = ConnectStepikFragment()
        subjectsFragment = SubjectsFragment()
        itmoFragment = ItmoFragment()
        universityCoursesFragment = UniversityCoursesFragment()
        searchFragment = SearchFragment()
        pmIFragment = PMIFragment()
        favFragment = FavFragment()


        val sharedPreferences : SharedPreferences = this.getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        setFragmentSplashScreen(SplashScreenFragment)

        object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val transition: Transition = Slide(Gravity.TOP)
                transition.duration = 300
                transition.addTarget(splashscreen)

                TransitionManager.beginDelayedTransition(main, transition)
                splashscreen.visibility = View.GONE
                var getlog = sharedPreferences.getString("log", "none")
                if (getlog != "yes"){
                    reglog.visibility = View.VISIBLE
                    setFragmentReglog(LoginFragment)

                }else{
                    allframes.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.VISIBLE
                    setAllFragment(MainScreenFragment)
                }
            }
        }.start()


        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    setAllFragment(MainScreenFragment)
                    true
                }
                R.id.navigation_search -> {
                    setAllFragment(searchFragment)
                    true
                }
                R.id.navigation_forum -> {
                    setAllFragment(forumFragment)
                    true
                }
                R.id.navigation_profile -> {
                    setAllFragment(personFragment)
                    true
                }
                else -> false
            }
        }





    }

    fun gotosubjects(){
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(subjectsFragment)
    }

    fun makemainvisible() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(MainScreenFragment)
    }

    fun gotoforum() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(forumFragment)
    }


    fun gotouniversity() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(universityCoursesFragment)
    }

    fun gotopmifragment() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(pmIFragment)
    }

    fun gotoconnectstepik() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(connectStepikFragment)
    }




    fun gotoitmo() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(itmoFragment)
    }

    fun gotonotes() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(favFragment)
    }


    fun gotoprofile() {
        allframes.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        reglog.visibility = View.GONE
        setAllFragment(personFragment)
    }

    private fun setAllFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.allframes, fragment)
        fragmentTransaction.commit()
    }

    private fun setFragmentSplashScreen(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.splashscreen, fragment)
        fragmentTransaction.commit()
    }

    private fun setFragmentReglog(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.reglog, fragment)
        fragmentTransaction.commit()
    }
}