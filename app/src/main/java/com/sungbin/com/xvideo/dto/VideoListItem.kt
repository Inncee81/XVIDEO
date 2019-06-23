package com.sungbin.com.xvideo.dto

class VideoListItem {
    var name: String? = null
    var link: String? = null
    var thumbnail: String? = null
    var main: String? = null

    constructor() {}

    constructor(
        name: String?,
        link: String?,
        thumbnail: String?,
        main: String?
    ) {
        this.name = name
        this.link = link
        this.thumbnail = thumbnail
        this.main = main
    }
}