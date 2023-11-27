package com.example.birdquest

class ModelObservation {
    //variables match as in firebase
    var id:String =""
    var birdName:String =""
    var birdDescription:String =""
    var uid:String =""

    //empty constructor, required by firebase
    constructor()

    //parameterized constructor
    constructor(id: String, birdName: String, birdDescription: String, uid: String) {
        this.id = id
        this.birdName = birdName
        this.birdDescription = birdDescription
        this.uid = uid
    }

}