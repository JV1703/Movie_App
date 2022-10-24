package com.example.bookmark.di.presentation

import android.content.Context
import com.example.bookmark.presentation.BookmarkFragment
import com.example.core.di.dependency.SharedDependency
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.scopes.ViewModelScoped

@Component(dependencies = [SharedDependency::class], modules = [PresentationModule::class])
@ViewModelScoped
interface PresentationComponent {

    fun inject(bookmarkFragment: BookmarkFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(sharedDependency: SharedDependency): Builder
        fun build(): PresentationComponent
    }

}