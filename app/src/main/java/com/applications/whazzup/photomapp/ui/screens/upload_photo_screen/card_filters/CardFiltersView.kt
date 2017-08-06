package com.applications.whazzup.photomapp.ui.screens.upload_photo_screen.card_filters

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckBox

import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import butterknife.BindView
import butterknife.OnClick
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.mvp.views.AbstractView
import kotlinx.android.synthetic.main.content_card_parametrs.*

class CardFiltersView(context : Context, attrs : AttributeSet) : AbstractView<CardFiltersScreen.CardFilterPresenter>(context, attrs), CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.card_color_red)
    lateinit var mRedCheckBox : CheckBox
    @BindView(R.id.card_color_orange)
    lateinit var mOrangeCheckBox : CheckBox
    @BindView(R.id.card_color_yellow)
    lateinit var mYellowCheckBox : CheckBox
    @BindView(R.id.card_color_green)
    lateinit var mGreenCheckBox : CheckBox
    @BindView(R.id.card_color_light_blue)
    lateinit var mLightBlueCheckBox : CheckBox
    @BindView(R.id.card_color_blue)
    lateinit var mBlueCheckBox : CheckBox
    @BindView(R.id.card_color_purple)
    lateinit var mViolentCheckBox : CheckBox
    @BindView(R.id.card_color_brown)
    lateinit var mBrownCheckBox : CheckBox
    @BindView(R.id.card_color_white)
    lateinit var mWhiteCheckBox : CheckBox
    @BindView(R.id.card_color_black)
    lateinit var mBlackCheckBox : CheckBox
    @BindView(R.id.dish_rg)
    lateinit var mDishRadioGroup : RadioGroup
    @BindView(R.id.decor_rg)
    lateinit var mDecorRadioGroup : RadioGroup
    @BindView(R.id.temperature_rg)
    lateinit var mTemperatureRadioGroup : RadioGroup
    @BindView(R.id.light_rg)
    lateinit  var mLightRadioGroup : RadioGroup
    @BindView(R.id.light_direction_rg)
    lateinit  var mLightDirectionRadioGroup : RadioGroup
    @BindView(R.id.light_count_rg)
    lateinit  var mLightCountRadioGroup : RadioGroup

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id){
            R.id.card_color_red -> if(isChecked) mPresenter.saveNuances("red") else mPresenter.deleteNuances("red")
            R.id.card_color_orange -> if(isChecked) mPresenter.saveNuances("orange") else mPresenter.deleteNuances("orange")
            R.id.card_color_yellow -> if(isChecked) mPresenter.saveNuances("yellow") else mPresenter.deleteNuances("yellow")
            R.id.card_color_green -> if(isChecked) mPresenter.saveNuances("green") else mPresenter.deleteNuances("green")
            R.id.card_color_light_blue -> if(isChecked) mPresenter.saveNuances("lightBlue") else mPresenter.deleteNuances("lightBlue")
            R.id.card_color_black -> if(isChecked) mPresenter.saveNuances("black") else mPresenter.deleteNuances("black")
            R.id.card_color_blue-> if(isChecked) mPresenter.saveNuances("blue") else mPresenter.deleteNuances("blue")
            R.id.card_color_purple-> if(isChecked) mPresenter.saveNuances("violet") else mPresenter.deleteNuances("violet")
            R.id.card_color_brown->if(isChecked) mPresenter.saveNuances("brown") else mPresenter.deleteNuances("brown")
            R.id.card_color_white -> if(isChecked) mPresenter.saveNuances("white") else mPresenter.deleteNuances("white")
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mRedCheckBox.setOnCheckedChangeListener(this)
        mOrangeCheckBox.setOnCheckedChangeListener(this)
        mYellowCheckBox.setOnCheckedChangeListener(this)
        mGreenCheckBox.setOnCheckedChangeListener(this)
        mLightBlueCheckBox.setOnCheckedChangeListener(this)
        mBlueCheckBox.setOnCheckedChangeListener(this)
        mViolentCheckBox.setOnCheckedChangeListener(this)
        mBrownCheckBox.setOnCheckedChangeListener(this)
        mWhiteCheckBox.setOnCheckedChangeListener(this)
        mBlackCheckBox.setOnCheckedChangeListener(this)
        mDishRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.meat_dish_rb -> mPresenter.addDish("meat")
                R.id.fish_dish_rb -> mPresenter.addDish("fish")
                R.id.vegetables_dish_rb -> mPresenter.addDish("vegetables")
                R.id.fruit_dish_rb -> mPresenter.addDish("fruit")
                R.id.cheese_dish_rb -> mPresenter.addDish("cheese")
                R.id.desert_dish_rb -> mPresenter.addDish("dessert")
                R.id.drink_dish_rb -> mPresenter.addDish("drinks")
            }
        }
        mDecorRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.decor_simple_rb -> mPresenter.addDecor("simple")
                R.id.decor_holiday_rb -> mPresenter.addDecor("holiday")
            }
        }

        mTemperatureRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.temperature_cold_rb -> mPresenter.addTemperature("cold")
                R.id.temperature_normal_rb -> mPresenter.addTemperature("normal")
                R.id.temperature_hot_rb -> mPresenter.addTemperature("hot")
            }
        }

        mLightRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.light_natural_rb -> mPresenter.addLight("natural")
                R.id.light_synthetic_rb -> mPresenter.addLight("synthetic")
                R.id.light_mixes_rb -> mPresenter.addLight("mixed")
            }
        }

        mLightDirectionRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.light_dir_back -> mPresenter.addLightDirection("backLight")
                R.id.light_dir_front_rb -> mPresenter.addLightDirection("direct")
                R.id.light_dir_side ->mPresenter.addLightDirection("sideLight")
                R.id.light_dir_mixed -> mPresenter.addLightDirection("mixed")
            }
        }

        mLightCountRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.light_count_1_rb-> mPresenter.addLightCount("one")
                R.id.light_count_2_rb-> mPresenter.addLightCount("two")
                R.id.light_count_3_rb-> mPresenter.addLightCount("three")
            }
        }
    }


    override fun viewOnBackPressed(): Boolean {
       return false
    }

    override fun initDagger(context: Context?) {
        DaggerService.getDaggerComponent<DaggerCardFiltersScreen_CardFiltersComponent>(context!!).inject(this)
    }

}