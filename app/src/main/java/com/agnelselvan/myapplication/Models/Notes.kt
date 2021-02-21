package com.agnelselvan.myapplication.Models

class Notes {
    var id: Int? = 0;
    var title: String? = "";
    var subTitle: String? = "";
    var datetime: String? = "";
    var noteText: String? = "";
    var imgPath: String? = "";
    var webLink: String? = "";
    var color: String? = "";

    constructor(title: String, subTitle: String, datetime: String, noteText: String, imgPath: String, webLink: String, color: String) {
        this.title = title
        this.subTitle = subTitle
        this.datetime = datetime
        this.noteText = noteText
        this.imgPath = imgPath
        this.webLink = webLink
        this.color = color
    }


    constructor( id: Int ,title: String, subTitle: String, datetime: String, noteText: String, imgPath: String, webLink: String, color: String) {
        this.id = id
        this.title = title
        this.subTitle = subTitle
        this.datetime = datetime
        this.noteText = noteText
        this.imgPath = imgPath
        this.webLink = webLink
        this.color = color
    }

    constructor(){}

    
}