package com.test.edu.kotlin_fb_demo.ui

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.test.edu.kotlin_fb_demo.R
import com.test.edu.kotlin_fb_demo.frag.RootFragment
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : RootActivity() {

    // viewPager의 어댑터 (view Fragment의 어댑터)
    private var mPagerAdapter: FragmentPagerAdapter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    init {
        mPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            private var mFragments = arrayOf<Fragment>(RootFragment(), RootFragment(), RootFragment())
            // viewPager가 요청하는 특정 화면
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            //viewPager에 보여질 총 화면 갯수
            override fun getCount(): Int {
                return mFragments.size
            }

            //fragment의 제목을 지정할 경우
            override fun getPageTitle(position: Int): CharSequence? {
                return position.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        // viewPager에 화면 공급자를 연결
        viewPager.adapter = mPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> navigation.selectedItemId = R.id.navigation_home
                    1 -> navigation.selectedItemId = R.id.navigation_dashboard
                    2 -> navigation.selectedItemId = R.id.navigation_notifications
                }
            }
        })
    }

    // 로그아웃, 글쓰기 기능 넣기
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_newpost -> newPost()
            R.id.menu_signout -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }
    // 메뉴 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side, menu)
        return true
    }

    fun newPost() {
        // 새글 쓰기 화면으로 이동
        startActivity(Intent(this, NewPostActivity::class.java))
    }

    override fun signOut() {
        super.signOut()
        finish()
    }

}
