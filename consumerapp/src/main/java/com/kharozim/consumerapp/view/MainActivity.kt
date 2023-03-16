package com.kharozim.consumerapp.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.kharozim.consumerapp.R
import com.kharozim.consumerapp.databinding.ActivityMainBinding
import com.kharozim.consumerapp.databinding.DialogPreviewBinding
import com.kharozim.consumerapp.model.UserModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getAllUserFav(this)
        viewModel.listUser.observe(this, { result ->
            setListUser(result ?: emptyList())
        })

    }

    private fun setListUser(data: List<UserModel>) {
        val adapter = HomeAdapter(data)
        adapter.onClickItem(object : HomeAdapter.MyListener {
            override fun onClicked(position: Int, data: Any?) {
                data as UserModel
                showDialogPreview(data)
            }
        })

        binding.rvUserFav.adapter = adapter
    }

    private fun showDialogPreview(data: UserModel) {
        val dialog = Dialog(this, R.style.Theme_AppCompat_Dialog_MinWidth)
        val dialogBinding = DialogPreviewBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                android.R.color.transparent,
                null
            )
        )

        dialogBinding.run {
            ivUser.load(data.avatarUrl)
            tvName.text = data.name
        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }
}