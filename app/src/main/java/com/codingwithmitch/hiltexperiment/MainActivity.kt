package com.codingwithmitch.hiltexperiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Qualifier

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing1())
        println(someClass.doAThing2())
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
        @Impl1 private val someInterfaceImpl1: SomeInterface,
        @Impl2 private val someInterfaceImpl2: SomeInterface
) {
    fun doAThing1(): String = "Look I got a ${someInterfaceImpl1.getAThing()}"
    fun doAThing2(): String = "Look I got a ${someInterfaceImpl2.getAThing()}"
}

class SomeInterfaceImpl1
@Inject
constructor(): SomeInterface {
    override fun getAThing() : String {
        return "A Thing 1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor(): SomeInterface {
    override fun getAThing() : String {
        return "A Thing 2"
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

    @Impl1
    @ActivityScoped
    @Provides
    fun provideSomeInterface1(): SomeInterface = SomeInterfaceImpl1()

    @Impl2
    @ActivityScoped
    @Provides
    fun provideSomeInterface2(): SomeInterface = SomeInterfaceImpl2()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2