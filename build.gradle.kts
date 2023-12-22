// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
}

allprojects {
    repositories {
        google() // This indicates Google's Maven repository.
        jcenter() // This represents the jcenter repository.
    }
}