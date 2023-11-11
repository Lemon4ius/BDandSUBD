package com.example.bdandsubd.presenter

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentExportWordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.xwpf.usermodel.BreakType
import org.apache.poi.xwpf.usermodel.TableRowAlign
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileOutputStream


class ExportWordFragment : Fragment() {
    lateinit var filepath: File
    lateinit var binding: FragmentExportWordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExportWordBinding.inflate(inflater)
        val permission = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(
            requireActivity(),
            permission,
            PackageManager.PERMISSION_GRANTED
        )
        filepath = File(requireActivity().getExternalFilesDir(null), "Test.docx")

        try {
            if (!filepath.exists()) {
                filepath.createNewFile()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.exportBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                createDocx(filepath)
                openDocument(filepath)
            }
        }
        // Здесь можно предоставить доступ к файлу, например, через FileProvider
        return binding.root
    }

    fun openDocument(name: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val file = name
        val extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

        val fileUri =
            FileProvider.getUriForFile(requireContext(), "com.example.bdandsubd.fileprovider", file)

        intent.setDataAndType(fileUri, mimetype)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Гарантия разрешения на чтение

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Обработка случая, если нет приложения для открытия данного типа файла
            e.printStackTrace()
        }
    }

    fun createDocx(filepath: File) {
        try {
            val xwpfDocument = XWPFDocument()

            val date = DbWorker.newsDao.getDataFromRoom()
            val titleParagraph = xwpfDocument.createParagraph()
            val titleRun = titleParagraph.createRun()
            titleRun.isBold = true
            titleRun.setText("Таблица созданных комнат")
            titleRun.setFontSize(16)

            // Create a table with the number of rows equal to the size of the 'date' list and 6 columns (including headers)
            val xwpfTable = xwpfDocument.createTable(date.size + 1, 6)

            // Set the width of the table and the alignment
            val tableWidth = 6.0 // in inches
            val tableAlignment = TableRowAlign.CENTER
            xwpfTable.width = (tableWidth * 1440).toInt() // Convert inches to twips
            xwpfTable.tableAlignment = tableAlignment

            // Define column headers
            val headers =
                listOf("ID", "Название отеля", "Имя гостя", "Номер комнаты", "Тип комнаты", "Цена")

            // Populate the first row with column headers
            val headerRow = xwpfTable.getRow(0)
            for (i in headers.indices) {
                headerRow.getCell(i).setText(headers[i])
            }

            // Loop through rows and cells to populate the table with data
            for (i in date.indices) {
                val row = xwpfTable.getRow(i + 1) // Start from row 1 to skip the header row
                row.getCell(0).setText(date[i].id?.toString() ?: "") // Column 1
                row.getCell(1).setText(date[i].nameHotel) // Column 2
                row.getCell(2).setText(date[i].nameGuest) // Column 3
                row.getCell(3).setText("№: " + date[i].roomNumber.toString()) // Column 4
                row.getCell(4).setText(date[i].type) // Column 5
                row.getCell(5).setText("${date[i].price} Byn") // Column 6
            }
            var result = 0.0
            date.forEach {
                result = it.price
            }

            // Create a paragraph for the table
            val paragraph = xwpfDocument.createParagraph()

            // Add an empty line after the table
            paragraph.createRun().addBreak(BreakType.TEXT_WRAPPING)
            val totalCostParagraph = xwpfDocument.createParagraph()
            val totalCostRun = totalCostParagraph.createRun()
            totalCostRun.setText("Общая цена за все комнаты - " + result)
            totalCostRun.setFontSize(16)
            
            val fileOutputStream = FileOutputStream(filepath)
            xwpfDocument.write(fileOutputStream)
            fileOutputStream.close()
            xwpfDocument.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = ExportWordFragment()
    }
}



