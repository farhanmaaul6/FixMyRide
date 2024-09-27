package com.bangkit.fixmyrideapp.view.recomendation
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.data.response.PredictionRequest
import com.bangkit.fixmyrideapp.data.utils.Result
import com.bangkit.fixmyrideapp.databinding.FragmentRecomendationBinding

class RecomendationFragment : Fragment() {

    private lateinit var binding: FragmentRecomendationBinding
    private val predictViewModel: RecomendationViewModel by viewModels{
        RecomendationViewModel.RecomendationFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRecomendationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPredict()
    }

    private fun setupPredict() {
        binding.btnSubmit.setOnClickListener {
            val gejala1 = binding.spGejala1.selectedItem.toString()
            val gejala2 = binding.spGejala2.selectedItem.toString()
            val gejala3 = binding.spGejala3.selectedItem.toString()
            val jenisMotor = binding.spJenisMotor.selectedItem.toString()
            val merkMotor = binding.spMerkMotor.selectedItem.toString()
            val tahun = binding.etTahunMotor.text.toString()
            val tahunInt = tahun.toInt()
            val kmMotor = binding.etKmMotor.text.toString()
            val kmMotorInt = kmMotor.toInt()
            val pernahServis = binding.spService.selectedItem.toString()
            val predictionRequest = PredictionRequest(
                gejala1,
                gejala2,
                gejala3,
                jenisMotor,
                merkMotor,
                tahunInt,
                kmMotorInt,
                pernahServis
            )
            predictViewModel.predict(predictionRequest)
            predictViewModel.predictionResult.observe(requireActivity()) {
                when (it){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        val response = it.data
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.tvHasilPermasalahan.text = response.Predicted_Class
                        binding.tvHasilSolusi.text = response.Solusi
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Error: ${it.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}