package cmc.sole.android.Follow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowCourseView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MainActivity
import cmc.sole.android.R

import cmc.sole.android.databinding.FragmentFollowBinding
import androidx.fragment.app.Fragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowFragment: Fragment(),
    FollowCourseView, HomeScrapAddAndCancelView {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    lateinit var homeService: HomeService
    lateinit var followService: FollowService
    lateinit var followActivityRVAdapter: FollowActivityRVAdapter
    private var followActivityList = ArrayList<FollowCourseResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        
        initService()
        initAdapter()
        initClickListener()
        
        return binding.root
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setHomeScrapAddAndCancelView(this)
        followService = FollowService()
        followService.setFollowCourseView(this)
        followService.getFollowCourse()
    }

    private fun initAdapter() {
        followActivityRVAdapter = FollowActivityRVAdapter(followActivityList)
        binding.followActivityRv.adapter = followActivityRVAdapter
        binding.followActivityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followActivityRVAdapter.setOnItemClickListener(object: FollowActivityRVAdapter.OnItemClickListener {
            override fun onItemClick(data: FollowCourseResult, position: Int) {
                // UPDATE: 업데이트 예정
                // homeService.scrapAddAndCancel(data.courseId!!)
            }
        })
    }

    private fun initClickListener() {
        binding.followFollowIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fl, FollowListFragment()).addToBackStack("FollowList").commit()
        }
    }

    override fun followCourseSuccessView(followCourse: ArrayList<FollowCourseResult>) {
        followActivityRVAdapter.addAllItems(followCourse)
    }

    override fun followCourseFailureView() {
        var message = "팔로잉 유저 코스 불러오기 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun homeScrapAddAndCancelSuccessView() {
        Log.d("API-TEST", "SCRAP 성공")
    }

    override fun homeScrapAddAndCancelFailureView() {
        var message = "스크랩 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}