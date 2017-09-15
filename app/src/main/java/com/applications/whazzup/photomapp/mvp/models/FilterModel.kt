package com.applications.whazzup.photomapp.mvp.models

class FilterModel : AbstractModel() {
    fun filter(dishList: MutableList<String>, colorList: MutableList<String>, decorList: MutableList<String>, temperatureList: MutableList<String>, lighrCountList: MutableList<String>, lightList: MutableList<String>, lightDirectionList: MutableList<String>) {
        mRealmManager.filter(dishList, colorList, decorList, temperatureList, lighrCountList, lightDirectionList, lightList)
    }
}
