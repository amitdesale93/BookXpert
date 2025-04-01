package com.ap.pdfviewerappbookxpert.ui.pdf

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ap.pdfviewerappbookxpert.R
import com.rajat.pdfviewer.PdfRendererView

class PdfViewerFragment  : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pdf_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pdfView: PdfRendererView = view.findViewById(R.id.pdfView)

        // Loads PDF from the given URL
       pdfView.initWithUrl(
            url = "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf",
            lifecycleCoroutineScope = lifecycleScope,
            lifecycle = lifecycle
        )
    }
}
