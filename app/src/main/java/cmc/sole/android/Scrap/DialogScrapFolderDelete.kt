package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDeleteView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.databinding.DialogScrapFolderDeleteBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogScrapFolderDelete: DialogFragment(),
    ScrapFolderDeleteView {

    lateinit var binding: DialogScrapFolderDeleteBinding

    lateinit var scrapService: ScrapService
    private var scrapFolderId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogScrapFolderDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        scrapFolderId = requireArguments().getInt("scrapFolderId")
        Log.d("API-TEST", "scrapFolderId = ${scrapFolderId}")

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderDeleteView(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.scrapFolderDeleteCancel.setOnClickListener {
            dismiss()
        }
        
        binding.scrapFolderDeleteBtn.setOnClickListener {
            scrapService.deleteScrapFolder(scrapFolderId)
        }
    }

    override fun scrapFolderDeleteSuccessView() {
        dismiss()
    }

    override fun scrapFolderDeleteFailureView() {
        Toast.makeText(context, "폴더 삭제 실패", Toast.LENGTH_LONG).show()
    }
}