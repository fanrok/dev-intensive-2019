package ru.skillbranch.devintensive.ui.profile

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.ui.custom.TextToDraw
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel


class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false
    var errorRepo = false
    private lateinit var viewModel: ProfileViewModel
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        delegate.setLocalNightMode(mode)
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields) {
                v.text = it[k].toString()
            }
        }
        setAva(profile)
    }

    private fun setAva(profile: Profile) {
        val initials = Utils.toInitials(profile.firstName, profile.lastName)
        if (initials != null) {
            val tv = TypedValue()
            theme.resolveAttribute(R.attr.colorAccent, tv, true)

            val ava = TextToDraw(
                iv_avatar.layoutParams.width,
                iv_avatar.layoutParams.height,
                initials,
                Utils.convertSpToPx(this, 48),
                Color.WHITE,
                tv.data
            )
            iv_avatar.setImageBitmap(ava.draw())
        } else {
            iv_avatar.setImageResource(R.drawable.avatar_default)
        }
    }

    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {

        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)
        btn_edit.setOnClickListener {
            if (errorRepo) {
                et_repository.text.clear()
            }
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }

        et_repository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (s.length != 0) {
                    val str = s.toString()
                    errorRepo = when {
                        str.contains("enterprise") -> true
                        str.contains("features") -> true
                        str.contains("topics") -> true
                        str.contains("collections") -> true
                        str.contains("trending") -> true
                        str.contains("events") -> true
                        str.contains("marketplace") -> true
                        str.contains("pricing") -> true
                        str.contains("nonprofit") -> true
                        str.contains("customer-stories") -> true
                        str.contains("security") -> true
                        str.contains("login") -> true
                        str.contains("join") -> true
                        else -> false
                    }
                    if (!errorRepo) {
                        errorRepo =
                            !str.matches("^(((https://)?(www.)?)(github.com/)([A-z0-9-]{1,256})/?)\$".toRegex())
                    }
                    wr_repository.isErrorEnabled = errorRepo
                    wr_repository.error = if (errorRepo) "Невалидный адрес репозитория" else ""
                } else {
                    errorRepo = false
                    wr_repository.isErrorEnabled = errorRepo
                    wr_repository.error = ""
                }
            }
        })
    }

    private fun showCurrentMode(isEdit: Boolean) {


        val info = viewFields.filter {
            setOf(
                "firstName",
                "lastName",
                "about",
                "repository"
            ).contains(it.key)
        }
        for ((_, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? = if (isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }
}
