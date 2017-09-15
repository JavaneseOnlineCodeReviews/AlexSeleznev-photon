package com.applications.whazzup.photomapp.data.storage.dto

class FiltersDto() {
    var dishes = mutableListOf<String>("meat", "fish", "vegetables", "fruit", "cheese", "dessert", "drinks")
    var nuances = mutableListOf<String>("red", "orange", "yellow", "green", "lightBlue", "blue", "violet", "brown", "black", "white")
    var decor = mutableListOf<String>("simple", "holiday")
    var temperature = mutableListOf<String>("hot", "middle", "cold")
    var light = mutableListOf<String>("natural", "synthetic", "mixed")
    var lightDirection = mutableListOf<String>("direct", "backLight", "sideLight", "mixed")
    var lightSource = mutableListOf<String>("one", "two", "three")

    constructor (dishList : MutableList<String>, nuancesList : MutableList<String>, decorList : MutableList<String>, temperatureList : MutableList<String>,
                lightList : MutableList<String>, lightDirectionList : MutableList<String>, lightSourceList : MutableList<String>) : this() {
        if(dishList !=null){
        this.dishes = dishList}

        if(nuancesList!=null){
            this.nuances = nuancesList
        }

        if(decorList!=null){
            this.decor = decorList
        }

        if(temperatureList!=null){
            this.temperature = temperatureList
        }

        if(lightList!=null){
            this.light = lightList
        }
    }
}