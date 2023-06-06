import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.splash.R
import com.example.splash.databinding.FragmentBakiyeCekBinding

class BakiyeCekFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBakiyeCekBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_bakiye_cek, container, false)

        binding.BakiyeCekButton.setOnClickListener {
            Toast.makeText(context, "Bakiye Çekme Talebiniz Alınmıştır.", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = BakiyeCekFragment()
    }
}