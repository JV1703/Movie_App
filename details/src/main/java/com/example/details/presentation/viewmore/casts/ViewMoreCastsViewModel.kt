package com.example.details.presentation.viewmore.casts

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.CastList
import com.example.details.presentation.viewmore.casts.model.Items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewMoreCastsViewModel : ViewModel() {

    suspend fun generateCastList(casts: CastList): List<Items> {
        return withContext(Dispatchers.Default) {
            val returnList = arrayListOf<Items>()

            val castList = casts.casts

            returnList.add(Items.Header("Casts", castList.size))
            castList.map {
                returnList.add(Items.CastDetails(it))
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