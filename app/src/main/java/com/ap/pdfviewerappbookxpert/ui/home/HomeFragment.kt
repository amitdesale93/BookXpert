package com.ap.pdfviewerappbookxpert.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ap.pdfviewerappbookxpert.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.btnViewPDF).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_pdf)
        }

        view.findViewById<Button>(R.id.btnImageCaptureAndGallerySelection).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_image)
        }

        view.findViewById<Button>(R.id.btnSettings).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

        view.findViewById<Button>(R.id.btnList).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_listitem)
        }
        return view
    }
}