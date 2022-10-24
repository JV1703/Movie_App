package com.example.details.presentation.viewmore.crew

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.CrewList
import com.example.details.presentation.viewmore.casts.model.Items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewMoreCrewsViewModel : ViewModel() {

    suspend fun generateCrewList(crews: CrewList): List<Items> {
        return withContext(Dispatchers.Default) {
            val returnList = arrayListOf<Items>()

            val crewList = crews.crews

            returnList.add(Items.Header("Crews", crewList.size))
            crewList.map { it.department }
                .distinct()
                .sortedBy { it }
                .forEach { department ->
                    returnList.add(Items.SubHeader(department ?: "Unknown"))
                    crewList
                        .filter { it.department == department }
                        .sortedWith(compareBy({ it.job }, { it.name }))
                        .forEach { crew ->
                            returnList.add(Items.CrewDetails(crew))
                        }
                }
            returnList
        }
    }

    //    suspend fun generateList(credits: Credits): List<Items> {
//        return withContext(Dispatchers.Default) {
//            val returnList = arrayListOf<Items>()
//
//            val casts = credits.casts
//            val crews = credits.crews
//
//            returnList.add(Items.Header("Casts", casts.size))
//            casts.map {
//                returnList.add(Items.CastDetails(it))
//            }
//            returnList.add(Items.Header("Crews", crews.size))
//            crews.map { it.department }
//                .distinct()
//                .sortedBy { it }
//                .forEach { department ->
//                    returnList.add(Items.SubHeader(department ?: "Unknown"))
//                    crews
//                        .filter { it.department == department }
//                        .sortedWith(compareBy({ it.job }, { it.name }))
//                        .forEach { crew ->
//                            returnList.add(Items.CrewDetails(crew))
//                        }
//                }
//            returnList
//        }
//    }

}