package com.example.search.di.presentation

import android.content.Context
import com.example.core.di.dependency.SharedDependency
import com.example.search.presentation.SearchFragment
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.scopes.ViewModelScoped

@Component(dependencies = [SharedDependency::class], modules = [PresentationModule::class])
@ViewModelScoped
interface PresentationComponent {

    fun inject(searchFragment: SearchFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(sharedDependency: SharedDependency): Builder
        fun build(): PresentationComponent
    }

}