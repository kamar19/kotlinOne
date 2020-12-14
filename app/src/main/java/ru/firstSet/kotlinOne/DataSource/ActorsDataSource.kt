package ru.firstSet.kotlinOne.DataSource

import ru.firstSet.kotlinOne.Data.Actor

object ActorsDataSource  {
    private var actorsList :List<Actor> = listOf()
    fun getActorsList(): List<Actor> {
        return actorsList
    }

    fun setActorsList(actorList :List<Actor>){
        actorsList=actorList
    }
}