package cmc.sole.android.Follow

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowFollowerFragment: BaseFragment<FragmentFollowerFollowerBinding>(FragmentFollowerFollowerBinding::inflate) {

    lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowListData>()

    override fun initAfterBinding() {
        initAdapter()
    }

    private fun initAdapter() {
        followListRVAdapter = FollowListRVAdapter(followList)
        binding.followFollowerRv.adapter = followListRVAdapter
        binding.followFollowerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // MEMO: DUMMY DATA
        followList.add(FollowListData("프로필", "닉네임 1", "1", "6", true))
        followList.add(FollowListData("프로필", "닉네임 2", "2", "2", false))
        followList.add(FollowListData("프로필", "닉네임 3", "3", "5", false))
        followList.add(FollowListData("프로필", "닉네임 4", "4", "4", true))
        followList.add(FollowListData("프로필", "닉네임 5", "5", "2", true))
        followList.add(FollowListData("프로필", "닉네임 6", "6", "22", true))
    }
}