package cmc.sole.android.MyCourse

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cmc.sole.android.Write.MyCourseWriteViewModel
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteTagBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyCourseWriteTagBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentMyCourseWriteTagBinding
    private lateinit var myCourseTagBottomPlaceRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomWithRVAdapter: MyCourseTagButtonRVAdapter
    private lateinit var myCourseTagBottomTransRVAdapter: MyCourseTagButtonRVAdapter
    private var placeTagList = ArrayList<TagButton>()
    private var withTagList = ArrayList<TagButton>()
    private var transTagList = ArrayList<TagButton>()
    private var checkTagList: MutableList<TagButton> = mutableListOf()
    private lateinit var dialogFinishListener: OnTagFragmentFinishListener
    private var sendTag = listOf<TagButton>()
    private var tagFlag = booleanArrayOf()

    private val writeVM: MyCourseWriteViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.AppBottomDialogTheme
    }

    interface OnTagFragmentFinishListener {
        fun finish(tagSort: List<TagButton>)
    }

    fun setOnFinishListener(listener: OnTagFragmentFinishListener) {
        dialogFinishListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        var returnList = mutableListOf<TagButton>()
        for (i in 0..8) {
            returnList.add(placeTagList[i])
        }
        for (i in 0..4) {
            returnList.add(withTagList[i])
        }
        for (i in 0..3) {
            returnList.add(transTagList[i])
        }
        Log.d("WRITE-TEST", "returnList = $returnList")
        dialogFinishListener.finish(returnList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // FIX: 태그 여러 개로 다시 나오는 오류 수정 필요!!
        binding = BottomFragmentMyCourseWriteTagBinding.inflate(inflater, container, false)
        // checkTagList = writeVM.getTag()

        tagFlag = requireArguments().getBooleanArray("tagFlag")!!

        initAdapter()
        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.myCourseTagOkBtn.setOnClickListener {
            var tagResult: ArrayList<TagButton> = arrayListOf()

            if (writeVM.getTag() != null) {
                var tagCheckList = writeVM.getTag()!!
                for (i in 0 until writeVM.getTag()!!.size - 1) {
                    tagResult.add(tagCheckList[i])
                }
            }

            for (element in checkTagList) {
                tagResult.add(element)
            }
            tagResult.add(TagButton(1000, "", false))

            writeVM.setTag(tagSort(tagResult))

            sendTag = tagSort(tagResult)

            for (i in 0..17) {
                Log.d("WRITE-TEST", "tagCheck = $i ${tagFlag[i]}")
            }
            dismiss()
        }
    }

    private fun initAdapter() {
        myCourseTagBottomPlaceRVAdapter = MyCourseTagButtonRVAdapter(placeTagList)
        binding.myCoursePlaceTagRv.adapter = myCourseTagBottomPlaceRVAdapter
        binding.myCoursePlaceTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCoursePlaceTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomPlaceRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position] = data.isChecked
                Log.d("WRITE-TEST", "$tagFlag")
//                if (data.isChecked) {
//                    checkTagList.add(data)
//                }
//                else {
//                    for (i in 0 until checkTagList.size) {
//                        if (data.title == checkTagList[i].title) {
//                            checkTagList.removeAt(i)
//                            break
//                        }
//                    }
//                }
//                writeVM.setCheckTag(position)
            }
        })

        placeTagList.add(TagButton(1, resources.getString(R.string.place_tag1), tagFlag[0]))
        placeTagList.add(TagButton(2, resources.getString(R.string.place_tag2), tagFlag[1]))
        placeTagList.add(TagButton(3, resources.getString(R.string.place_tag3), tagFlag[2]))
        placeTagList.add(TagButton(4, resources.getString(R.string.place_tag4), tagFlag[3]))
        placeTagList.add(TagButton(5, resources.getString(R.string.place_tag5), tagFlag[4]))
        placeTagList.add(TagButton(6, resources.getString(R.string.place_tag6), tagFlag[5]))
        placeTagList.add(TagButton(7, resources.getString(R.string.place_tag7), tagFlag[6]))
        placeTagList.add(TagButton(8, resources.getString(R.string.place_tag8), tagFlag[7]))
        placeTagList.add(TagButton(9, resources.getString(R.string.place_tag9), tagFlag[8]))
        placeTagList.add(TagButton(null, "", false))

        myCourseTagBottomWithRVAdapter = MyCourseTagButtonRVAdapter(withTagList)
        binding.myCourseWithTagRv.adapter = myCourseTagBottomWithRVAdapter
        binding.myCourseWithTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseWithTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomWithRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position] = data.isChecked
                Log.d("WRITE-TEST", "$tagFlag")

//                if (data.isChecked) {
//                    checkTagList.add(data)
//                }
//                else {
//                    for (i in 0 until checkTagList.size) {
//                        if (data.title == checkTagList[i].title) {
//                            checkTagList.removeAt(i)
//                            break
//                        }
//                    }
//                }
//                writeVM.setCheckTag(position + 9)
            }
        })

        withTagList.add(TagButton(10, resources.getString(R.string.with_tag10), tagFlag[9]))
        withTagList.add(TagButton(11, resources.getString(R.string.with_tag11), tagFlag[10]))
        withTagList.add(TagButton(12, resources.getString(R.string.with_tag12), tagFlag[11]))
        withTagList.add(TagButton(13, resources.getString(R.string.with_tag13), tagFlag[12]))
        withTagList.add(TagButton(14, resources.getString(R.string.with_tag14), tagFlag[13]))
        withTagList.add(TagButton(null, "", false))

        myCourseTagBottomTransRVAdapter = MyCourseTagButtonRVAdapter(transTagList)
        binding.myCourseTransportTagRv.adapter = myCourseTagBottomTransRVAdapter
        binding.myCourseTransportTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 40))
        binding.myCourseTransportTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 40))
        myCourseTagBottomTransRVAdapter.setOnItemClickListener(object: MyCourseTagButtonRVAdapter.OnItemClickListener {
            override fun onItemClick(data: TagButton, position: Int) {
                tagFlag[position] = data.isChecked
                Log.d("WRITE-TEST", "$tagFlag")
            }
        })

        transTagList.add(TagButton(15, resources.getString(R.string.trans_tag15), tagFlag[14]))
        transTagList.add(TagButton(16, resources.getString(R.string.trans_tag16), tagFlag[15]))
        transTagList.add(TagButton(17, resources.getString(R.string.trans_tag17), tagFlag[16]))
        transTagList.add(TagButton(18, resources.getString(R.string.trans_tag18), tagFlag[17]))
        transTagList.add(TagButton(null, "", false))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 85 / 100
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun tagSort(list: List<TagButton>): List<TagButton> {
        return list.sortedBy { it.index }
    }
}