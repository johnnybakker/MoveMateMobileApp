package nl.johnny.movemate.repositories

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface IRepositoryBinder {
    fun getUserRepository(context: Context): IUserRepository
    fun getSessionRepository(context: Context): ISessionRepository
}