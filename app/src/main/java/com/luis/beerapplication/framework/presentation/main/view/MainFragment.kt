package com.luis.beerapplication.framework.presentation.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.luis.beerapplication.R
import com.luis.beerapplication.data.model.Bar
import com.luis.beerapplication.data.model.BarItem
import com.luis.beerapplication.databinding.FragmentMainBinding
import com.luis.beerapplication.framework.presentation.activity.viewModel.MainViewModel
import com.luis.beerapplication.framework.presentation.main.adapters.MainAdapter
import com.luis.beerapplication.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnMainAdapterClickListener {

    @Inject
    lateinit var mainAdapter: MainAdapter

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.titleApp)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.setupRecycleView()
        this.setupBottomNavigation()
        this.setupSearchView()
        this.setupObservers()
    }

    private fun setupRecycleView() {
        binding.recycleViewElements.adapter = this.mainAdapter
        binding.recycleViewElements.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationOption.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.all -> {
                    mainViewModel.selectedAll()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.favorites -> {
                    mainViewModel.selectedFavorites()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setupSearchView() {
        binding.searchViewByName.onActionViewExpanded()
        binding.searchViewByName.clearFocus()

        binding.searchViewByName.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(filterText: String?): Boolean {
                binding.recycleViewElements.adapter?.let {
                    (binding.recycleViewElements.adapter as MainAdapter).filter.filter(filterText)
                }
                return false
            }
        })
    }

    private fun setupObservers() {
        mainViewModel.fetchData.observe(viewLifecycleOwner) {
            setupObserverData(it)
        }
        mainViewModel.eventOnNavigationItemSelected.observe(viewLifecycleOwner) {
            setupObserverNavigationBottom(it)
        }
    }

    private fun setupObserverData(result: Resource<Serializable>) {
        when (result) {
            is Resource.Loading -> {
                binding.mainContainer.visibility = View.GONE
                binding.progressBarContainer.visibility = View.VISIBLE
            }

            is Resource.Success -> {
                this.mainAdapter.setData(result.data as Bar)
                this.mainAdapter.setOnMainAdapterClickListener(this)
                loadFavoritesFromDatabase()

                binding.mainContainer.visibility = View.VISIBLE
                binding.progressBarContainer.visibility = View.GONE
            }

            is Resource.Failure -> {

                binding.mainContainer.visibility = View.GONE
                binding.progressBarContainer.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
                Toast.makeText(
                    requireContext(),
                    "${result.exception}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupObserverNavigationBottom(id: Int) {
        when (id) {
            mainViewModel.SHOW_ALL -> {
                if (binding.recycleViewElements.adapter != null) {
                    (binding.recycleViewElements.adapter as MainAdapter).showAll(
                        binding.searchViewByName.query
                    )
                }
            }

            else -> {
                if (binding.recycleViewElements.adapter != null) {
                    (binding.recycleViewElements.adapter as MainAdapter).showFavorites(
                        binding.searchViewByName.query
                    )
                }
            }
        }
    }

    private fun loadFavoritesFromDatabase() {
        mainViewModel.getFavorites(this.mainAdapter)
    }

    override fun onItemClickListener(barItem: BarItem) {
        this.mainViewModel.item = barItem
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }
}