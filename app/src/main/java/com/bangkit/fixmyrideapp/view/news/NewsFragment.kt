package com.bangkit.fixmyrideapp.view.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.data.adapter.NewsAdapter
import com.bangkit.fixmyrideapp.data.response.NewsData
import com.bangkit.fixmyrideapp.data.utils.Result
import com.bangkit.fixmyrideapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModel.NewsFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        newsViewModel.getNews().observe(requireActivity()){
            when(it){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val error = it.error
                    binding.tvError.text = error
                    binding.tvError.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    showNewsData(it.data)
                }
            }
        }
    }

    private fun showNewsData(data: List<NewsData>) {
        val adapter = NewsAdapter()
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(data)
    }
}