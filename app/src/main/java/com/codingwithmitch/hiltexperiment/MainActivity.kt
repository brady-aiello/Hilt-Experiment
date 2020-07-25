package com.codingwithmitch.hiltexperiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing())
    }
}

@AndroidEntryPoint
class MyFragment: Fragment() {
    @Inject
    lateinit var someClass: SomeClass
}

@ActivityScoped
//@FragmentScoped //<-- Scoping to Fragment Scope means the
/* Activity will not have access to it. So, our someClass
 field in MainActivity can't be injected properly. At compile,
 you will get an error like this:
 error: [Dagger/IncompatiblyScopedBindings]
 com.codingwithmitch.hiltexperiment.MyApplication_HiltComponents
 .ActivityC scoped with @dagger.hilt.android.scopes.ActivityScoped
 may not reference bindings with different scopes:
*/
class SomeClass @Inject constructor() {
    fun doAThing(): String = "Look I did a thing!"
}
