package com.example.splash

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splash.api.RestApiService
import com.example.splash.api.models.Pagination
import com.example.splash.api.models.RentalHouseList
import com.example.splash.databinding.FragmentIlanlarimBinding

class IlanlarimFragment : Fragment() {
    private lateinit var adventAdapter: RecyclerViewAdapterOwned
    private lateinit var binding: FragmentIlanlarimBinding
    private lateinit var data: RentalHouseList

    /* List page parameters */
    private var page: Int = 1
    private var limit: Int = 10
    private var sort: String = "created_at:desc"
    private var search: String = ""

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentIlanlarimBinding.inflate(inflater, container, false)

        data = RentalHouseList(
            false,
            Pagination(false, 0, 0, false),
            arrayListOf()
        )
        getDataAPI()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adventAdapter = RecyclerViewAdapterOwned(data)
        binding.recyclerView.adapter = adventAdapter

        binding.backButn.setOnClickListener {
            val action = IlanlarimFragmentDirections.actionIlanlarimFragmentToProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.forwardButton.setOnClickListener {
            page += 1
            getDataAPI()
        }

        binding.backwardButton.setOnClickListener {
            page -= 1
            if(page < 1) {
                page = 1
            }
            getDataAPI()
        }

        return binding.root
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
        binding.recyclerView.visibility = View.INVISIBLE
        binding.pageIndicator.visibility = View.INVISIBLE
    }

    private fun loaded() {
        binding.progressBar1.visibility = View.INVISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.pageIndicator.visibility = View.VISIBLE
    }

    private fun getDataAPI() {
        loading()
        lockButtons()
        val apiService = RestApiService(requireContext())
        apiService.getRentalHouseOwnedList(page, limit, sort, search) {
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
                binding.recyclerView.scrollToPosition(0)
            } else {
                Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_SHORT)
                    .show()
                unlockButtons()
                loaded()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
    }
}