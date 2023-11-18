package com.fortetigre.gamibrjo.data

class Time(val minutes:Int, val seconds:Int) {

    fun getString():String{
        val res = StringBuilder()
        if (minutes<10) res.append("0${minutes}:")
        else res.append("${minutes}:")
        if (seconds<10) res.append("0${seconds}")
        else res.append(seconds.toString())

        return res.toString()
    }

}