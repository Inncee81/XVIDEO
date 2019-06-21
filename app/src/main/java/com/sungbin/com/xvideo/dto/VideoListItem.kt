package com.sungbin.com.xvideo.dto

class VideoListItem {
    var name: String? = null
    var link: String? = null

    constructor() {}

    constructor(
        name: String?,
        link: String?
    ) {
        this.name = name
        this.link = link
    }
}