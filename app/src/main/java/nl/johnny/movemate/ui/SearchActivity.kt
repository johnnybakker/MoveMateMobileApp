package nl.johnny.movemate.ui

import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.repositories.IRepositoryBinder

class SearchActivity : BaseActivity() {



    override fun onRepositoryBinder(binder: IRepositoryBinder) {
        setContent {
            val users = remember { mutableStateListOf<User>() }




        }
    }


}