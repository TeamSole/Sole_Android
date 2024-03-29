package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Scrap.Retrofit.*
import cmc.sole.android.databinding.DialogScrapMoveFolderBinding
import cmc.sole.android.getPlaceCategories
import org.json.JSONArray

class DialogScrapCourseMove: DialogFragment(), ScrapCourseMoveView, ScrapFolderView {

    lateinit var binding: DialogScrapMoveFolderBinding
    private var scrapFolderId: Int = -1
    private var moveCourseId = mutableSetOf<Int>()
    private lateinit var scrapCourseMoveRVAdapter: ScrapCourseMoveRVAdapter
    private var folderList = ArrayList<ScrapFolderDataResult>()
    lateinit var scrapService: ScrapService
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var moveResult = false

    interface OnDialogFinishListener {
        fun finish()
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogScrapMoveFolderBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER)

        scrapFolderId = requireArguments().getInt("scrapFolderId")
        arguments?.getIntegerArrayList("courseId")?.let { moveCourseId.addAll(it) }

        initAdapter()
        initService()

        return binding.root
    }

    private fun initAdapter() {
        scrapCourseMoveRVAdapter = ScrapCourseMoveRVAdapter(folderList)
        binding.scrapMoveFolderRv.adapter = scrapCourseMoveRVAdapter
        binding.scrapMoveFolderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        scrapCourseMoveRVAdapter.setOnItemClickListener(object: ScrapCourseMoveRVAdapter.OnItemClickListener {
            override fun onItemClick(data: ScrapFolderDataResult, position: Int) {
                scrapService.moveScrapCourse(data.scrapFolderId, ScrapFolderCourseMoveRequest(moveCourseId))
            }
        })
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderView(this)
        scrapService.setScrapCourseMoveView(this)
        scrapService.getScrapFolder()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish()
    }

    override fun scrapCourseMoveSuccessView(scrapCourseMoveResult: ScrapCourseMoveResult) {
        dismiss()
    }

    override fun scrapCourseMoveFailureView() {

    }

    override fun scrapFolderSuccessView(scrapFolderDataResult: ArrayList<ScrapFolderDataResult>) {
        scrapCourseMoveRVAdapter.addAllItems(scrapFolderDataResult)
    }

    override fun scrapFolderFailureView() {

    }
}