package com.applications.whazzup.photomapp.ui.screens.search.filter

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import com.applications.whazzup.photomapp.ui.screens.filtered_photo_card_list.FilteredPhotoCardListScreen
import flow.Flow

class FilterView(context: Context, attrs: AttributeSet) : AbstractView<FilterScreen.FilterPresenter>(context, attrs), CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.filters_wrapper)
    lateinit var filterWrapper : LinearLayout


    override fun onFinishInflate() {
        super.onFinishInflate()
        (0..filterWrapper.childCount)
                .filter { filterWrapper.getChildAt(it) is LinearLayout }
                .forEach { i ->
                    (0..(filterWrapper.getChildAt(i) as LinearLayout).childCount)
                            .filter { (filterWrapper.getChildAt(i) as LinearLayout).getChildAt(it) is CheckBox }
                            .forEach { ((filterWrapper.getChildAt(i) as LinearLayout).getChildAt(it) as CheckBox).setOnCheckedChangeListener(this) }
                }
    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id){
            //Dishes
            R.id.filter_meat_dish_cb-> if(isChecked) mPresenter.dishList.add("meat") else mPresenter.dishList.remove("meat")
            R.id.filter_fish_dish_cb-> if(isChecked) mPresenter.dishList.add("fish") else mPresenter.dishList.remove("fish")
            R.id.filter_cheese_dish_cb-> if(isChecked) mPresenter.dishList.add("cheese") else mPresenter.dishList.remove("cheese")
            R.id.filter_vegetables_dish_cb-> if(isChecked) mPresenter.dishList.add("vegetables") else mPresenter.dishList.remove("vegetables")
            R.id.filter_fruit_dish_cb-> if(isChecked) mPresenter.dishList.add("fruits") else mPresenter.dishList.remove("fruits")
            R.id.filter_desert_dish_cb-> if(isChecked) mPresenter.dishList.add("desert") else mPresenter.dishList.remove("desert")
            R.id.filter_drink_dish_cb-> if(isChecked) mPresenter.dishList.add("drink") else mPresenter.dishList.remove("drink")
            //Colors
            R.id.card_color_red -> if(isChecked) mPresenter.colorList.add("red") else mPresenter.colorList.remove("red")
            R.id.card_color_orange -> if(isChecked) mPresenter.colorList.add("orange") else mPresenter.colorList.remove("orange")
            R.id.card_color_yellow -> if(isChecked) mPresenter.colorList.add("yellow") else mPresenter.colorList.remove("yellow")
            R.id.card_color_green -> if(isChecked) mPresenter.colorList.add("green") else mPresenter.colorList.remove("green")
            R.id.card_color_light_blue -> if(isChecked) mPresenter.colorList.add("lightBlue") else mPresenter.colorList.remove("lightBlue")
            R.id.card_color_blue -> if(isChecked) mPresenter.colorList.add("blue") else mPresenter.colorList.remove("blue")
            R.id.card_color_purple -> if(isChecked) mPresenter.colorList.add("violet") else mPresenter.colorList.remove("violet")
            R.id.card_color_brown -> if(isChecked) mPresenter.colorList.add("brown") else mPresenter.colorList.remove("brown")
            R.id.card_color_black -> if(isChecked) mPresenter.colorList.add("black") else mPresenter.colorList.remove("black")
            R.id.card_color_white -> if(isChecked) mPresenter.colorList.add("white") else mPresenter.colorList.remove("white")
            //Temperature
            R.id.filter_temperature_cold_cb -> if(isChecked) mPresenter.temperatureList.add("cold") else mPresenter.temperatureList.remove("cold")
            R.id.filter_temperature_hot_cb -> if(isChecked) mPresenter.temperatureList.add("hot") else mPresenter.temperatureList.remove("hot")
            R.id.filter_temperature_normal_cb -> if(isChecked) mPresenter.temperatureList.add("middle") else mPresenter.temperatureList.remove("middle")
            //Decor
            R.id.filter_decor_simple_cb -> if(isChecked) mPresenter.decorList.add("simple") else mPresenter.temperatureList.remove("simple")
            R.id.filter_decor_holiday_cb -> if(isChecked) mPresenter.temperatureList.add("holiday") else mPresenter.temperatureList.remove("holiday")
            //Light
            R.id.filter_light_natural_cb -> if(isChecked) mPresenter.lightList.add("natural") else mPresenter.lightList.remove("natural")
            R.id.filter_light_synthetic_cb -> if(isChecked) mPresenter.lightList.add("synthetic") else mPresenter.lightList.remove("synthetic")
            R.id.filter_light_mixes_cb -> if(isChecked) mPresenter.lightList.add("mixed") else mPresenter.lightList.remove("mixed")
            //Light direction
            R.id.filter_light_dir_back_cb -> if(isChecked) mPresenter.lightDirectionList.add("backLight") else mPresenter.lightDirectionList.remove("backLight")
            R.id.filter_light_dir_front_cb -> if(isChecked) mPresenter.lightDirectionList.add("direct") else mPresenter.lightDirectionList.remove("direct")
            R.id.filter_light_dir_mixed_cb -> if(isChecked) mPresenter.lightDirectionList.add("mixed") else mPresenter.lightDirectionList.remove("mixed")
            R.id.filter_light_dir_side_cb -> if(isChecked) mPresenter.lightDirectionList.add("sideLight") else mPresenter.lightDirectionList.remove("sideLight")
            //Light count
            R.id.light_count_1_rb -> if(isChecked) mPresenter.lighrCountList.add("one") else mPresenter.lighrCountList.remove("one")
            R.id.light_count_2_rb -> if(isChecked) mPresenter.lighrCountList.add("two") else mPresenter.lighrCountList.remove("two")
            R.id.light_count_3_rb -> if(isChecked) mPresenter.lighrCountList.add("three") else mPresenter.lighrCountList.remove("three")
        }
    }

    //region ================= AbstractView =================

    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DaggerFilterScreen_FilterPresenterComponent>(context).inject(this)
    }

    fun show() {
        Flow.get(this).set(FilteredPhotoCardListScreen(1, Object()))
    }

    //endregion

}
