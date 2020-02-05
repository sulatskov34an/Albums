package ru.sulatskov.base.view

interface BaseViewInterface{
    fun showProgress()
    fun hideProgress()
    fun getToolbarTitle() : String
    fun getHasHomeUp() : Boolean
}
