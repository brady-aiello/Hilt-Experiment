package com.codingwithmitch.hiltexperiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

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
class SomeClass @Inject constructor(
        private val someInterface: SomeInterface,
        private val gson: Gson
) {
    fun doAThing(): String = "Look I got a thing" //${someInterface.getAThing()}"
}

class SomeInterfaceImpl
@Inject
constructor(): SomeInterface {
    override fun getAThing() : String {
        return "A Thing"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

/*@InstallIn(ActivityComponent::class)
@Module
abstract class MyModule {
    @ActivityScoped
    @Binds
    abstract fun bindSomeDependency(
            someImpl: SomeInterfaceImpl
    ): SomeInterface

    @ActivityScoped
    @Binds
    // This won't compile, because Dagger doesn't know how to instantiate Gson.
    abstract fun bindGson(
            gson: Gson
    ): Gson
}*/
@InstallIn(ActivityComponent::class)
@Module
class MyModule {

    @ActivityScoped
    @Provides
    fun provideSomeInterface(): SomeInterface = SomeInterfaceImpl()

    @ActivityScoped
    @Provides
    fun provideGson(): Gson = Gson()
}