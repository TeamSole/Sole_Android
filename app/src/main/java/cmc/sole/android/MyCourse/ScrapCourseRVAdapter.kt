package cmc.sole.android.MyCourse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import cmc.sole.android.databinding.ItemMyCourseCourseCheckBinding

class ScrapCourseRVAdapter(private val scrapCourseList: ArrayList<ScrapCourseResult>): RecyclerView.Adapter<ScrapCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var itemLongClickListener: OnItemLongClickListener

    interface OnItemClickListener {
        fun onItemClick(data: ScrapCourseResult, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(data: ScrapCourseResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        itemLongClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScrapCourseRVAdapter.ViewHolder {
        val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrapCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemLongClickListener.onItemLongClick(scrapCourseList[position], position)

            if (holder.binding.myCourseCourseCheckIv.visibility == View.VISIBLE) {
                holder.binding.myCourseCourseCheckIv.visibility = View.GONE
            } else {
                holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
            }
        }
        holder.bind(scrapCourseList[position])
    }

    override fun getItemCount(): Int = scrapCourseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapCourse: ScrapCourseResult) {
//            if (course.title == "베이커리 맞은 편 일식당") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_3)
//            } else if (course.title == "발리 다녀와서 파이") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_4)
//            } else if (course.title == "관람차로 내다보는 속초 바다") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_5)
//            } else if (course.title == "행궁동 로컬 추천 코스") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_6)
//            } else if (course.title == "물고기, 고기") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_7)
//            }
//
//            binding.myCourseCourseTitleTv.text = course.title
//            binding.myCourseCourseLocationTv.text = course.location
//            binding.myCourseCourseTimeTv.text = course.time
//            binding.myCourseCourseDistanceTv.text = course.distance

            // TODO: 태그 추가하기
        }
    }

    fun addItem(item: ScrapCourseResult) {
        scrapCourseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<ScrapCourseResult>) {
        scrapCourseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun removeAllItems() {
        scrapCourseList.clear()
    }
}