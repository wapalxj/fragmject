package com.example.miaow.picture.selector.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.example.fragment.library.base.adapter.BaseAdapter
import com.example.fragment.library.base.utils.screenWidth
import com.example.miaow.picture.databinding.PictureSelectorItemBinding
import com.example.miaow.picture.selector.bean.MediaBean

class PictureSelectorAdapter : BaseAdapter<MediaBean>() {

    private val selectPosition: MutableList<Int> = ArrayList()

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return PictureSelectorItemBinding::inflate
    }

    @SuppressLint("SetTextI18n")
    override fun onItemView(holder: ViewBindHolder, position: Int, item: MediaBean) {
        val binding = holder.binding as PictureSelectorItemBinding
        binding.root.layoutParams.apply {
            height = screenWidth() / 4
        }
        binding.image.load(item.uri)
        if (selectPosition.contains(position)) {
            if (!selectPosition.contains(position)) {
                selectPosition.add(position)
            }
            binding.dim.alpha = 0.5f
            binding.serial.text = (selectPosition.indexOf(position) + 1).toString()
            binding.originalBox.isSelected = true
        } else {
            if (selectPosition.contains(position)) {
                selectPosition.remove(position)
            }
            binding.dim.alpha = if (selectPosition.size < 9) 0f else 0.9f
            binding.serial.text = ""
            binding.originalBox.isSelected = false
        }
        binding.originalBox.setOnClickListener {
            if (selectPosition.contains(position)) {
                selectPosition.remove(position)
            } else if (selectPosition.size < 9) {
                selectPosition.add(position)
            }
            notifyItemChanged(position)
            selectPosition.forEach {
                notifyItemChanged(it)
            }
        }
    }

    fun setAlbumData(data: List<MediaBean>) {
        selectPosition.clear()
        setNewData(data)
    }

    fun setSelectPosition(data: List<Int>) {
        selectPosition.clear()
        selectPosition.addAll(data)
    }

    fun getSelectPosition(): List<Int> {
        return selectPosition
    }

    fun getSelectPositionData(): List<MediaBean> {
        val data: MutableList<MediaBean> = ArrayList()
        selectPosition.forEach { position ->
            data.add(getItem(position))
        }
        return data
    }

}