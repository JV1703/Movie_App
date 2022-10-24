package com.example.details.presentation.viewmore.casts.model

import com.example.core.data.source.remote.response.movie_details.Cast
import com.example.core.data.source.remote.response.movie_details.Crew

sealed class Items {

    data class CrewDetails(val crew: Crew) : Items()
    data class CastDetails(val cast: Cast) : Items()
    data class Header(val header:String, val headerDetails: Int) : Items()
    data class SubHeader(val subHeader:String) : Items()

}