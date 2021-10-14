package com.example.headsup

class CelebrityInfo {

    var celebrity : List<Info>? = null

    class Info{

        var name: String? = null
        var taboo1: String? = null
        var taboo2: String? = null
        var taboo3: String? = null
        var pk : Int? = null

        constructor( name: String?,  taboo1: String?, taboo2: String?, taboo3: String?, pk : Int?){

            this.name = name
            this.taboo1 = taboo1
            this.taboo2 = taboo2
            this.taboo3 = taboo3
            this.pk = pk
        }


    }
}