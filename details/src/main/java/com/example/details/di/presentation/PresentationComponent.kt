package com.example.details.di.presentation

import android.content.Context
import com.example.core.di.dependency.SharedDependency
import com.example.details.presentation.DetailsActivity
import com.example.details.presentation.details.DetailsFragment
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.scopes.ViewModelScoped

@Component(dependencies = [SharedDependency::class], modules = [PresentationModule::class])
@ViewModelScoped
interface PresentationComponent {

    fun inject(detailsActivity: DetailsActivity)
    fun inject(detailsFragment: DetailsFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(sharedDependency: SharedDependency): Builder
        fun build(): PresentationComponent
    }
}