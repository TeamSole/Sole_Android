package cmc.sole.android.Follow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Follow.Retrofit.FollowUserInfoView
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.R

import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.FragmentFollowUserBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.math.roundToInt
import androidx.fragment.app.Fragment
import cmc.sole.android.Course.ScrapSelectFolderBottomFragment
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowUserFragment: Fragment(),
    FollowUserInfoView, HomeScrapAddAndCancelView {

    private var _binding: FragmentFollowUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var followService: FollowService
    private lateinit var homeService: HomeService
    lateinit var followUserRecentRVAdapter: HomeDefaultCourseRVAdapter
    lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()
    var recentCourseList = ArrayList<DefaultCourse>()
    private var followInfoMemberSocialId = ""
    var courseId: Int? = null
    var clickItemIndex: Int? = null

    // UPDATE: courseId 연결해주기!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowUserBinding.inflate(inflater, container, false)
        
        followInfoMemberSocialId = requireArguments().getString("followInfoMemberSocialId").toString()

        initService()
        initAdapter()
        initClickListener()
        
        return binding.root
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setHomeScrapAddAndCancelView(this)

        followService = FollowService()
        followService.setFollowUserInfoView(this)
        followService.getFollowUserInfo(followInfoMemberSocialId, courseId)
    }

    private fun initAdapter() {
        followUserRecentRVAdapter = HomeDefaultCourseRVAdapter(recentCourseList)
        binding.followUserRecentRv.adapter = followUserRecentRVAdapter
        binding.followUserRecentRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followUserRecentRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int, mode: String) {
                clickItemIndex = position
                if (mode == "all") {
                    val intent = Intent(activity, CourseDetailActivity::class.java)
                    intent.putExtra("courseId", data.courseId)
                    intent.putExtra("like", data.like)
                    startActivity(intent)
                } else if (mode == "heart") {
                    if (data.like) {
                        homeService.scrapAddAndCancel(data.courseId, null)
                    } else {
                        val scrapSelectFolderBottomFragment = ScrapSelectFolderBottomFragment()
                        var bundle = Bundle()
                        bundle.putInt("courseId", data.courseId,)
                        scrapSelectFolderBottomFragment.arguments = bundle
                        scrapSelectFolderBottomFragment.show(activity!!.supportFragmentManager, "ScrapSelectFolderBottomFragment")
                        scrapSelectFolderBottomFragment.setOnDialogFinishListener(object: ScrapSelectFolderBottomFragment.OnDialogFinishListener {
                            override fun finish(isSuccess: Boolean) {
                                if (isSuccess) {
                                    followUserRecentRVAdapter.changeLikeStatus(position)
                                    followUserRecentRVAdapter.notifyItemChanged(position)
                                }
                            }
                        })
                    }
                }
            }
        })

        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.followUserPopularTagRv.adapter = tagRVAdapter
        binding.followUserPopularTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.followUserPopularTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.followUserPopularTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))
    }

    private fun initClickListener() {
        binding.followUserBackIv.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun followUserInfoSuccessView(followUserInfo: FollowUserInfoResult) {
        Glide.with(this).load(followUserInfo.profileImg)
            .placeholder(R.drawable.ic_profile).centerCrop().circleCrop().into(binding.followUserProfileIv)
        binding.followUserNicknameTv.text = followUserInfo.nickname
        binding.followUserPopularTitleTv.text = "${followUserInfo.nickname}님의 인기 코스"
        binding.followUserRecentTv.text = "${followUserInfo.nickname}님의 최근 코스"
        binding.followUserFollowerTv.text = "팔로워 ${followUserInfo.followerCount}"
        binding.followUserFollowingTv.text = "팔로잉 ${followUserInfo.followingCount}"
        binding.followerUserInfoTv.text = followUserInfo.description

        if (followUserInfo.followStatus == "FOLLOWER") {
            binding.itemFollowFollowBtn.visibility = View.VISIBLE
            binding.itemFollowFollowingBtn.visibility = View.GONE
        } else if (followUserInfo.followStatus == "FOLLOWING") {
            binding.itemFollowFollowBtn.visibility = View.GONE
            binding.itemFollowFollowingBtn.visibility = View.VISIBLE
        }

        var popularCourse = followUserInfo.popularCourse
        Glide.with(this).load(popularCourse.thumbnailImg).into(binding.followUserPopularImg)
        binding.followUserPopularTitleTv.text = popularCourse.title
        binding.followUserPopularLocationTv.text = popularCourse.address
        binding.followUserPopularTimeTv.text = "${(popularCourse.duration.toDouble() / 60).toInt()} 시간 소요"
        binding.followUserPopularDistanceTv.text = ((popularCourse.distance * 100.0).roundToInt() / 100.0).toString() + "km 이동"

        for (i in 0 until popularCourse.categories.size) {
            tagRVAdapter.addItem(Translator.tagEngToKor(activity as AppCompatActivity, popularCourse.categories.elementAt(i).toString()))
        }
        tagRVAdapter.addItem("")

        if (popularCourse.like)
            binding.followUserPopularHeartIv.setImageResource(R.drawable.ic_heart_color)
        else binding.followUserPopularHeartIv.setImageResource(R.drawable.ic_heart_empty)

        // UPDATE: 태그 추가
        followUserRecentRVAdapter.addAllItems(followUserInfo.recentCourses)
    }

    override fun followUserInfoFailureView() {
        var message = "팔로우 유저 정보 불러오기 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun homeScrapAddAndCancelSuccessView() {
        followUserRecentRVAdapter.changeLikeStatus(clickItemIndex!!)
        followUserRecentRVAdapter.notifyItemChanged(clickItemIndex!!)
    }

    override fun homeScrapAddAndCancelFailureView() {

    }
}