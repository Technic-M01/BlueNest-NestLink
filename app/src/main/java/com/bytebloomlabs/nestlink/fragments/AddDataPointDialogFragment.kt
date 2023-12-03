package com.bytebloomlabs.nestlink.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bytebloomlabs.nestlink.models.Backend
import com.bytebloomlabs.nestlink.models.UserData
import com.bytebloomlabs.nestlink.databinding.FragmentAddDataPointDialogBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class AddDataPointDialogFragment : DialogFragment() {

    private var _binding: FragmentAddDataPointDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDataPointDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (binding) {

            btnCancel.setOnClickListener {
//                findNavController().popBackStack()
                this@AddDataPointDialogFragment.dismiss()
            }

            btnAddNote.setOnClickListener {

                val formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
                val current = LocalDateTime.now().format(formatter)

                val telem = etEggDataTelemetry.text.toString()

                // create data point object
                val newDataPoint = UserData.EggDataPoints(
                    id = UUID.randomUUID().toString(),
                    telemetry = telem.toByteArray(Charsets.UTF_8),
                    telemetryTimestamp = current,
                    eggType = etEggDataEggType.text.toString()
                )

                // store it in the backend
                Backend.createEggDataPoint(newDataPoint)

                // add it to UserData, this will trigger a UI refresh
                UserData.addEggDataPoint(newDataPoint)

                this@AddDataPointDialogFragment.dismiss()
//                findNavController().popBackStack()
            }
        }

    }


    companion object {
        private const val TAG = "AddDataPointFragment"
    }

}