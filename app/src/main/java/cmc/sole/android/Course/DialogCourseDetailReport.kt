package cmc.sole.android.Course

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import cmc.sole.android.databinding.DialogCourseDetailDeleteBinding
import cmc.sole.android.databinding.DialogCourseDetailReportBinding
import cmc.sole.android.databinding.DialogScrapFolderNewBinding

class DialogCourseDetailReport: DialogFragment() {

    lateinit var binding: DialogCourseDetailReportBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCourseDetailReportBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)

        initClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {
        binding.courseDetailReportCancel.setOnClickListener {
            dismiss()
        }
        
        binding.courseDetailReportBtn.setOnClickListener {
            Toast.makeText(context, "신고하기", Toast.LENGTH_SHORT).show()
        }
    }
}