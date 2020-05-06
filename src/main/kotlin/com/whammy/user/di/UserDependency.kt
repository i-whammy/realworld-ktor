package com.whammy.user.di

import com.whammy.user.driver.InMemoryUserDriver
import com.whammy.user.repository.UserDriver
import com.whammy.user.repository.UserRepository
import com.whammy.user.service.UserService
import com.whammy.user.usecase.IUserRepository
import com.whammy.user.usecase.UserUsecase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val userDependency = Kodein{
    bind<UserDriver>() with singleton { InMemoryUserDriver() }
    bind<UserService>() with singleton { UserService() }
    bind<IUserRepository>() with singleton { UserRepository(instance()) }
    bind<UserUsecase>() with singleton { UserUsecase(instance(), instance()) }
}