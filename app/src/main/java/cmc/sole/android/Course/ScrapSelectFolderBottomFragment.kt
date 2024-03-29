package cmc.sole.android.Course

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Scrap.Retrofit.*
import cmc.sole.android.Scrap.ScrapCourseMoveRVAdapter
import cmc.sole.android.databinding.BottomFragmentScrapSelectFolderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScrapSelectFolderBottomFragment: BottomSheetDialogFragment(), ScrapFolderView, HomeScrapAddAndCancelView {

    lateinit var binding: BottomFragmentScrapSelectFolderBinding
    private var courseId: Int = -1
    private lateinit var scrapCourseMoveRVAdapter: ScrapCourseMoveRVAdapter
    private var folderList = ArrayList<ScrapFolderDataResult>()
    lateinit var scrapService: ScrapService
    lateinit var homeService: HomeService
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var isSuccess = false

    interface OnDialogFinishListener {
        fun finish(isSuccess: Boolean)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomFragmentScrapSelectFolderBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER)

        courseId = arguments?.getInt("courseId")!!

        initAdapter()
        initService()

        return binding.root
    }

    private fun initAdapter() {
        scrapCourseMoveRVAdapter = ScrapCourseMoveRVAdapter(folderList)
        binding.scrapSelectFolderRv.adapter = scrapCourseMoveRVAdapter
        binding.scrapSelectFolderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        scrapCourseMoveRVAdapter.setOnItemClickListener(object: ScrapCourseMoveRVAdapter.OnItemClickListener {
            override fun onItemClick(data: ScrapFolderDataResult, position: Int) {
                // MEMO: 폴더 이동시키기
                homeService.scrapAddAndCancel(courseId, data.scrapFolderId)
            }
        })
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderView(this)
        scrapService.getScrapFolder()
        homeService = HomeService()
        homeService.setHomeScrapAddAndCancelView(this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(isSuccess)
    }

    override fun scrapFolderSuccessView(scrapFolderDataResult: ArrayList<ScrapFolderDataResult>) {
        scrapCourseMoveRVAdapter.addAllItems(scrapFolderDataResult)
    }

    override fun scrapFolderFailureView() {

    }

    override fun homeScrapAddAndCancelSuccessView() {
        dismiss()
        isSuccess = true
    }

    override fun homeScrapAddAndCancelFailureView() {
        isSuccess = false
    }
}