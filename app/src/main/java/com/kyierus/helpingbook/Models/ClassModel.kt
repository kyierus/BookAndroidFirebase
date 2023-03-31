package com.kyierus.helpingbook.Models

data class ClassModel(var classId:Int,
                      var className:String,
                      var classLogo:String)
{
    constructor():this(
        0,
        "",
        ""
    )
}
