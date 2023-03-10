package cmc.sole.android.Home

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.MyPage.MyPageActivity
import cmc.sole.android.Home.Search.SearchActivity
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var popularCourseRVAdapter: HomePopularCourseRVAdapter
    private lateinit var myCourseRVAdapter: HomeDefaultCourseRVAdapter
    private var popularCourseList = ArrayList<PopularCourse>()
    private var myCourseList = ArrayList<DefaultCourse>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        popularCourseRVAdapter = HomePopularCourseRVAdapter(popularCourseList)
        binding.homePopularCourseRv.adapter = popularCourseRVAdapter
        binding.homePopularCourseRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.homePopularCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        myCourseRVAdapter = HomeDefaultCourseRVAdapter(myCourseList)
        binding.homeMyCourseRv.adapter = myCourseRVAdapter
        binding.homeMyCourseRv.addItemDecoration(RecyclerViewVerticalDecoration("bottom", 40))
        binding.homeMyCourseRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // MEMO: DummyData
        // CONDITION: 이 주변 인기 코스 7개로 고정
        popularCourseList.add(PopularCourse("image", "테스트1"))
        popularCourseList.add(PopularCourse("image", "테스트2"))
        popularCourseList.add(PopularCourse("image", "테스트3"))
        popularCourseList.add(PopularCourse("image", "테스트4"))
        popularCourseList.add(PopularCourse("image", "테스트5"))

        // CONDITION: 내 취향 코스 5개 + 더 보기 버튼 이용
        myCourseList.add(DefaultCourse("image", "제목제목", false, "서울 종로구1", "1시간 소요", "1.2km", arrayListOf("테스트")))
        myCourseList.add(DefaultCourse("image", "제목제목", true, "서울 종로구2", "2시간 소요", "2.2km", arrayListOf("테스트")))
        myCourseList.add(DefaultCourse("image", "제목제목", false, "서울 종로구3", "3시간 소요", "3.2km", arrayListOf("테스트")))
        myCourseList.add(DefaultCourse("image", "제목제목", true, "서울 종로구4", "4시간 소요", "4.2km", arrayListOf("테스트")))
        myCourseList.add(DefaultCourse("image", "제목제목", true, "서울 종로구5", "5시간 소요", "5.2km", arrayListOf("테스트")))
    }

    private fun initClickListener() {
        binding.homeSearchIv.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        binding.homeProfileIv.setOnClickListener {
            startActivity(Intent(activity, MyPageActivity::class.java))
        }
    }
}