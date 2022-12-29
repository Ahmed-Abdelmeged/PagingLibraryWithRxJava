package com.ahmedabdelmeged.pagingwithrxjava.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by faisalamir on 29/12/22
 * PagingLibraryWithRxJava
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2022 Frogobox Media Inc.
 * All rights reserved
 *
 */


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val binding : VB by lazy { getViewBinding() }

    abstract fun getViewBinding() : VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}
