package com.example.bdandsubd.presenter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.R
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentExportWordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.xwpf.usermodel.TableRowAlign
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import java.io.File
import java.io.FileOutputStream


class ExportWordFragment : Fragment() {
    lateinit var filepath:File
    lateinit var binding: FragmentExportWordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExportWordBinding.inflate(inflater)
        val permission= arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(requireActivity(),permission,PackageManager.PERMISSION_GRANTED)
        filepath= File(requireActivity().getExternalFilesDir(null),"Test.docx")

        try {
            if (!filepath.exists()){
                filepath.createNewFile()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding.exportBtn.setOnClickListener {
            createDocx()
        }
        // Здесь можно предоставить доступ к файлу, например, через FileProvider
        return binding.root
    }
    fun createDocx() {
        try {
            val xwpfDocument = XWPFDocument()

            CoroutineScope(Dispatchers.IO).launch{
                val date=DbWorker.newsDao.getDataFromRoom()
                val xwpfTable = xwpfDocument.createTable(date.size, 5)

                // Set the width of the table and the alignment (optional)
                val tableWidth = 6.0 // in inches
                val tableAlignment = TableRowAlign.CENTER
                xwpfTable.width = (tableWidth * 1440).toInt() // Convert inches to twips
                xwpfTable.tableAlignment = tableAlignment



                // Loop through rows and cells to populate the table
                for (row in xwpfTable.rows) {
                    for (cell in row.tableCells) {
                        val xwpfParagraph = cell.addParagraph()
                        val xwpfRun = xwpfParagraph.createRun()
                        for (i in date.indices){
                            val cell = row.getCell(i)
                            val paragraph1 = cell.addParagraph()
                            val run1 = paragraph1.createRun()
                            run1.setText(date[i].nameGuest)
                            run1.fontSize = 12
                        }


                    }
                }
            }
            // Create a table with 2 rows and 3 columns


            // Add an empty line after the table
            xwpfDocument.createParagraph()

            // Rest of your code...

            val fileOutputStream = FileOutputStream(filepath)
            xwpfDocument.write(fileOutputStream)

            if (fileOutputStream != null) {
                fileOutputStream.flush()
                fileOutputStream.close()
            }

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



