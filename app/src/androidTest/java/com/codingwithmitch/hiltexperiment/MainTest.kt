package com.codingwithmitch.hiltexperiment

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.codingwithmitch.hiltexperiment.di.AppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@UninstallModules(AppModule::class)
@HiltAndroidTest
class MainTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var someString: String

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun someTest() {
        assertThat(someString, containsString("TESTING"))
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    object TestAppModule {
        @Singleton
        @Provides
        fun provideSomeString(): String = "It's some TESTING string!"
    }
}