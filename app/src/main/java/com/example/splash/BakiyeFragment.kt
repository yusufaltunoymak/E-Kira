package com.example.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.splash.api.RestApiService
import com.example.splash.databinding.FragmentBakiyeBinding
import com.google.android.material.tabs.TabLayoutMediator

class BakiyeFragment : Fragment() {
    private lateinit var binding: FragmentBakiyeBinding

    private val tabTitle = arrayOf("Bakiye Çek" , "Taleplerim")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBakiyeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = RestApiService(requireContext())
        apiService.getBalance() {
            if(it?.isSuccess == true) {
                val balance = it.result
                val formatBigDecimal = String.format("%.2f", balance)
                binding.textView6.text = formatBigDecimal
            } else {
                binding.textView6.text = "0.00"
                Toast.makeText(context, "Bakiye bilgisi alınamadı: " + it?.error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.ViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pager = binding.ViewPager2
        val tl = binding.tablayout
        pager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(tl, pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        val indicator = binding.indicator
        indicator.setViewPager(binding.ViewPager2)

        binding.backbtn.setOnClickListener {
            val action = BakiyeFragmentDirections.actionBakiyeFragmentToProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}