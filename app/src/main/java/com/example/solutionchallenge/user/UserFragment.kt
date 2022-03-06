package com.example.solutionchallenge.user

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solutionchallenge.R
import com.example.solutionchallenge.api.Item
import com.example.solutionchallenge.databinding.FragmentUserBinding
import com.example.solutionchallenge.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment(R.layout.fragment_user) {

    private val binding by viewBinding(FragmentUserBinding::bind)
    private val viewModel: UserFragmentViewModel by viewModel()

    private val userAdapter: UserAdapter by lazy { UserAdapter() }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservers()
        setupListeners()
        setupClickListener()
    }

    private fun setupListeners(){
        binding.apply {
            search.editText?.addTextChangedListener {
                it?.let {
                 userButton.isEnabled = validateField()
                }
            }
        }
    }

    private fun setupClickListener(){
        binding.apply {
            userButton.setOnClickListener {
                viewModel.resetState()
                viewModel.loadUsers(search.editText?.text.toString(), PER_PAGE)
            }
        }
    }

    private fun setupAdapter(){
        binding.apply {
            userList.apply {
                adapter = userAdapter
                addOnScrollListener(this@UserFragment.scrollListener)
            }
        }
    }

    private fun setupObservers(){
        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                UserUiState.Loading -> renderLoading()
                is UserUiState.Success -> renderSuccess(it.items)
                is UserUiState.Error -> renderError(getString(it.title),getString(it.message))
                UserUiState.EmptyList -> renderEmptyError(getString(R.string.something_went_wrong),getString(
                                      R.string.user_not_found))

            }
        }
    }

    private fun validateField(): Boolean{
        binding.apply {
            var validated = true
            if(search.editText?.text.toString().isEmpty()) validated = false
            return validated
        }
    }

    private fun renderSuccess(items: MutableList<Item>){
        binding.apply {
            loading.isVisible = false
            userList.isVisible = true
            isLoading = false
            userAdapter.differ.submitList(items.toList())
        }
    }

    private fun renderEmptyError(title: String,message: String){
        binding.apply {
            loading.isVisible = false
            userList.isVisible = false
            isLoading = false
            showDialog(title, message)
        }
    }

    private fun renderError(title: String,message: String){
        binding.apply {
            loading.isVisible = false
            isLoading = false
            showDialog(title, message)
        }
    }

    private fun renderLoading(){
        binding.apply {
            loading.isVisible = true
            isLoading = true
        }
    }

    private fun showDialog(title: String,message: String){
        activity?.let {
            val dialog = Dialog(it)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.dialog_notification)
            dialog.window?.apply {
                setBackgroundDrawableResource(android.R.color.transparent)
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
            dialog.show()
            val titleView: TextView = dialog.findViewById(R.id.title)
            val description: TextView = dialog.findViewById(R.id.description)
            val closeButton: Button = dialog.findViewById(R.id.closeButton)

            titleView.text = title
            description.text = message

            closeButton.setOnClickListener {
                dialog.dismiss()
            }

        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PER_PAGE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                binding.apply {
                    viewModel.loadUsers(search.editText?.text.toString(), PER_PAGE)
                    isScrolling = false
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    companion object{
        const val PER_PAGE = 9
    }
}