package nl.johnny.movemate.repositories

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface IRepositoryBinder {
    fun <T> getUserRepository(owner: T): IUserRepository where T : LifecycleOwner, T : Context
}