package com.example.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splash.api.RestApiService
import com.example.splash.api.models.Pagination
import com.example.splash.api.models.RentalHouseList
import com.example.splash.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var adventAdapter : FavoriteRecyclerAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var data: RentalHouseList

    /* List page parameters */
    private var page: Int = 1
    private var limit: Int = 10
    private var sort: String = "created_at:desc"
    private var search: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = RentalHouseList(
            false,
            Pagination(false, 0, 0, false),
            arrayListOf()
        )
        getDataAPI()

        adventAdapter = FavoriteRecyclerAdapter(data, binding)
        binding.FavoriteRc.adapter = adventAdapter
        binding.FavoriteRc.layoutManager = LinearLayoutManager(requireContext())
        adventAdapter.notifyDataSetChanged()

        binding.favoribtn.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun lockButtons() {
        binding.forwardButton.isEnabled = false
        binding.backwardButton.isEnabled = false
    }

    private fun unlockButtons() {
        binding.forwardButton.isEnabled = true
        binding.backwardButton.isEnabled = true
    }

    private fun loading() {
        binding.progressBar1.visibility = View.VISIBLE
        binding.FavoriteRc.visibility = View.INVISIBLE
    }

    private fun loaded() {
        binding.progressBar1.visibility = View.INVISIBLE
        binding.FavoriteRc.visibility = View.VISIBLE
    }

    private fun getDataAPI() {
        loading()
        lockButtons()
        val apiService = RestApiService(requireContext())
        apiService.getRentalHouseList(page, limit, sort, search, true) {
            if (it?.isSuccess == true && it.result != null) {
                val totalPage = (it.result?.pagination?.fullCount?.toFloat()?.div(limit))
                var totalPageInt = totalPage?.toInt()
                if (totalPage != null) {
                    if (totalPage % 1 != 0f) {
                        totalPageInt = totalPage.toInt() + 1
                    }
                }
                val pageText = "${page}/${totalPageInt}"
                binding.pageIndicator.text = pageText
                if(it.result?.pagination?.nextPage == true) {   // if next page exists
                    binding.forwardButton.visibility = View.VISIBLE
                } else {
                    binding.forwardButton.visibility = View.INVISIBLE
                }
                if(it.result?.pagination?.prevPage == true) {   // if prev page exists
                    binding.backwardButton.visibility = View.VISIBLE
                } else {
                    binding.backwardButton.visibility = View.INVISIBLE
                }
                it.result?.fetched = true
                adventAdapter.UpdateData(it.result as RentalHouseList)
                unlockButtons()
                loaded()
                // scroll to top
                binding.FavoriteRc.scrollToPosition(0)
            } else {
                Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_SHORT)
                    .show()
                unlockButtons()
                loaded()
            }
        }
    }
}
